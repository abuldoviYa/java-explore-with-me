package ru.practicum.event.service;

import ru.practicum.StatsView;
import ru.practicum.event.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StatsService {
    void addHit(HttpServletRequest request);

    List<StatsView> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    Map<Long, Long> getViews(Set<Event> events);

    Map<Long, Long> getConfirmedRequests(Set<Event> events);
}
