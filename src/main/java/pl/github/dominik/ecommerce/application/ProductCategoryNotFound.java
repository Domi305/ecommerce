package pl.github.dominik.ecommerce.application;

import lombok.Value;
import pl.github.dominik.ecommerce.domain.ProductCategory;

import javax.persistence.EntityNotFoundException;
@Value
public final class ProductCategoryNotFound extends EntityNotFoundException {

    private long productCategoryId;

    public ProductCategoryNotFound(long productCategoryId) {
        super("product category" + productCategoryId + "not found");
        this.productCategoryId = productCategoryId;
    }
}
