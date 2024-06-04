package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointRequest;
import ru.practicum.StatsConstants;
import ru.practicum.StatsView;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Stats;
import ru.practicum.repository.RequestRepository;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final RequestRepository requestRepository;
    private final StatsMapper statsMapper;

    @Override
    public void addRequest(EndpointRequest endpointRequest) {

        Stats request = statsMapper.toStats(endpointRequest, LocalDateTime.parse(endpointRequest.getTimestamp(), StatsConstants.DT_FORMATTER));
        requestRepository.save(request);
    }

    @Override
    public List<StatsView> getRequestsWithViews(LocalDateTime startDate, LocalDateTime endDate, List<String> uris, Boolean isUnique) {
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("Wrong dates");
        }
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
