package com.project1.property_booking_website.securityConfig;

import com.project1.property_booking_website.exeption.CustomAccessDeniedHandler;
import com.project1.property_booking_website.jwt.AddEmailHeaderFilter;
import com.project1.property_booking_website.jwt.JwtFilter;
import com.project1.property_booking_website.service.AuthUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {


    @Configuration
    public class SecurityConfig {

        private final JwtFilter jwtFilter;
        private final AddEmailHeaderFilter addEmailHeaderFilter;

        private final AuthUserDetailsService userDetailsService;

        public SecurityConfig(JwtFilter jwtFilter, AuthUserDetailsService userDetailsService, AddEmailHeaderFilter addEmailHeaderFilter) {
            this.jwtFilter = jwtFilter;
            this.userDetailsService = userDetailsService;
            this.addEmailHeaderFilter = addEmailHeaderFilter;

        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable);

            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/auth/*").permitAll()
                            .requestMatchers("/swagger-ui/*").permitAll()
                            .requestMatchers("/v3/api-docs").permitAll()
                            .anyRequest().authenticated()
                    )
                    .exceptionHandling(ex ->
                            ex.accessDeniedHandler(new CustomAccessDeniedHandler())
                    )

                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(addEmailHeaderFilter, JwtFilter.class);
            return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

}
