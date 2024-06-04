package ru.practicum.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.enums.EventStateType;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.DT_FORMAT;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    Long id;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;
    private EventStateType state;
    private String title;
    private Long views;
}