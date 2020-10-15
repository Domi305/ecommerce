package pl.github.dominik.ecommerce.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pl.github.dominik.ecommerce.api.UserCredentials;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final String jwtSecret;

    private final long jwtExpirationTime;

    public SecurityConfiguration(ObjectMapper objectMapper,
                                 @Value("alaMaKota") String jwtSecret,
                                 @Value("90000") long jwtExpirationTime) {
        this.objectMapper = objectMapper;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTime = jwtExpirationTime;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserCredentialsAuthenticationFilter userCredentialsAuthenticationFilter() throws Exception {
        val bean = new UserCredentialsAuthenticationFilter(objectMapper);
        bean.setAuthenticationManager(authenticationManager());
        bean.setAuthenticationSuccessHandler(new UserCredentialsAuthenticationSuccessHandler(jwtSecret, jwtExpirationTime));
        //bean.setAuthenticationFailureHandler();
        return bean;

    }
}
