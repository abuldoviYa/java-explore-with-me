package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.DT_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}