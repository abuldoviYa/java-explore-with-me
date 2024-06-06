package ru.practicum.user.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserShortDto {
    private Long id;
    private String name;
}
