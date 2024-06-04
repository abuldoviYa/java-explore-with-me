package ru.practicum.event.dto;

import lombok.*;
import ru.practicum.event.enums.StateActionAdminType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(builderMethodName = "adminBuilder")
public class UpdateEventAdminRequest extends UpdateEventRequest {
    private StateActionAdminType stateAction;
}