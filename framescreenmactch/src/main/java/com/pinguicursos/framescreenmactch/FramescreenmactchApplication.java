package com.pinguicursos.framescreenmactch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FramescreenmactchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FramescreenmactchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola Mundo desde Spring");

	}
}
