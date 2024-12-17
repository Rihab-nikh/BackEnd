package org.example.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;




@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(getCorsConfigurationSource()))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/all").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api.products/all").hasAnyAuthority("ADMIN", "CASHIER", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyAuthority("ADMIN", "CASHIER", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/categories/all").hasAnyAuthority("ADMIN", "CASHIER", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyAuthority("ADMIN", "CASHIER", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/products/save").hasAuthority("CREATE")
                                .requestMatchers(HttpMethod.DELETE, "/api/products/delete/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api.products/update").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
@Bean
protected CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.applyPermitDefaultValues();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}