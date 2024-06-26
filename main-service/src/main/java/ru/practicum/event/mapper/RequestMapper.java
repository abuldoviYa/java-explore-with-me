package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.event.dto.ParticipationRequestDto;
import ru.practicum.event.model.Request;


@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "event", expression = "java(request.getEvent().getId())")
    @Mapping(target = "requester", expression = "java(request.getRequester().getId())")
    ParticipationRequestDto toParticipationRequestDto(Request request);
}