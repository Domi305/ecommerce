package pl.github.dominik.ecommerce.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.github.dominik.ecommerce.SampleDataTestConfiguration;
import pl.github.dominik.ecommerce.configuration.SampleDataFixture;
import pl.github.dominik.ecommerce.domain.User;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(SampleDataTestConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SampleDataFixture fixture;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        fixture.save();
    }

    @AfterEach
    public void tearDown() {
        fixture.remove();
    }

    @Test
    public void listingAllUsers() throws Exception {
        mockMvc.perform(get("/users?page=0&size=20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void gettingExistingUser() throws Exception {
        val customer = fixture.customer();

        mockMvc.perform(get("/users/" + customer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(6)))
                .andExpect(jsonPath("$.id", is(customer.getId().intValue())))
                .andExpect(jsonPath("$.login", is(customer.getLogin())))
                .andExpect(jsonPath("$.role", is(customer.getRole().name())));
    }

    @Test
    public void gettingNotExistingAuthor() throws Exception {
        mockMvc.perform(get("/users/" + 999_999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registeringNewCustomer() throws Exception {
        UserRegistrationRequest request = registrationOf(fixture.customer());
        String payload = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    private UserRegistrationRequest registrationOf(User user) {
        return UserRegistrationRequest.builder()
                .login("new" + user.getLogin())
                .password("sia1aBabaM@k")
                .role(UserRegistrationRequest.Role.valueOf(user.getRole().name()))
                .contactPreference(UserRegistrationRequest.ContactPreference.valueOf(user.getContactPreference().name()))
                .avatarUrl(user.getAvatarUrl())
                .address(UserRegistrationRequest.Address.builder()
                .country(user.getAddress().getCountry())
                        .city(user.getAddress().getCity())
                        .street(user.getAddress().getStreet())
                        .zipCode(user.getAddress().getZipCode())
                        .build())
                .build();
    }
}
