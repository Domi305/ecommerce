package pl.github.dominik.ecommerce.api;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.github.dominik.ecommerce.application.ProductDto;
import pl.github.dominik.ecommerce.application.ProductService;
import pl.github.dominik.ecommerce.application.UserDto;
import pl.github.dominik.ecommerce.application.UserService;
import pl.github.dominik.ecommerce.domain.User;

import java.util.List;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "users", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRegistrationRequestValidator userRegistrationRequestValidator;

    @GetMapping(path = "")
    public List<UserDto> listUsers(Pageable page) {
        return userService.list(page).toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return userService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Errors> registerUser(@RequestBody UserRegistrationRequest request, Errors errors) {
        userRegistrationRequestValidator.validate(request, errors);

        if (!errors.hasErrors()) {
            val author = userService.register(request);
            val link = ControllerLinkBuilder.linkTo(methodOn(UserController.class).getUser(author.getId()));

            return ResponseEntity.created(link.toUri()).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
