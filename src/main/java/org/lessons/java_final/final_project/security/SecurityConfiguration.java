package org.lessons.java_final.final_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(requests -> requests

                // API
                .requestMatchers(HttpMethod.GET, "/games/api/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/games/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/games/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/games/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/genres/api/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/genres/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/genres/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/genres/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/platforms/api/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/platforms/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/platforms/api/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/platforms/api/**").hasAuthority("ADMIN")
                // MVC Admins
                .requestMatchers("/games/create", "/games/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/genres/", "/genres/**").hasAuthority("ADMIN")
                .requestMatchers("/platforms/", "/platforms/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/games/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/genres/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/platforms/**").hasAuthority("ADMIN")

                // MVC Users
                .requestMatchers("/games", "/games/**").hasAnyAuthority("USER", "ADMIN")

                // Tutte le altre richieste
                .anyRequest().authenticated())

                // Form del login
                .formLogin(Customizer.withDefaults())

                // Authenticazione tramite POSTMAN Basic
                .httpBasic(Customizer.withDefaults())

                // Form del logout
                .logout(Customizer.withDefaults())

                // CSRF Cross Site Request Forgery                       Falsificazione di richieste tra siti
                .csrf(csrf -> csrf

                        .ignoringRequestMatchers("/games/api/**")
                        .ignoringRequestMatchers("/genres/api/**")
                        .ignoringRequestMatchers("/platforms/api/**"))

                // CORS  Cross-Origin Resourse Sharing                   Condivisione delle risorse tra origini diverse
                .cors(cors -> cors.disable());
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DatabaseUserDetailService userDetailService() {
        return new DatabaseUserDetailService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
