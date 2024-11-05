package com.pinguicursos.framescreenmactch;


import com.pinguicursos.framescreenmactch.principal.EjemploStreams;
import com.pinguicursos.framescreenmactch.principal.Principal;
import com.pinguicursos.framescreenmactch.principal.PrincipalPersistencia;
import com.pinguicursos.framescreenmactch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FramescreenmactchApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(FramescreenmactchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Ejecutar Clase PrincipalPerisistencia
		PrincipalPersistencia persistencia = new PrincipalPersistencia(repository);
		persistencia.muestraElMenu();

		//Ejecutar Clase Principal
//		Principal principal = new Principal();
//		principal.muestraMenu();

		//Ejecutar Clase EjemploStreams
//		EjemploStreams ejemploStreams = new EjemploStreams();
//		ejemploStreams.muestraEjemplo();


	}
}
