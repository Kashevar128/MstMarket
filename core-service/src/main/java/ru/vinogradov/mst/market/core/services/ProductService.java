package ru.vinogradov.mst.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.vinogradov.mst.market.api.ProductDto;
import ru.vinogradov.mst.market.core.exceptions.ResourceNotFoundException;
import ru.vinogradov.mst.market.core.entities.Product;
import ru.vinogradov.mst.market.core.exceptions.TheProductExistsExeption;
import ru.vinogradov.mst.market.core.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(int page, int pageSize, Specification<Product> specification) {
        return productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(
                () -> new ResourceNotFoundException("Категория с названием: " +
                        productDto.getCategoryTitle() + " не найдена")));
        if (productRepository.existsByTitle(productDto.getTitle())) {
            throw new TheProductExistsExeption("Такой продукт уже существует");
        }
        productRepository.save(product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
