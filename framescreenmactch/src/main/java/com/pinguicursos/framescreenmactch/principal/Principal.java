package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.DatosEpisodio;
import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.model.DatosTemporada;
import com.pinguicursos.framescreenmactch.model.Episodio;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
//        List<DatosEpisodio> datosEpisodios = listaTemporadas.stream()
//                .flatMap(t -> t.datosEpisodios().stream())
//                .collect(Collectors.toList());

        //Top 5
//        System.out.println("TOP 5 EPISODIOS");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A): " + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo filtro (Ordenar): "+ e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercer filtro (Mayusculas): "+ e))
//                .limit(5)
//                .forEach(System.out::println);

        //Convertir datos a una lista del tipo Episodio
        List<Episodio> episodios = listaTemporadas.stream()
                .flatMap(t -> t.datosEpisodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        //episodios.forEach(System.out::println);


        //Busqueda de episodios apartir de una fecha especifica

        //System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null  && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: "+ e.getTemporada() +
//                                "Episodio: "+ e.getTitulo() +
//                                "Fecha de lanzamiento: "+ e.getFechaDeLanzamiento().format(dtf)
//
//        ));

        //Busqueda de episodios por nombre o parte del nombre
//        System.out.println("Indica el titulo del episodio que deseas ver");
//        var parteTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(parteTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Los datos son: "+ episodioBuscado.get());
//        } else {
//            System.out.println("Episodio no encontrado");
//        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e-> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("la media de las evaluaciones es: " + est.getAverage());
        System.out.println("La mayor calificacion obtenida en la serie fue de: " + est.getMax());
        System.out.println("La menor calificacion obtenida en la serie fue de: " + est.getMin());
        System.out.println("Total evaluaciones: " + est.getCount());

    }
}
