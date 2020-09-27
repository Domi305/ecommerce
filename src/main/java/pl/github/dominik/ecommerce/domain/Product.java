package pl.github.dominik.ecommerce.domain;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // kontroluje sposob w jaki ID sie generuje
    private Long id;

    private String title;

    private String description;

    private String thumbnailUrl;

    // private ProductCategory category;

    private double price; //tylko jezeli nie mnozymy albo dodajemy

    // private Enum<?> type;

    // private User author;

}
