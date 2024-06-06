package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NewCompilationDto {
    @NotBlank
    @Size(min = 3, max = 50)
    String title;

    boolean pinned = false;
    List<Long> events;
}