package pl.github.dominik.ecommerce;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.github.dominik.ecommerce.configuration.SampleDataFixture;
import pl.github.dominik.ecommerce.domain.AuthorRepository;
import pl.github.dominik.ecommerce.domain.ProductCategoryRepository;
import pl.github.dominik.ecommerce.domain.ProductRepository;

import javax.persistence.EntityManager;

@TestConfiguration
public class SampleDataTestConfiguration {

    @Bean
    public SampleDataFixture sampleDataFixture(AuthorRepository authorRepository,
                                               ProductCategoryRepository productCategoryRepository,
                                               ProductRepository productRepository,
                                               EntityManager entityManager) {
        return new SampleDataFixture(authorRepository, productRepository, productCategoryRepository, entityManager);
    }
}
