package com.pinguicursos.framescreenmactch;


import com.pinguicursos.framescreenmactch.principal.EjemploStreams;
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
		//Ejecutar Clase Principal
		Principal principal = new Principal();
		principal.muestraMenu();

		//Ejecutar Clase EjemploStreams
//		EjemploStreams ejemploStreams = new EjemploStreams();
//		ejemploStreams.muestraEjemplo();





	}
}
