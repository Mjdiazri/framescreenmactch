package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.model.DatosTemporada;
import com.pinguicursos.framescreenmactch.model.Episodio;
import com.pinguicursos.framescreenmactch.model.Serie;
import com.pinguicursos.framescreenmactch.repository.SerieRepository;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PrincipalPersistencia {

        private Scanner teclado = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private final String URL_BASE = "https://www.omdbapi.com/?apikey=64058bad&t=";
        private ConvierteDatos conversor = new ConvierteDatos();
        private List<DatosSerie> datosSerie = new ArrayList<>();
        private SerieRepository repositorio;
        private List<Serie> series;

    public PrincipalPersistencia(SerieRepository repository) {
        this.repositorio = repository;

    }

    public void muestraElMenu() {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    1 - Buscar series
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    
                    0 - Salir
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarSerieWeb();
                        break;
                    case 2:
                        buscarEpisodioPorSerie();
                        break;

                    case 3:
                        mostrarSeriesBuscadas();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }

        }


    private DatosSerie getDatosSerie() {
            System.out.println("Escribe el nombre de la serie que deseas buscar");
            var nombreSerie = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+"));
            System.out.println(json);
            DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
            return datos;
        }
        private void buscarEpisodioPorSerie() {
            // Sin postgres
            // DatosSerie datosSerie = getDatosSerie();

            //Con postgres
            mostrarSeriesBuscadas();
            System.out.println("Escribe el nombre de la serie donde quieres buscar los episodios");
            var nombreSerie = teclado.nextLine();

            Optional<Serie> serie = series.stream()
                    .filter(s -> s.getTitulo().toUpperCase().contains(nombreSerie.toUpperCase()))
                    .findFirst();

            if(serie.isPresent()){
                var serieEncontrada = serie.get();
                List<DatosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                    var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i);
                    DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                    temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);

                List<Episodio> episodios = temporadas.stream()
                        .flatMap(d -> d.datosEpisodios().stream()
                                .map(e -> new Episodio(d.numero(), e)))
                        .collect(Collectors.toList());

                serieEncontrada.setEpisodios(episodios);
                repositorio.save(serieEncontrada);
            }



        }


        private void buscarSerieWeb() {
            DatosSerie dates = getDatosSerie();
            Serie serie = new Serie(dates);
            repositorio.save(serie);
            //datosSerie.add(dates);
            System.out.println(dates);
        }

         private void mostrarSeriesBuscadas() {
        //Con postgres
             series = repositorio.findAll();


        //Sin postgres
//            List<Serie> series = new ArrayList<>();
//            series = datosSerie.stream()
//                    .map(d -> new Serie(d))
//                    .collect(Collectors.toList());

            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }
}
