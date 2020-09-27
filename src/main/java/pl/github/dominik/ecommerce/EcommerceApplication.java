package pl.github.dominik.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.github.dominik.ecommerce.configuration.SecurityConfiguration;

// TODO: Enable security
@SpringBootApplication(exclude = {SecurityConfiguration.class})
public class EcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
