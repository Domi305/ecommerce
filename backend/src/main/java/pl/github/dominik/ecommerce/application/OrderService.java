package pl.github.dominik.ecommerce.application;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.github.dominik.ecommerce.api.OrderRequest;
import pl.github.dominik.ecommerce.domain.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final OrderDomainDtoConverter orderConverter;

    private final UserRepository userRepository;

    private final AddressDomainDtoConverter addressConverter;

    private final ProductRepository productRepository;

    public Optional<OrderDto> get(long orderId) {
        return repository.findById(orderId).map(orderConverter::convert);
    }

    public OrderDto makeOrder(long userId, @NonNull OrderRequest orderRequest) {
        var order = new Order();
        order.setCustomer(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("unknown user")));
        order.setCustomerAddress(addressConverter.convert(orderRequest.getCustomerAddress()));
        order.setDeliveryAddress(addressConverter.convert(orderRequest.getDeliveryAddress()));
        order.setState(Order.State.NEW);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalCost(orderRequest.getEntries()
                .stream()
                .mapToDouble(entry -> productRepository.findById(entry.getProductId()).map(Product::getPrice).orElse(Double.NaN) * entry.getCount())
                .sum());
        order.setEntries(orderRequest.getEntries()
                .stream()
                .map(entry -> {
                    val product = productRepository.findById(entry.getProductId()).orElseThrow(IllegalMonitorStateException::new);
                    val domainEntry = new Order.Entry();
                    domainEntry.setProduct(product);
                    domainEntry.setPrice(product.getPrice());
                    domainEntry.setCount(entry.getCount());
                    return domainEntry;
                })
                .collect(Collectors.toList()));

        order = repository.save(order);
        return orderConverter.convert(order);
    }

    public Optional<OrderDto> updateOrderState(long orderId, OrderState newState) {
        return repository.findById(orderId)
                .map(order -> {
                    order.setState(Order.State.valueOf(newState.name()));
                    return order;
                })
                .map(orderConverter::convert);
    }

    public Optional<OrderDto> cancelOrder(long orderId) {
        return updateOrderState(orderId, OrderState.CANCELLED);
    }
}
