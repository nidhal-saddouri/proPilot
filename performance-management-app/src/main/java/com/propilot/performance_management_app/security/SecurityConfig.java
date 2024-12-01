package com.propilot.performance_management_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
          http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r ->{

                    r.requestMatchers("/users/manager").hasRole("MANAGER")
                            .requestMatchers("/users/employee").hasRole("EMPLOYEE")
                            .requestMatchers("/users/admin").hasRole("ADMIN")
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/admin/login").permitAll()
                            .requestMatchers("/users/**").permitAll()
                            .requestMatchers("/users/AddUser").permitAll()
                            .requestMatchers("/auth/register").permitAll()
                            .requestMatchers("/auth/approve/**").permitAll()
                            .requestMatchers("/auth/reset-password/**").permitAll()
                            .requestMatchers("/auth/forgot-password").permitAll()
                            .requestMatchers("/api/users/register").permitAll()
                            .anyRequest().authenticated();
                 })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
          return http.build();
    }
}