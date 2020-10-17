package pl.github.dominik.ecommerce.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    private final String jwtSecret;

    private final long jwtExpirationTime;

    private final UserDetailsManager userDetailsManager;

    public SecurityConfiguration(ObjectMapper objectMapper,
                                 @Value("alaMaKota") String jwtSecret,
                                 @Value("900000") long jwtExpirationTime,
                                 UserDetailsManager userDetailsManager) {
        this.objectMapper = objectMapper;
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTime = jwtExpirationTime;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    protected void configure (AuthenticationManagerBuilder authenticationManager) throws Exception {
        authenticationManager.authenticationProvider(userCredentialsAuthenticationProvider())
                .userDetailsService(userDetailsManager);
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
                .addFilter(userCredentialsAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN));
    }

    @Bean
    public UserCredentialsAuthenticationFilter userCredentialsAuthenticationFilter() throws Exception {
        val bean = new UserCredentialsAuthenticationFilter(objectMapper);
        bean.setAuthenticationManager(authenticationManager());
        bean.setAuthenticationSuccessHandler(new UserCredentialsAuthenticationSuccessHandler(jwtSecret, jwtExpirationTime));
        //bean.setAuthenticationFailureHandler();
        return bean;

    }

    private JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), userDetailsService(), jwtSecret);
    }

    private UserCredentialsAuthenticationProvider userCredentialsAuthenticationProvider() {
        return new UserCredentialsAuthenticationProvider(userDetailsService());
    }
}
