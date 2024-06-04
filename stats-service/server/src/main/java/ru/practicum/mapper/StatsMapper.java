package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.EndpointRequest;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    @Mapping(target = "timestamp", expression = "java(timestamp)")
    Stats toStats(EndpointRequest endpointHit, LocalDateTime timestamp);
}
