package pl.github.dominik.ecommerce.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.*;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    private final EntityManager entityManager;

    private Author janKowalski;
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

    public boolean shouldBeSaved() {
        return authorRepository.count() == 0 &&
                productCategoryRepository.count() == 0 &&
                productRepository.count() == 0;
    }

    public void save() {
        janKowalski = generateAuthor("Jan Kowalski");

        clothes = generateCategory("Odziez", null);
        childClothes = generateCategory("Odziez dziecieca", clothes);
        manClothes = generateCategory("Odziez meska", clothes);
        womanClothes = generateCategory("Odziez damska", clothes);

        drinks = generateCategory("Napoje", null);
        nonAlcohol = generateCategory("Napoje alkoholowe", drinks);
        stillDrinks = generateCategory("Napoje niegazowane", drinks);
        fizzyDrinks = generateCategory("Napoje gazowane", drinks);

        szmata = productRepository.save(Product.builder()
                .title("Szmata")
                .description("Stara, podarta, ale lepsza niz nic")
                .thumbnailUrl("")
                .price(1.00)
                .author(janKowalski)
                .category(clothes)
                .type(ProductType.CLOTHES)
                .build());

        buty = productRepository.save(Product.builder()
                .title("Buty")
                .description("Lepsze to niz chodzic na bosaka")
                .thumbnailUrl("")
                .price(1.00)
                .author(janKowalski)
                .category(clothes)
                .type(ProductType.CLOTHES)
                .build());
    }

    public void remove() {
        entityManager.clear();

        Optional.ofNullable(buty).map(Product::getId).map(productRepository::getOne).ifPresent(productRepository::delete);
        Optional.ofNullable(szmata).map(Product::getId).map(productRepository::getOne).ifPresent(productRepository::delete);

        Optional.ofNullable(fizzyDrinks).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(stillDrinks).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(nonAlcohol).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(drinks).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);

        Optional.ofNullable(womanClothes).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(manClothes).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(childClothes).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);
        Optional.ofNullable(clothes).map(ProductCategory::getId).map(productCategoryRepository::getOne).ifPresent(productCategoryRepository::delete);

        Optional.ofNullable(janKowalski).map(Author::getId).map(authorRepository::getOne).ifPresent(authorRepository::delete);
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
