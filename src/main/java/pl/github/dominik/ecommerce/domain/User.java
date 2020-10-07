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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // kontroluje sposob w jaki ID sie generuje
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private @NonNull byte[] password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private Address address;

    @Column(name = "contact_preference", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactPreference contactPreference;

    @Column(name = "avatar_Url", nullable = false)
    private String avatarUrl;

    public enum Role {
        ADMIN,
        CUSTOMER
    }

    @Embeddable
    @Data
    public static class Address {

        @Column(name = "country", nullable = false)
        private String country;

        @Column(name = "city", nullable = false)
        private String city;

        @Column(name = "street", nullable = false)
        private String street;

        @Column(name = "zip_code", nullable = false)
        private String zipCode;
    }

    public enum ContactPreference {
        MAIL,
        EMAIL
    }

    protected User() {
    }

    @Builder
    private User(Long id,
                 @NonNull String login,
                 @NonNull byte[] password,
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
