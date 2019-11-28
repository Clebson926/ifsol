package com.example.ifsol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IfsolApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfsolApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("admin"));
	}

}
