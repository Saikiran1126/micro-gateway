package com.microservice.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
	    http
	        .authorizeExchange()
	            .pathMatchers("/swagger-ui.html", "/v3/api-docs/**").permitAll()
	            .pathMatchers("/admin/**").hasRole("ADMIN")
	            .pathMatchers("/customer/**").hasRole("CUSTOMER")
	            .anyExchange().authenticated()
	        .and()
	        .httpBasic()
	        .and()
	        .csrf().disable();
	    return http.build();
	}

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminpass")) // Encode the password
                .roles("ADMIN")
                .build();

        UserDetails customer = User.withUsername("user")
                .password(passwordEncoder().encode("userpass")) // Encode the password
                .build();

        return new MapReactiveUserDetailsService(admin, customer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}