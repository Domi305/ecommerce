package pl.github.dominik.ecommerce.application;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.api.CreateProductRequest;
import pl.github.dominik.ecommerce.domain.ProductRepository;
import pl.github.dominik.ecommerce.domain.ProductType;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    private final ProductDomainDtoConverter converter;

    private final ProductCategoryService categoryService;

    public Page<ProductDto> list(@NonNull Pageable page) {
        return repository.findAll(page).map(converter::convert);
    }

    public Optional<ProductDto> get(long id) {
        return repository.findById(id).map(converter::convert);
    }

    public ProductDto add(CreateProductRequest createProductRequest) {
        var product = converter.convert(createProductRequest);
        product = repository.save(product);
        return converter.convert(product);
    }

    public ProductDto add(ProductDto productDto) {
        var product = converter.convert(productDto);
        product = repository.save(product);
        return converter.convert(product);
    }

    public void remove(ProductDto productDto) {
        remove(productDto.getId());
    }

    public void remove(long productId) {
        repository.deleteById(productId);
    }

    public Page<ProductDto> findByAuthor(long productCategoryId, @NonNull Pageable page) {
        return repository.findByAuthorId(productCategoryId, page).map(converter::convert);
    }

    public Page<ProductDto> search(String name, String type, Long categoryId, Double minPrice, Double maxPrice, Pageable page) {
        return repository.search(
                Optional.ofNullable(name).map(String::toLowerCase).orElse(null),
                type != null ? ProductType.valueOf(type) : null,
                categoryService.list(categoryId).stream().map(ProductCategoryDto::getId).collect(Collectors.toUnmodifiableSet()),
                minPrice,
                maxPrice,
                page)
                .map(converter::convert);
    }
}