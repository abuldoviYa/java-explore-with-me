package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateCompilationDto {
    @Size(min = 3, max = 50)
    String title;

    Boolean pinned;
    List<Long> events;
}