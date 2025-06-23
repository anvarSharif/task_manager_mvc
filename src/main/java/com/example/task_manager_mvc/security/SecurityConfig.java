package com.example.task_manager_mvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(manager -> {
            manager
                    .requestMatchers("/login", "/auth/*").permitAll()
                    .anyRequest()
                    .authenticated();
        });

        http.formLogin((manager) -> {
            manager.loginPage("/login").defaultSuccessUrl("/");
        });
        http.logout((manager) -> {
            manager.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}













