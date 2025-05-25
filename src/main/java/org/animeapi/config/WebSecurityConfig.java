package org.animeapi.config;

import lombok.AllArgsConstructor;
import org.animeapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    public AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(daoAuthenticationProvider())
            .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/req/signup", "/req/login", "/css/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/req/login")
                    .successHandler(authenticationSuccessHandler)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/req/logout", "GET"))
                    .logoutSuccessUrl("/req/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )
            .build();
    }
}
