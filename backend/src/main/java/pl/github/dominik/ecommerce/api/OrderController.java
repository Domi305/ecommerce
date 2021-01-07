package pl.github.dominik.ecommerce.api;


import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.github.dominik.ecommerce.application.OrderDto;
import pl.github.dominik.ecommerce.application.OrderService;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "orders", produces = "application/json")
public class OrderController {

    private final OrderService orderService;

    private final OrderRequestValidator orderRequestValidator;


    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable long id) {
        return orderService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Errors> makeOrder(@RequestBody OrderRequest request, Errors errors) {
        orderRequestValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            val author = orderService.makeOrder(0L, request);
            val link = ControllerLinkBuilder.linkTo(methodOn(OrderController.class).getOrder(author.getId()));

        return ResponseEntity.created(link.toUri()).build();
        } else {
            // TODO: add error to response
            return ResponseEntity.badRequest().build();
        }
    }
}
