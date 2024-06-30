package com.study.hotelland.configuration;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager(HttpSecurity security,
                                                       UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {

        var authManager = security.getSharedObject(AuthenticationManagerBuilder.class);
        authManager.userDetailsService(userDetailsService);

        var authProvider = new DaoAuthenticationProvider(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);

        authManager.authenticationProvider(authProvider);

        return authManager.build();
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(AuthenticationManager authenticationManager,
                                                   HttpSecurity security) {

        security.authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/hotelland/visitor").permitAll()
                            .requestMatchers(HttpMethod.POST, "/hotelland/hotel").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/hotelland/hotel/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/hotelland/hotel/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/hotelland/room").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/hotelland/room/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/hotelland/room/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/hotelland/reservation").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/hotelland/statistic").hasAuthority("ADMIN")
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return security.build();
    }

}
