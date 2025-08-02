package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(User user){
        //Store the hashed passwrod
        user.setPassword(encoder.encode(user.getPassword()));
        //Store user in database
        return userRepository.save(user);
    }

    public String verify(User user){
        //Tells authentication amanger that there is a login request using this username
        //and password, please try to auehtnticate teh user. If successful, give 
        //authentication object that contains all their detail
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
                )
        );
        if(authentication.isAuthenticated()){
            return generateToken();
        }
    }
}
