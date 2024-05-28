package com.scm.scm10.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.scm.scm10.services.SecurityCustomUserService;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Autowired
    private SecurityCustomUserService customUserService;

    @Autowired
    private CustomAuthenticationHandler authenticationHandler;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpqdbwtv6",
                "api_key", "386919823479715",
                "api_secret", "guT23gJbcwzwoz-JbMXzvMlcFPY"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserService);
        authenticationProvider.setPasswordEncoder(getBCryptPasswordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) -> auth

                .requestMatchers("/register", "/Uploads/**", "/js/**", "/login", "/css/**", "/v1/**")

                .permitAll()

                .anyRequest().authenticated()

        );
        httpSecurity.formLogin(form -> form.loginPage("/login")
                .defaultSuccessUrl("/user/dashboard")

        );
        // oauth2 configuration
        httpSecurity.oauth2Login(oauth -> oauth.loginPage("/login")
                .successHandler(authenticationHandler)

        );

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
