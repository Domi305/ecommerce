package pl.github.dominik.ecommerce.application;

import lombok.val;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.Product;

@Component
class ProductDomainDtoConverter {

    public ProductDto convert(Product product) {
        return ProductDto.builder().id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .thumbnailUrl(product.getThumbnailUrl())
                .price(product.getPrice())
                .build();

    }

    public Product convert(ProductDto product) {
        val result = new Product();
        result.setId(product.getId());
        result.setTitle(product.getTitle());
        result.setDescription(product.getDescription());
        result.setThumbnailUrl(product.getThumbnailUrl());
        result.setPrice(product.getPrice());
        return result;
    }
}
