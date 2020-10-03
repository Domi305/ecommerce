package pl.github.dominik.ecommerce.application;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductRequest {

    private String title;

    private String description;

    private String thumbnailUrl;

    private Double price;

    private String type;

    private Long authorId;

    private Long categoryId;

}
