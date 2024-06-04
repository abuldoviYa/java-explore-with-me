package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.EndpointRequest;
import ru.practicum.StatsView;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    void addRequest(EndpointRequest requestDto);

    List<StatsView> getRequestsWithViews(LocalDateTime startDate, LocalDateTime endDate, List<String> uris, Boolean isUnique);
}
