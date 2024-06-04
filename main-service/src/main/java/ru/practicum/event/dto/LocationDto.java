package ru.practicum.event.dto;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class LocationDto {
    @NotNull
    private Float lat;

    @NotNull
    private Float lon;
}