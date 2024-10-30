package com.pinguicursos.framescreenmactch.principal;


import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Angely", "Caballo", "Juan Camilo", "Pinguina", "Monica");

        nombres.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("C"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);

    }
}
