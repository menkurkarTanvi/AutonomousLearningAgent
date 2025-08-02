package com.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		//Creates the container (stored in JVM remeber video)
		//Dependency injection: if you want an object of certain class,
		//spring will inject that object for you
		SpringApplication.run(ProjectApplication.class, args);
	}

}
