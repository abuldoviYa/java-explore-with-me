package ru.practicum.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CategoryDto {
    private Long id;
    @Size(max = 50)
    @NotBlank
    private String name;
}