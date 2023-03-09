package ru.vinogradov.mst.market.core.mappers;

import org.springframework.stereotype.Component;
import ru.vinogradov.mst.market.api.CategoryDto;
import ru.vinogradov.mst.market.core.entities.Category;

@Component
public class CategoryMapper {

    public CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .build();
    }
}
