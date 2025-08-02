package com.example.project.config;

import java.io.IOException;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.service.JWTService;
import com.example.project.service.MyUserServiceDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //We only want the authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
        //Client will return following: Bearer egnisnksgpishirngdkjh384u so we need to extract only token
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            //Make username is not null and not already authenticated
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(jwtService.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorites());
                }
            }
        }
    }

}
