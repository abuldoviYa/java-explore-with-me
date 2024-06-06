package ru.practicum.event.dto;

import lombok.*;
import ru.practicum.event.enums.RequestStatusActionType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EventRequestStatusUpdateRequest {
    @NotEmpty
    private List<Long> requestIds;

    @NotNull
    private RequestStatusActionType status;
}