package org.lessons.java_final.final_project.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
                // MVC Admins
                .requestMatchers("/games/create", "/games/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/genres/", "/genres/**").hasAuthority("ADMIN")
                .requestMatchers("/platforms/", "/platforms/**").hasAuthority("ADMIN")
                .requestMatchers("/developers/", "/developers/**").hasAuthority("ADMIN")
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

                // CSRF Cross Site Request Forgery Falsificazione di richieste tra siti
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/games/api/**"))
                // CORS Cross-Origin Resourse Sharing Condivisione delle risorse tra origini
                // diverse
                .cors(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/games/api/**", configuration);
        return source;
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
