package ru.practicum.service;

import org.springframework.stereotype.Service;
import ru.practicum.RequestDTO;
import ru.practicum.RequestOutDTO;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    void addRequest(RequestDTO requestDto);

    List<RequestOutDTO> getRequestsWithViews(LocalDateTime startDate, LocalDateTime endDate, List<String> uris, Boolean isUnique);
}
