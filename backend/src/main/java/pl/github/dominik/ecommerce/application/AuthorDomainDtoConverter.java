package pl.github.dominik.ecommerce.application;

import lombok.val;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.Author;

@Component
class AuthorDomainDtoConverter {

    public AuthorDto convert(Author author) {
        if (author == null) {
            return null;
        }

        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public Author convert(AuthorDto author) {
        if (author == null) {
            return null;
        }

        val result = new Author();
        result.setId(author.getId());
        result.setFirstName(author.getFirstName());
        result.setLastName(author.getLastName());
        return result;
    }
}
