package ru.practicum;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class StatsView {
    @NotBlank
    @Size(min = 3, max = 25)
    private String app;

    @NotBlank
    @Size(min = 3, max = 25)
    private String uri;

    @NotBlank
    private Long hits;
}