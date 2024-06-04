package ru.practicum;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class StatsView {
    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private Long hits;
}