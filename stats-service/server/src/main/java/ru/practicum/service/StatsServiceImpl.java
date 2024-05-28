package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.RequestDTO;
import ru.practicum.RequestOutDTO;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.App;
import ru.practicum.model.Request;
import ru.practicum.repository.AppRepository;
import ru.practicum.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final AppRepository appRepository;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    public void addRequest(RequestDTO requestDto) {
        Optional<App> oApp = appRepository.findByName(requestDto.getApp());
        App app = oApp.orElseGet(() -> appRepository.save(new App(requestDto.getApp())));

        Request request = requestMapper.toRequest(requestDto);
        request.setApp(app);
        requestRepository.save(request);
    }

    @Override
    public List<RequestOutDTO> getRequestsWithViews(LocalDateTime startDate, LocalDateTime endDate, List<String> uris, Boolean isUnique) {
        if (isUnique) {
            if (uris == null || uris.isEmpty()) {
                return requestRepository.findUniqueIpRequestsWithoutUri(startDate, endDate);
            }
            return requestRepository.findUniqueIpRequestsWithUri(startDate, endDate, uris);
        } else {
            if (uris == null || uris.isEmpty()) {
                return requestRepository.findAllRequestsWithoutUri(startDate, endDate);
            }
            return requestRepository.findAllRequestsWithUri(startDate, endDate, uris);
        }

    }
}
