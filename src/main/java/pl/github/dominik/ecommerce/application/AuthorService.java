package pl.github.dominik.ecommerce.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.github.dominik.ecommerce.domain.Author;
import pl.github.dominik.ecommerce.domain.AuthorRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    private final AuthorDomainDtoConverter converter;

    public AuthorDto save(String firstName, String lastName) {
        var entity = new Author();
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity = repository.save(entity);
        return converter.convert(entity);
    }

    public void remove(long authorId) {
        repository.deleteById(authorId);
    }

    public Page<AuthorDto> list(Pageable page) {
        return repository.findAll(page).map(converter::convert);
    }

    public Optional<AuthorDto> get(Long authorId) {
        return repository.findById(authorId).map(converter::convert);
    }
}
