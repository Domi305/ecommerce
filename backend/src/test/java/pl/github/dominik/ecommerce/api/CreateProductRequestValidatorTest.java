package pl.github.dominik.ecommerce.api;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.validation.Errors;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CreateProductRequestValidatorTest {

    private CreateProductRequestValidator validator = new CreateProductRequestValidator();

    //TODO rename
    @Test
    void supportsProductDtoClass() {
        assertTrue(validator.supports(CreateProductRequest.class));
    }

    @Test
    void doesntSupportsObjectClass() {
        assertFalse(validator.supports(Objects.class));
    }

    @Test
    void aValidProductShouldTriggerNoErrors() {
        val product = aValidProduct();
        val errors = mock(Errors.class);

        validator.validate(product, errors);

        verify(errors, times(0)).rejectValue(anyString(), anyString());
    }

/*    @Test
    void aProductWithIdSetShouldTriggerOneError() {
        val product = aProductHavingIdSet();
        val errors = mock(Errors.class);
        val fieldNameCapture = ArgumentCaptor.forClass(String.class);
        val errorCodeCapture = ArgumentCaptor.forClass(String.class);

        validator.validate(product, errors);

        verify(errors, times(1)).rejectValue(fieldNameCapture.capture(), errorCodeCapture.capture());
        assertThat(fieldNameCapture.getValue()).isEqualTo("id");
        assertThat(errorCodeCapture.getValue()).isEqualTo("PRESENT");
    }*/

    @Test
    void aProductHavingTooLongDescriptionShouldTriggerOneError() {
        val product = aProductHavingTooLongDescription();
        val errors = mock(Errors.class);
        val fieldNameCapture = ArgumentCaptor.forClass(String.class);
        val errorCodeCapture = ArgumentCaptor.forClass(String.class);

        validator.validate(product, errors);

        verify(errors, times(1)).rejectValue(fieldNameCapture.capture(), errorCodeCapture.capture());
        assertThat(fieldNameCapture.getValue()).isEqualTo("description");
        assertThat(errorCodeCapture.getValue()).isEqualTo("TOO_LONG");
    }

    public CreateProductRequest aValidProduct() {
        return CreateProductRequest.builder()
                .title("Test Product")
                .description("Some description")
                .thumbnailUrl("https://wow.olympus.eu/webfile/img/1632/oly_testwow_stage.jpg?x=1024")
                .price(9.99)
                .authorId(123L)
                .categoryId(456L)
                .type("CLOTHES")
                .build();
    }

/*    public ProductDto aProductHavingIdSet() {
        return ProductDto.builder()
                .id(123L)
                .title("Test product")
                .description("some desc")
                .thumbnailUrl("https://wow.olympus.eu/webfile/img/1632/oly_testwow_stage.jpg?x=1024")
                .price(9.99)
                .build();
    }*/

    public CreateProductRequest aProductHavingTooLongDescription() {
        return CreateProductRequest.builder()
                .title("Test product")
                .description("test ".repeat(200))
                .thumbnailUrl("https://wow.olympus.eu/webfile/img/1632/oly_testwow_stage.jpg?x=1024")
                .price(9.99)
                .authorId(123L)
                .categoryId(456L)
                .type("CLOTHES")
                .build();
    }
}