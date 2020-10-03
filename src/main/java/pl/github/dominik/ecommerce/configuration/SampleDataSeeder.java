package pl.github.dominik.ecommerce.configuration;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.domain.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Profile("sample-data")
@Service
@Transactional
@RequiredArgsConstructor
public class SampleDataSeeder {

    private final AuthorRepository authorRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final ProductRepository productRepository;

    @PostConstruct
    public void seed() {
        if (!isDatabaseEmpty()) {
            return;
        }

        val janKowalski = generateAuthor("Jan Kowalski");

        val clothes = generateCategory("Odziez", null);
        val childClothes = generateCategory("Odziez dziecieca", clothes);
        val manClothes = generateCategory("Odziez meska", clothes);
        val womanClothes = generateCategory("Odziez damska", clothes);

        val drinks = generateCategory("Napoje", null);
        val nonAlcohol = generateCategory("Napoje alkoholowe", drinks);
        val stillDrinks = generateCategory("Napoje niegazowane", drinks);
        val fizzyDrinks = generateCategory("Napoje gazowane", drinks);

        productRepository.saveAll(List.of(
                Product.builder()
                        .title("Szmata")
                        .description("Stara, podarta, ale lepsza niz nic")
                        .thumbnailUrl("")
                        .price(1.00)
                        .author(janKowalski)
                        .category(clothes)
                        .type(ProductType.CLOTHES)
                        .build(),
                Product.builder()
                        .title("Buty")
                        .description("Lepsze to niz chodzic na bosaka")
                        .thumbnailUrl("")
                        .price(1.00)
                        .author(janKowalski)
                        .category(clothes)
                        .type(ProductType.CLOTHES)
                        .build()
        ));

    }

    private boolean isDatabaseEmpty() {
        return authorRepository.count() == 0 &&
                productCategoryRepository.count() == 0 &&
                productRepository.count() == 0;
    }

    private Author generateAuthor(String fullName) {
        String[] nameParts = fullName.split("");

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
