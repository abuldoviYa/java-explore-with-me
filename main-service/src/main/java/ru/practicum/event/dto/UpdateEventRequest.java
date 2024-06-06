package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UpdateEventRequest {
    @Size(min = MIN_LENGTH_ANNOTATION, max = MAX_LENGTH_ANNOTATION)
    private String annotation;

    private Long category;

    @Size(min = MIN_LENGTH_DESCRIPTION, max = MAX_LENGTH_DESCRIPTION)
    private String description;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    @Valid
    private LocationDto location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = MIN_LENGTH_TITLE, max = MAX_LENGTH_TITLE)
    private String title;
}
