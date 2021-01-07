package pl.github.dominik.ecommerce.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.github.dominik.ecommerce.application.AuthorDto;
import pl.github.dominik.ecommerce.application.AuthorService;
import pl.github.dominik.ecommerce.application.ProductService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "authors", produces = "application/json")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final AddAuthorValidator addAuthorValidator;

    private final ProductService productService;


    @GetMapping(path = "")
    public List<AuthorDto> listAuthors(Pageable page) {
        return authorService.list(page).toList();
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity <AuthorDto> getAuthor(@PathVariable long id) {
        return authorService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Void> addAuthor(@RequestBody AddAuthor request, Errors errors) {
        addAuthorValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            val author = authorService.save(request.getFirstName(), request.getLastName());
            val link = ControllerLinkBuilder.linkTo(methodOn(AuthorController.class).getAuthor(author.getId()));
        return ResponseEntity.created(link.toUri()).build();
        } else {
        return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping(path = "/{authorId}", consumes = "application/json")
    public ResponseEntity<Void> removeAuthor(@PathVariable long authorId) {
        if (anyProductsRelatedToAuthor(authorId)) {
            return ResponseEntity.badRequest().build();
        }
            authorService.get(authorId).map(AuthorDto::getId).ifPresent(authorService::remove);
        return ResponseEntity.noContent().build();
    }

    private boolean anyProductsRelatedToAuthor(long authorId) {
        return !productService.findByAuthor(authorId, PageRequest.of(0,1)).isEmpty();
    }
}
