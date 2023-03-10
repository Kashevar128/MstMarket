package ru.vinogradov.mst.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vinogradov.mst.market.api.ProductDto;
import ru.vinogradov.mst.market.core.entities.Category;
import ru.vinogradov.mst.market.core.entities.Order;
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
        Sort sort = Sort.by("title");
        return productRepository.findAll(specification, PageRequest.of(page, pageSize, sort));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(ProductDto productDto) {
        Product product = productRepository.getById(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setVisible(true);
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

    @Transactional
    public void updateVisible(Long id, Boolean visible) {
        Product byId = productRepository.getById(id);
        byId.setVisible(visible);
        productRepository.save(byId);
    }
}
