package ru.practicum.event.dto;

import lombok.*;
import ru.practicum.event.enums.StateActionUserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(builderMethodName = "userBuilder")
public class UpdateEventUserRequest extends UpdateEventRequest {
    private StateActionUserType stateAction;
}