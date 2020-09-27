package pl.github.dominik.ecommerce.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
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
    private double price; //jezeli nie mnozymy albo dodajemy

    // private Enum<?> type;

    // private User author;

}
