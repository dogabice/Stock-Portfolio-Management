package com.spmapi.spmapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //---------------------------------------------------------------- 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF koruması ihtiyaca göre etkinleştirilmelidir
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/public/**").permitAll() // Public URL'ler
                    .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin endpoint'leri
                    .requestMatchers("/api/user/**").hasRole("USER") // User endpoint'leri
                    .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulamalıdır
            )
            .httpBasic(Customizer.withDefaults()) // Temel kimlik doğrulama
            .logout(logout -> 
                logout.permitAll()); // Çıkış işlemi herkes için açık
    
        return http.build();
    }
    //----------------------------------------------------------------     
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("adminpassword"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("userpassword"))
                .roles("USER")
                .build());
        return manager;
    }
    //---------------------------------------------------------------- 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
