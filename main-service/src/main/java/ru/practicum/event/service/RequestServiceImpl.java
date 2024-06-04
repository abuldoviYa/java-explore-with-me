package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.enums.EventStateType;
import ru.practicum.event.enums.RequestStatusActionType;
import ru.practicum.event.enums.RequestStatusType;
import ru.practicum.event.mapper.RequestMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Request;
import ru.practicum.event.repository.RequestRepository;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final UserService userService;
    private final EventService eventService;
    private final StatsService statsService;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public List<ParticipationRequestDto> getEventRequestsByRequester(Long userId) {
        log.info("Displaying a list of requests for participation in other people's " +
                "events by a user with an id {}", userId);

        userService.getUserById(userId);

        return toParticipationRequestsDto(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createEventRequest(Long userId, Long eventId) {
        log.info("Creating a request to participate in an event with id {} by a user with id {}", eventId, userId);

        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ForbiddenException("You cannot create a request for your own event.");
        }

        if (!event.getState().equals(EventStateType.PUBLISHED)) {
            throw new ForbiddenException("You cannot create a request for an unpublished event.");
        }

        Optional<Request> oldRequest = requestRepository.findByEventIdAndRequesterId(eventId, userId);

        if (oldRequest.isPresent()) {
            throw new ForbiddenException("It is forbidden to create a repeat request.");
        }

        checkIsNewLimitGreaterOld(
                statsService.getConfirmedRequests(Set.of(event)).getOrDefault(eventId, 0L) + 1,
                event.getParticipantLimit()
        );

        Request newRequest = Request.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            newRequest.setStatus(RequestStatusType.CONFIRMED);
        } else {
            newRequest.setStatus(RequestStatusType.PENDING);
        }

        return requestMapper.toParticipationRequestDto(requestRepository.save(newRequest));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelEventRequest(Long userId, Long requestId) {
        log.info("Cancellation of a request with id {} to participate in an event by " +
                "a user with id {}", requestId, userId);

        userService.getUserById(userId);

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("There is no application for participation with such an id."));

        checkUserIsOwner(request.getRequester().getId(), userId);

        request.setStatus(RequestStatusType.CANCELED);

        return requestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestsByEventOwner(Long userId, Long eventId) {
        log.info("Displaying a list of requests to participate in an event with id {} " +
                "owner with id {}", eventId, userId);

        userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        checkUserIsOwner(event.getInitiator().getId(), userId);

        return toParticipationRequestsDto(requestRepository.findAllByEventId(eventId));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult patchEventRequestsByEventOwner(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Updating requests to participate in an event with id {} owner with id {} and parameters {}",
                eventId, userId, eventRequestStatusUpdateRequest);

        userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);

        checkUserIsOwner(event.getInitiator().getId(), userId);

        if (!event.getRequestModeration() ||
                event.getParticipantLimit() == 0 ||
                eventRequestStatusUpdateRequest.getRequestIds().isEmpty()) {
            return new EventRequestStatusUpdateResult(List.of(), List.of());
        }

        List<Request> confirmedList = new ArrayList<>();
        List<Request> rejectedList = new ArrayList<>();

        List<Request> requests = requestRepository.findAllByIdIn(eventRequestStatusUpdateRequest.getRequestIds());

        if (requests.size() != eventRequestStatusUpdateRequest.getRequestIds().size()) {
            throw new NotFoundException("Some participation requests were not found.");
        }

        if (!requests.stream()
                .map(Request::getStatus)
                .allMatch(RequestStatusType.PENDING::equals)) {
            throw new ForbiddenException("Only pending applications can be changed..");
        }

        if (eventRequestStatusUpdateRequest.getStatus().equals(RequestStatusActionType.REJECTED)) {
            rejectedList.addAll(changeStatusAndSave(requests, RequestStatusType.REJECTED));
        } else {
            Long newConfirmedRequests = statsService.getConfirmedRequests(Set.of(event)).getOrDefault(eventId, 0L) +
                    eventRequestStatusUpdateRequest.getRequestIds().size();

            checkIsNewLimitGreaterOld(newConfirmedRequests, event.getParticipantLimit());

            confirmedList.addAll(changeStatusAndSave(requests, RequestStatusType.CONFIRMED));

            if (newConfirmedRequests >= event.getParticipantLimit()) {
                rejectedList.addAll(changeStatusAndSave(
                        requestRepository.findAllByEventIdAndStatus(eventId, RequestStatusType.PENDING),
                        RequestStatusType.REJECTED)
                );
            }
        }

        return new EventRequestStatusUpdateResult(toParticipationRequestsDto(confirmedList),
                toParticipationRequestsDto(rejectedList));
    }

    private List<ParticipationRequestDto> toParticipationRequestsDto(List<Request> requests) {
        return requests.stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    private List<Request> changeStatusAndSave(List<Request> requests, RequestStatusType status) {
        requests.forEach(request -> request.setStatus(status));
        return requestRepository.saveAll(requests);
    }

    private void checkIsNewLimitGreaterOld(Long newLimit, Integer eventParticipantLimit) {
        if (eventParticipantLimit != 0 && (newLimit > eventParticipantLimit)) {
            throw new ForbiddenException(String.format("The limit of confirmed participation requests " +
                            "has been reached: %d",
                    eventParticipantLimit));
        }
    }

    private void checkUserIsOwner(Long id, Long userId) {
        if (!Objects.equals(id, userId)) {
            throw new ForbiddenException("The user is not the owner.");
        }
    }
}