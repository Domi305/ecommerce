package pl.github.dominik.ecommerce.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.github.dominik.ecommerce.application.CreateProductRequest;
import pl.github.dominik.ecommerce.application.ProductDto;
import pl.github.dominik.ecommerce.application.ProductService;
import pl.github.dominik.ecommerce.domain.Product;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "products", produces = "application/json")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductValidator productValidator;

    @GetMapping(path = "")
    public List<ProductDto> listProducts(Pageable page) {
        return productService.list(page).toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity <ProductDto> getProduct(@PathVariable long id) {
        return productService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Void> addProduct(@RequestBody CreateProductRequest request, Errors errors) {
        productValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            val product = productService.add(request);
            val link = ControllerLinkBuilder.linkTo(methodOn(ProductController.class).getProduct(product.getId()));
        return ResponseEntity.created(link.toUri()).build();
        } else {
        return ResponseEntity.badRequest().build();
        }
    }
}
