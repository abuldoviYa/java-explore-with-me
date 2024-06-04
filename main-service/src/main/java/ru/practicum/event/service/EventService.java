package ru.practicum.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.dto.*;
import ru.practicum.event.enums.EventSortType;
import ru.practicum.event.enums.EventStateType;
import ru.practicum.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventService {
    Set<EventDto> getEventsByAdmin(List<Long> users, List<EventStateType> states, List<Long> categories,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto patchEventByAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    Set<EventShortDto> getAllEventsByPrivate(Long userId, Pageable pageable);

    EventDto createEventByPrivate(Long userId, NewEventDto newEventDto);

    EventDto getEventByPrivate(Long userId, Long eventId);

    EventDto patchEventByPrivate(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<EventShortDto> getEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
                                          Integer from, Integer size, HttpServletRequest request);

    EventDto getEventByPublic(Long id, HttpServletRequest request);

    Event getEventById(Long eventId);

    Set<Event> getEventsByIds(List<Long> eventsId);

    Set<EventShortDto> toEventsShortDto(Set<Event> events);
}