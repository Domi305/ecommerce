package pl.github.dominik.ecommerce.domain;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // kontroluje sposob w jaki ID sie generuje
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnailUrl", nullable = false)
    private String thumbnailUrl;

    // private ProductCategory category;

    @Column(name = "price", nullable = false)
    private double price; //tylko jezeli nie mnozymy albo dodajemy

    // private Enum<?> type;

    // private User author;

}
