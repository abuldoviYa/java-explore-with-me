package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.event.enums.RequestStatusType;

import java.time.LocalDateTime;

import static ru.practicum.util.MainConstantsUtil.DT_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;

    @JsonFormat(pattern = DT_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;

    private RequestStatusType status;

}
