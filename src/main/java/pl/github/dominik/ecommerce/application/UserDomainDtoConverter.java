package pl.github.dominik.ecommerce.application;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.User;
import pl.github.dominik.ecommerce.domain.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDomainDtoConverter {

    private final UserRepository repository;

    public UserDto convert(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(UserDto.Role.valueOf(user.getRole().name()))
                .contactPreference(UserDto.ContactPreference.valueOf(user.getContactPreference().name()))
                .country(user.getAddress().getCountry())
                .city(user.getAddress().getCity())
                .street(user.getAddress().getStreet())
                .zipCode(user.getAddress().getZipCode())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public User convert(UserDto user) {
        val entity = repository.findById(user.getId());

        return User.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(entity.map(User::getPassword).orElse("{noop}"))
                .role(User.Role.valueOf(user.getRole().name()))
                .contactPreference(User.ContactPreference.valueOf(user.getContactPreference().name()))
                .country(user.getAddress().getCountry())
                .city(user.getAddress().getCity())
                .street(user.getAddress().getStreet())
                .zipCode(user.getAddress().getZipCode())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
