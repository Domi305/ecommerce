package pl.github.dominik.ecommerce.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // select * from products where author
    Page<Product> findByAuthorId(long authorId, Pageable page);

    Page<Product> findByCategoryId(long productCategoryId, Pageable page);
}
