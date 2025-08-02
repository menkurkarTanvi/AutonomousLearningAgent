package com.example.project.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.project.entity.User;
import com.example.project.service.UserService;
public class UserController {
    private UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserController(UserService userService){
        this.userService = userService;

    }
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }

}
