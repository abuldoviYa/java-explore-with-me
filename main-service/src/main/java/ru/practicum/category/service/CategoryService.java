package ru.practicum.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategoryDto);

    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto getById(Long catId);

    CategoryDto update(Long catId, CategoryDto categoryDto);

    void deleteById(Long catId);

    Category getCategoryById(Long catId);
}