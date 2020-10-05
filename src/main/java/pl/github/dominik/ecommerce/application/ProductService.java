package pl.github.dominik.ecommerce.application;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.api.CreateProductRequest;
import pl.github.dominik.ecommerce.domain.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    private final ProductDomainDtoConverter converter;

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
}