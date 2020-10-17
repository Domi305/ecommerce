package pl.github.dominik.ecommerce.application;

import lombok.Value;
import pl.github.dominik.ecommerce.domain.Product;

@Value
public class OrderEntryDto {

    private long productId;

    private double price;

    private double count;
}
