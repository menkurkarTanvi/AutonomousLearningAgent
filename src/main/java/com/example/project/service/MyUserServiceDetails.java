package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;

@Service
public class MyUserServiceDetails implements UserDetailsService{
    private final UserRepository userRepository;
    //Injectes UserRepoisotry object which is resposnsible for handling the database
    
    //Connects to database
    @Autowired
    public MyUserServiceDetails(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
        
    }
}
