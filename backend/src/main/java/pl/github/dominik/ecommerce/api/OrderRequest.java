package pl.github.dominik.ecommerce.api;

import lombok.Value;
import pl.github.dominik.ecommerce.application.AddressDto;

import java.util.List;

// TODO: check if Jackson works with getters returning Optional<Address>
@Value
public class OrderRequest {

    private AddressDto customerAddress;

    private AddressDto deliveryAddress;

    private List<Entry> entries;

    @Value
    public static class Entry {

        private long productId;

        private double count;
    }
}
