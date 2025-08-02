package com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
    @Id
    //Stretgy for generating primary key values foe entities using a database sequence
    //DROP SEQUENCE IF EXISTS my_sequence_name;
    //CREATE SEQUENCE my_sequence_name START 1;
    @SequenceGenerator(
        name = "userGen",
        sequenceName =  "user_sequence",
        allocationSize = 1
    )
    //Specifies that the id should be generated automatically
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long UserId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setUserName(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

}
