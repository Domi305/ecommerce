package pl.github.dominik.ecommerce.application;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductCategoryDto {

    private Long id;

    private String name;

    private Long parentCategoryId;
}