package pl.github.dominik.ecommerce.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.Errors;
import pl.github.dominik.ecommerce.application.ProductDto;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

    private ProductValidator validator = new ProductValidator();

    @Test
    void supportsProductDtoClass() {
        assertTrue(validator.supports(ProductDto.class));
    }

    @Test
    void doesntSupportsObjectClass() {
        assertFalse(validator.supports(Objects.class));
    }

    @Test
    void aValidProductShouldTriggerNoErrors() {
        Errors errors = new Errors() {
        }
    }

    public ProductDto aValidProduct() {
        return ProductDto.builder()
                .title("Test Product")
                .description("Some description")
                .thumbnailUrl("https://")
                .price(9.99)
                .build();
    }
}