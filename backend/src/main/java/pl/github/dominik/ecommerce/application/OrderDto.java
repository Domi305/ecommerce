package pl.github.dominik.ecommerce.application;


import lombok.Builder;
import lombok.Value;
import pl.github.dominik.ecommerce.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderDto {

    private Long id;

    private long customer;

    private AddressDto customerAddress;

    private AddressDto deliveryAddress;

    private LocalDateTime orderTime;

    private OrderState state;

    private List<OrderEntryDto> entries;

}