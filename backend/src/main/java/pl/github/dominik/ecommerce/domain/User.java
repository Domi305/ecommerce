package pl.github.dominik.ecommerce.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private Address address;

    @Column(name = "avatar_url", nullable = false)
    private String avatarUrl;

    @Column(name = "contact_preference", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactPreference contactPreference;

    public enum Role {
        ADMIN,
        CUSTOMER
    }

    public enum ContactPreference {
        MAIL,
        EMAIL
    }

    @SuppressWarnings("unused")  // needed by Hibernate
    protected User() {
    }

    @Builder
    private User(Long id,
                 @NonNull String login,
                 @NonNull String password,
                 @NonNull Role role,
                 @NonNull String avatarUrl,
                 @NonNull ContactPreference contactPreference,
                 @NonNull String country,
                 @NonNull String city,
                 @NonNull String street,
                 @NonNull String zipCode) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.address = new Address();
        this.address.setCountry(country);
        this.address.setCity(city);
        this.address.setStreet(street);
        this.address.setZipCode(zipCode);
        this.avatarUrl = avatarUrl;
        this.contactPreference = contactPreference;
    }
}
