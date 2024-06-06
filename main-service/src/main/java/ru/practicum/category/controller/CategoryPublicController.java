package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_FROM;
import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class CategoryPublicController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        return categoryService.getAll(PageRequest.of(from / size, size));
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable Long catId) {
        return categoryService.getById(catId);
    }
}
