package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        log.info("Adding new Category {}", newCategoryDto);

        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.newCategoryDtoToCategory(newCategoryDto)));
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        log.info("Output of all Categories with pagination {}", pageable);

        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long catId) {
        log.info("Output of a Category with an id {}", catId);

        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("There is no Category with this id."));

        return categoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto update(Long catId, CategoryDto categoryDto) {
        log.info("Updating a Category with id {} new parameters {}", catId, categoryDto);

        categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("There is no Category with this id."));

        categoryDto.setId(catId);
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.categoryDtoToCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void deleteById(Long catId) {
        log.info("Deleting a Category with an id {}", catId);

        categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("There is no Category with this id."));

        categoryRepository.deleteById(catId);
    }

    @Override
    public Category getCategoryById(Long catId) {
        log.info("Output of a Category with id {}", catId);

        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("There is no Category with this id."));
    }
}
