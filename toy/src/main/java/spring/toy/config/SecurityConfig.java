package spring.toy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import spring.toy.oauth2.CustomClientRegistrationRepo;
import spring.toy.oauth2.CustomOAuth2AuthorizedClientService;
import spring.toy.service.CustomOAuth2UserService;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());

        http.formLogin((login) -> login.disable());

        http.httpBasic((basic) -> basic.disable());

        http.oauth2Login((oauth2) -> oauth2
                .loginPage("/login")
                .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
        );

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/oauth2/**", "login/**", "/error/**").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }
}
