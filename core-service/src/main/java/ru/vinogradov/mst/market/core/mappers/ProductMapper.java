package ru.vinogradov.mst.market.core.mappers;

import org.springframework.stereotype.Component;
import ru.vinogradov.mst.market.api.ProductDto;
import ru.vinogradov.mst.market.core.entities.Product;
import ru.vinogradov.mst.market.core.repositories.ProductRepository;

@Component
public class ProductMapper {

    public ProductDto mapProductToProductDto (Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .categoryTitle(product.getCategory().getTitle())
                .build();
    }
}
