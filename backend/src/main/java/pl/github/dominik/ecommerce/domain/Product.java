package pl.github.dominik.ecommerce.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // kontroluje sposob w jaki ID sie generuje
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnailUrl", nullable = false)
    private String thumbnailUrl;

    @OneToOne(optional = false)
    @JoinColumn(name = "category")
    private ProductCategory category;

    @Column(name = "price", nullable = false)
    private double price; //jezeli nie mnozymy albo dodajemy

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING) // obowiazkowa adnotacja przy enumach
    private ProductType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author")
    private Author author;

    @SuppressWarnings("unused")
    protected Product() {
    }

    @Builder
    private Product(Long id, String title, String description, String thumbnailUrl, ProductCategory category, double price, ProductType type, Author author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
        this.price = price;
        this.type = type;
        this.author = author;
    }
}
