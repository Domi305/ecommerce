package pl.github.dominik.ecommerce.api;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;
import pl.github.dominik.ecommerce.application.ProductDto;
import pl.github.dominik.ecommerce.application.ProductService;

import org.springframework.data.domain.Pageable;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private static final Pageable ALL_PRODUCTS_PAGE = PageRequest.of(0, 9999);

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        service.add(ProductDto.builder()
                .title("Sample product 1")
                .description("Some description")
                .thumbnailUrl("http://who.car.es")
                .price(9.99)
                .build());

        service.add(ProductDto.builder()
                .title("Sample product 2")
                .description("some description")
                .thumbnailUrl("http://who.car.es")
                .price(9.99)
                .build());
    }

    @AfterEach
    public void tearDown() {
        val allProducts = service.list(ALL_PRODUCTS_PAGE);
        allProducts.forEach(service::remove);
    }

    @Test
    public void listingAllProducts() throws Exception {
        mockMvc.perform(get("/products?page=0&size=20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(2)));
    }

    @Test
    public void gettingExistingProduct() throws Exception {
        val product = service.list(PageRequest.of(0,1)).getContent().get(0);

        mockMvc.perform(get("/products" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.title", is(product.getTitle())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.thumbnailUrl", is(product.getThumbnailUrl())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));
    }

    @Test
    public void gettingNotExistingProduct() throws Exception {
        mockMvc.perform(get("/products"+ 999_999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addingNeProduct() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Sample product added\", \"description\": \"bla bla bla\", \"thumbnailUrl\": \"http://who.car.es\", \"price\": 5.00}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}
