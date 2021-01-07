package pl.github.dominik.ecommerce.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Profile({"sample-data", "test"})
@Component
@Transactional
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class SampleDataFixture {

    @Getter(AccessLevel.NONE)
    private final AuthorRepository authorRepository;

    @Getter(AccessLevel.NONE)
    private final ProductRepository productRepository;

    @Getter(AccessLevel.NONE)
    private final ProductCategoryRepository productCategoryRepository;

    @Getter(AccessLevel.NONE)
    private final UserRepository userRepository;

    @Getter(AccessLevel.NONE)
    private final EntityManager entityManager;

    private Author janKowalski;
    private Author karolinaNowak;
    private ProductCategory clothes;
    private ProductCategory childClothes;
    private ProductCategory manClothes;
    private ProductCategory womanClothes;
    private ProductCategory drinks;
    private ProductCategory nonAlcohol;
    private ProductCategory stillDrinks;
    private ProductCategory fizzyDrinks;
    private Product szmata;
    private Product buty;
    private User customer;
    private User admin;

    public boolean shouldBeSaved() {
        return authorRepository.count() == 0 &&
                productCategoryRepository.count() == 0 &&
                productRepository.count() == 0;
    }

    public void save() {
        janKowalski = generateAuthor("Jan Kowalski");
        karolinaNowak = generateAuthor("Karolina Nowak");

        clothes = generateCategory("Odziez", null);
        childClothes = generateCategory("Odziez dziecieca", clothes);
        manClothes = generateCategory("Odziez meska", clothes);
        womanClothes = generateCategory("Odziez damska", clothes);

        drinks = generateCategory("Napoje", null);
        nonAlcohol = generateCategory("Napoje nie alkoholowe", drinks);
        stillDrinks = generateCategory("Napoje niegazowane", drinks);
        fizzyDrinks = generateCategory("Napoje gazowane", drinks);

        szmata = productRepository.save(Product.builder()
                .title("Szmata")
                .description("Stara, podarta, ale lepsza niz nic")
                .thumbnailUrl("https://c.allegroimg.com/original/01f1c8/76c094d142678adc5092eb020b5c/SZARA-SCIERKA-DO-PODLOGI-szmata-czysciwo-szmatka")
                .price(1.00)
                .author(janKowalski)
                .category(clothes)
                .type(ProductType.CLOTHES)
                .build());

        buty = productRepository.save(Product.builder()
                .title("Buty")
                .description("Lepsze to niz chodzic na bosaka")
                .thumbnailUrl("http://google.com")
                .price(1.00)
                .author(janKowalski)
                .category(clothes)
                .type(ProductType.CLOTHES)
                .build());

        admin = userRepository.save(User.builder()
                .login("admmin@example.com")
                .password("noop")
                .role(User.Role.ADMIN)
                .contactPreference(User.ContactPreference.EMAIL)
                .avatarUrl("http://google.com")
                .country("Nowhere")
                .city("Nowhere")
                .street("Homeless")
                .zipCode("00-000")
                .build());

        customer = userRepository.save(User.builder()
                .login("customer@example.com")
                .password("{noop}")
                .role(User.Role.CUSTOMER)
                .contactPreference(User.ContactPreference.EMAIL)
                .avatarUrl("http://google.com")
                .country("Nowhere")
                .city("Nowhere")
                .street("Homeless")
                .zipCode("00-001")
                .build());
    }

    public void remove() {
        entityManager.clear();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
    }

    private Author generateAuthor(String fullName) {
        String[] nameParts = fullName.split(" ");

        if (nameParts.length != 2) {
            throw new IllegalArgumentException("fullName not in correct format");
        }

        Author result = new Author();
        result.setFirstName(nameParts[0]);
        result.setLastName(nameParts[1]);
        return authorRepository.save(result);
    }

    private ProductCategory generateCategory(String name, ProductCategory parentCategory) {
        ProductCategory result = new ProductCategory();
        result.setName(name);
        result.setParentCategory(parentCategory);
        return productCategoryRepository.save(result);
    }
}
