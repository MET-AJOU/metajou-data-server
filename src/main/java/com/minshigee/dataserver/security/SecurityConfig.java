package com.minshigee.dataserver.security;

import com.minshigee.dataserver.security.jwt.JwtAuthenticationFilter;
import com.minshigee.dataserver.security.jwt.JwtUtils;
import com.minshigee.dataserver.security.property.CorsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//TODO: https://stackoverflow.com/questions/37770967/jwt-tokens-in-sessionstorage-vs-cookies

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = {
})
@EnableConfigurationProperties(value = {
        CorsProperties.class
})
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final CorsProperties corsProperties;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        //TODO Make Stateless
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        //TODO ADD CUSTOM Filter
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtils), SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE);

        //TODO ETC
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();

        return http.authorizeExchange()
                .pathMatchers("/api", "/login/**", "/authorize/**", "/webjars/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsProperties.getUrl()));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
