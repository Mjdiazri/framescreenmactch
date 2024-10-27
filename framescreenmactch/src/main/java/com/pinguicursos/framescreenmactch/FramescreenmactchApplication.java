package com.pinguicursos.framescreenmactch;

import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
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
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones");
		System.out.println(json);
	}
}
