package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.event.dto.EventShortDto;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    Long id;
    String title;
    Boolean pinned;
    Set<EventShortDto> events;
}
