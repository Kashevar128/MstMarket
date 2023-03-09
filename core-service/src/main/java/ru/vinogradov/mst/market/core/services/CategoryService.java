package ru.vinogradov.mst.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vinogradov.mst.market.api.CategoryDto;
import ru.vinogradov.mst.market.core.entities.Category;
import ru.vinogradov.mst.market.core.mappers.CategoryMapper;
import ru.vinogradov.mst.market.core.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<CategoryDto> getAllCategoriesDto() {
        return getAllCategories().stream().map(categoryMapper::mapCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    public List<String> getTitleCategory() {
        return getAllCategories().stream().map(Category::getTitle).collect(Collectors.toList());
    }
}
