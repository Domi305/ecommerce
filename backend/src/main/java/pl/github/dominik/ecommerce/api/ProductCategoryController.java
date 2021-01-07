package pl.github.dominik.ecommerce.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.github.dominik.ecommerce.application.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "products/categories", produces = "application/json")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    private final CreateProductCategoryRequestValidator createProductCategoryRequestValidator;


    @GetMapping(path = "")
    public List<ProductCategoryDto> listProductCategories(@RequestParam(required = false) Long root) {
        return productCategoryService.list(root);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity <ProductCategoryDto> getProductCategory(@PathVariable long id) {
        return productCategoryService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Errors> addProductCategory(@RequestBody CreateProductCategoryRequest request, Errors errors) {
       createProductCategoryRequestValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            val author = productCategoryService.save(request.getName(), request.getParentCategoryId());
            val link = ControllerLinkBuilder.linkTo(methodOn(ProductCategoryController.class).getProductCategory(author.getId()));
        return ResponseEntity.created(link.toUri()).build();
        } else {
        return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping(path = "/{productCategoryId}", consumes = "application/json")
    public ResponseEntity<ProductCategoryDto> updateProductCategory(@PathVariable long productCategoryId,
                                                                    @RequestBody CreateProductCategoryRequest request, Errors errors) {
        createProductCategoryRequestValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            productCategoryService.rename(productCategoryId, request.getName());
            productCategoryService.changeParentCategory(productCategoryId, request.getParentCategoryId());

            return productCategoryService.get(productCategoryId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(path = "/{productCategoryId}", consumes = "application/json")
    public ResponseEntity<Errors> removeProductCategory(@PathVariable long productCategoryId,@RequestParam(defaultValue = "false") boolean wholeSubtree) {
        if (anyProductsRelated(productCategoryId)) {
        return ResponseEntity.badRequest().build();
        }

        if (wholeSubtree) {
            productCategoryService.removeAll(productCategoryId);
        } else {
            productCategoryService.removeOne(productCategoryId);
        }

        return ResponseEntity.noContent().build();
    }

    private boolean anyProductsRelated(long productCategoryId) {
        return !productService.findByAuthor(productCategoryId, PageRequest.of(0,1)).isEmpty();
    }
}
