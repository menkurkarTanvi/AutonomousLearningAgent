package com.example.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//Define beasn so spring can inject objects
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        //Returns object of security filter chain
        return http 
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(request ->request
            //These endpoints do not need authentication
            .requestMatchers("register", "login")
            .permitAll()
            .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //Add a filter in the security filter chain that validates the token from the user
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    

    @Bean
    //Since this is a bean, spring automatically injects it into security framework at startup
    public AuthenticationProvider authenticationProvider(){
        //Specific implementation of Authentication provider
        //Uses UserDetailsService to load user from a database!
        //Then compares the provided password from login form with stored password
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //Tells provider when comparing the entered password, use this password encoder
        provider.setPasswordEncoder(passwordEncoder());
        //Telling the provider user this service to fetch users from database when someone logs in
        provider.setUserDetailsService(userDetailsService);
        //Spring security will use the following
        //1. UserDetailsService to load the user
        //2. PassWord ecnoder to validate teh password
        //3. If everything is successful, authentication is successful
        return provider;
    }

    @Bean
    //This object is provded by Spring Security. It gives access to the fully initialized
    //security configuration including the AuthenticationManager
    //"Give me the default Authentication maanger that already know how to use Authentication Provider"
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        //Retrieve Authentication Manager from Spring Security Config 
        //Which is prewired to your custom AuthenticationProvider
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
