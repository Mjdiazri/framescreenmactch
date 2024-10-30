package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.DatosEpisodio;
import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.model.DatosTemporada;
import com.pinguicursos.framescreenmactch.model.Episodio;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?apikey=64058bad&t=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu() {
        //Buscar datos generales de una serie
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var serieUsuario = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+serieUsuario.replace(" ","+"));
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Buscar datos de las temporadas existentes en la serie
        List<DatosTemporada> listaTemporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + serieUsuario.replace(" ", "+") + "&Season=" + i);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            listaTemporadas.add(datosTemporadas);
        }
        //listaTemporadas.forEach(System.out::println);

        //Mostrar el titulo de cada episodio por temporada
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodioTemporada = listaTemporadas.get(i).datosEpisodios();
//            for (int j = 0; j < episodioTemporada.size(); j++) {
//                System.out.println(episodioTemporada.get(j).titulo());
//
//            }
////        }

        //Mostrar el titulo de cada episodio por temporada simplificado con lambda
        //listaTemporadas.forEach(t -> t.datosEpisodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones a una lista del tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = listaTemporadas.stream()
                .flatMap(t -> t.datosEpisodios().stream())
                .collect(Collectors.toList());

        //Top 5
        System.out.println("TOP 5 EPISODIOS");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);

        //Convertir datos a una lista del tipo Episodio
        List<Episodio> episodios = listaTemporadas.stream()
                .flatMap(t -> t.datosEpisodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);


        //Busqueda de episodios apartir de una fecha especifica

        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null  && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada: "+ e.getTemporada() +
                                "Episodio: "+ e.getTitulo() +
                                "Fecha de lanzamiento: "+ e.getFechaDeLanzamiento().format(dtf)

        ));
    }
}
