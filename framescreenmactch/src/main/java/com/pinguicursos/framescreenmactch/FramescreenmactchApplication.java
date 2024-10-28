package com.pinguicursos.framescreenmactch;

import com.pinguicursos.framescreenmactch.model.DatosEpisodio;
import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;
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

		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones&Season=1&Episode=1");
		var episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodio);
	}
}
