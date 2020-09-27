package pl.github.dominik.ecommerce.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository implements JpaRepository<Product, Long> {
}
