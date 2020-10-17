package pl.github.dominik.ecommerce.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {

    @PostMapping("/login")
    void login(@RequestBody UserCredentials userCredentials) {
    }
}
