package com.beautyProject.beautyProject.config;
//
//import com.beautyProject.beautyProject.filter.JwtAuthFilterConfigurerAdapter;
import com.beautyProject.beautyProject.security.JwtAuthenticationEntryPoint;
import com.beautyProject.beautyProject.security.JwtAuthenticationFilter;
import com.beautyProject.beautyProject.security.JwtTokenProvider;
import com.beautyProject.beautyProject.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private static final String[] PUBLIC_ENDPOINTS = {
//            "/api/auth/**",
//            "/api/auth/login",
//            "/api/auth/register",
//            "/api/products/**",
//            "/api/quiz/**",
//            "/api/skin-types/**",
//            "/swagger-ui/**",
//            "/v3/api-docs/**"
//    };
//
//    private static final String[] ADMIN_ENDPOINTS = {
//            "/api/admin/**",
//            "/api/products/*/admin/**"
//    };
//
//    private final JwtAuthFilterConfigurerAdapter jwtAuthFilterConfigurerAdapter;
//
//    @Autowired
//    @Qualifier("delegatedAuthenticationEntryPoint")
//    private AuthenticationEntryPoint authEntryPoint;
//
//    @Autowired
//    @Qualifier("delegatedAccessDeniedHandler")
//    private AccessDeniedHandler accessDeniedHandler;
//
//    @Bean
//    public static BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.apply(jwtAuthFilterConfigurerAdapter);
//
//        http.csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
//                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ROLE_ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(authEntryPoint)
//                        .accessDeniedHandler(accessDeniedHandler)
//                )
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//}










public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products/*/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/quiz/**").permitAll()
                        .requestMatchers("/api/skin-types/**").permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean                                                                 //
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
//        configuration.setAllowedMethods(Arrays.asList(allowedMethods));
//        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
