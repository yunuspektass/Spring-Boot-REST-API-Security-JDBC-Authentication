package com.yunuspektas.springboot.cruddemo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {
// add support for JDBC ... no more hardcoded users

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE ,"/api/employees/**").hasRole("ADMIN")
        );
//    use HTTP Basic authentication
    http.httpBasic(Customizer.withDefaults());

//    disable Cross Site Request Forgery (CSRF)
//    in general , not required for stateless REST APIs that use POST, PUT , DELETE and/or PATCH
    http.csrf(csrf -> csrf.disable());

    return http.build();
    }

        /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails yunus = User.builder()
                .username("yunus")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails sude = User.builder()
                .username("sude")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();

        UserDetails alp = User.builder()
                .username("alp")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(yunus,sude,alp);
    }
     */
}








