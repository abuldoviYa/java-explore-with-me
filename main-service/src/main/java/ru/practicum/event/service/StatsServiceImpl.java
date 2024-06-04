package ru.practicum.event.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointRequest;
import ru.practicum.StatsClient;
import ru.practicum.StatsView;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.util.MainConstantsUtil.DT_FORMATTER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsClient statsClient;
    private final RequestRepository requestRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value(value = "${app.name}")
    private String appName;

    @Override
    public void addHit(HttpServletRequest request) {
        log.info("A request has been sent to register a request to the statistics " +
                "server with the request parameters = {}", request);
        EndpointRequest endpointRequest = EndpointRequest
                .builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DT_FORMATTER))
                .build();
        statsClient.postRequest(endpointRequest);
    }

    @Override
    public List<StatsView> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Sent a request to get statistics to the statistics server with parameters " +
                "start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);

        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);

        try {
            return Arrays.asList(mapper.readValue(mapper.writeValueAsString(response.getBody()), StatsView[].class));
        } catch (IOException exception) {
            throw new ClassCastException(exception.getMessage());
        }
    }

    @Override
    public Map<Long, Long> getViews(Set<Event> events) {
        log.info("A request was sent to get statistics of non-unique visits in the form of Map<eventId, count> " +
                "for a list of events.");

        Map<Long, Long> views = new HashMap<>();

        Set<Event> publishedEvents = getPublished(events);

        if (events.isEmpty()) {
            return views;
        }

        Optional<LocalDateTime> minPublishedOn = publishedEvents.stream()
                .map(Event::getPublishedOn)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo);

        if (minPublishedOn.isPresent()) {
            LocalDateTime start = minPublishedOn.get();
            LocalDateTime end = LocalDateTime.now();
            List<String> uris = publishedEvents.stream()
                    .map(Event::getId)
                    .map(id -> ("/events/" + id))
                    .collect(Collectors.toList());

            List<StatsView> stats = getStats(start, end, uris, null);
            stats.forEach(stat -> {
                Long eventId = Long.parseLong(stat.getUri()
                        .split("/", 0)[2]);
                views.put(eventId, views.getOrDefault(eventId, 0L) + stat.getHits());
            });
        }

        return views;
    }

    @Override
    public Map<Long, Long> getConfirmedRequests(Set<Event> events) {
        List<Long> eventsId = getPublished(events).stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> requestStats = new HashMap<>();

        if (!eventsId.isEmpty()) {
            requestRepository.getConfirmedRequests(eventsId)
                    .forEach(stat -> requestStats.put(stat.getEventId(), stat.getConfirmedRequests()));
        }

        return requestStats;
    }

    private Set<Event> getPublished(Set<Event> events) {
        return events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .collect(Collectors.toSet());
    }
}
