package com.pinguicursos.framescreenmactch;


import com.pinguicursos.framescreenmactch.principal.Principal;
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
		Principal principal = new Principal();
		principal.muestraMenu();




		//FUNCIONALIDADES CLASE PRINCIPAL
		//		var consumoAPI = new ConsumoAPI();
//		var json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones");
//		System.out.println(json);
//
//		//Datos de la serie
//		ConvierteDatos conversor = new ConvierteDatos();
//		var datos = conversor.obtenerDatos(json, DatosSerie.class);
//		System.out.println(datos);
//		//Datos de un capitulo
//
//		json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones&Season=1&Episode=1");
//		var episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
//		System.out.println(episodio);
//
//		//Datos de una temporada
//		json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones&Season=1");
//		var temporada = conversor.obtenerDatos(json, DatosTemporada.class);
//		System.out.println(temporada);
//
//		//Datos de todas las temporadas de la serie
//		List<DatosTemporada> listaTemporadas = new ArrayList<>();
//		for (int i = 1; i < datos.totalDeTemporadas(); i++) {
//			json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?apikey=64058bad&t=game+of+thrones&Season="+i);
//			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
//			listaTemporadas.add(datosTemporadas);
//		}
//		listaTemporadas.forEach(System.out::println);
	}
}
