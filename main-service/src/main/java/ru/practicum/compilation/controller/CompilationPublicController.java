package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_FROM;
import static ru.practicum.util.MainConstantsUtil.PAGE_DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = PAGE_DEFAULT_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = PAGE_DEFAULT_SIZE) @Positive Integer size) {
        return compilationService.getAll(pinned, PageRequest.of(from / size, size));
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getById(@PathVariable Long compId) {
        return compilationService.getById(compId);
    }
}
