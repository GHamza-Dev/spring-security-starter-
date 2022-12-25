package flat.io.springsecuritystarter.config;

import flat.io.springsecuritystarter.user.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsersRepository usersRepository;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UsersRepository usersRepository, @Lazy UserDetailsService userDetailsService) {
        this.usersRepository = usersRepository;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/hello-user")
                .hasRole("USER")
                .requestMatchers("/hello-admin")
                .hasRole("ADMIN")
                .requestMatchers("/users/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
