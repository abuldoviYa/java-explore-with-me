package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compId, UpdateCompilationDto updateCompilationRequest);

    void deleteById(Long compId);

    List<CompilationDto> getAll(Boolean pinned, Pageable pageable);

    CompilationDto getById(Long compId);
}