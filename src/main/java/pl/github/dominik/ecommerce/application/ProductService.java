package pl.github.dominik.ecommerce.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.domain.ProductRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    private final ProductDomainDtoConverter converter;

    public ProductDto add(ProductDto productDto) {
        var product = converter.convert(productDto)
        product = repository.save(product);
        return converter.convert(product);
    }
}
