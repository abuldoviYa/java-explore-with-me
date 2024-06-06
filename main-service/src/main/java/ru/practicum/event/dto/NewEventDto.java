package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NewEventDto {
    @NotBlank
    @Size(min = MIN_LENGTH_ANNOTATION, max = MAX_LENGTH_ANNOTATION)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = MIN_LENGTH_DESCRIPTION, max = MAX_LENGTH_DESCRIPTION)
    private String description;

    @NotNull
    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private LocationDto location;

    private boolean paid = false;

    @PositiveOrZero
    private int participantLimit = 0;

    private boolean requestModeration = true;

    @NotBlank
    @Size(min = MIN_LENGTH_TITLE, max = MAX_LENGTH_TITLE)
    private String title;
}
