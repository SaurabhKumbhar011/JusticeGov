package com.justicegov.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/api/**").permitAll()
                
                // Public endpoints
//                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
//                .requestMatchers("/h2-console/**", "/error").permitAll()
//
//                // Admin 
//                .requestMatchers(HttpMethod.POST, "/auth/approve/**").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/auth/logs").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/auth/suspend/**").hasAuthority("ADMIN")	
//                .requestMatchers(HttpMethod.PUT, "/auth/reactivate/**").hasAuthority("ADMIN")
//                
//                //audit
//                .requestMatchers(HttpMethod.GET, "/api/reports/**", "/api/analytics/**").hasAnyAuthority("ADMIN", "AUDITOR")
//
//                // Profile management
//                .requestMatchers(HttpMethod.GET, "/profiles/citizens/**").hasAnyAuthority("CITIZEN", "ADMIN", "CLERK")
//                .requestMatchers(HttpMethod.GET, "/profiles/lawyers/**").hasAnyAuthority("LAWYER", "ADMIN", "CLERK")
//
//                // Case management
//                .requestMatchers(HttpMethod.GET, "/api/cases/**").hasAnyAuthority("CITIZEN", "LAWYER", "ADMIN", "CLERK", "JUDGE")
//                .requestMatchers(HttpMethod.POST, "/api/cases/**").hasAnyAuthority("LAWYER", "CLERK", "ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/cases/**").hasAnyAuthority("CLERK", "ADMIN")
//
//                // Judgments and Court Research
//                .requestMatchers(HttpMethod.GET, "/api/judgments/**", "/api/court-orders/**").hasAnyAuthority("JUDGE", "CLERK", "ADMIN", "LAWYER")
//                .requestMatchers(HttpMethod.POST, "/api/judgments/**", "/api/court-orders/**").hasAnyAuthority("JUDGE", "CLERK", "ADMIN")
//
//                // Hearings and Research
//                .requestMatchers(HttpMethod.GET, "/api/hearings/**").hasAnyAuthority("JUDGE", "CLERK", "REGISTRAR", "ADMIN", "LAWYER")
//                .requestMatchers(HttpMethod.POST, "/api/hearings/**").hasAnyAuthority("JUDGE", "CLERK", "REGISTRAR", "ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/research/**").hasAnyAuthority("RESEARCHER", "ADMIN")

                // Global protection
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}