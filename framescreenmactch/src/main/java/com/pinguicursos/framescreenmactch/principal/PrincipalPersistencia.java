package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.*;
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
                    4 - Buscar series por titulo
                    5 - Top 5 Mejores series
                    6 - Buscar series por categoria
                    7 - Filtrar series
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

                    case 4:
                        buscarSeriePorTitulo();
                        break;

                    case 5:
                        buscarTop5Series();
                        break;

                    case 6:
                        buscarSeriePorCategoria();
                        break;

                    case 7:
                        filtrarSeriesPorTemporadaYEvaluacion();
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

        private void buscarSeriePorTitulo() {
            System.out.println("Escriba el nombre de la serie que quiere buscar");
            var nombreSerie = teclado.nextLine();
            Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
            if(serieBuscada.isPresent()){
                System.out.println("La serie buscada es: "+ serieBuscada.get());
            } else {
                System.out.println("Serie no encontrada");
            }

        }


        private void buscarTop5Series() {
            List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
            topSeries.forEach(s -> System.out.println("Serie: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
        }

        private void buscarSeriePorCategoria() {
            System.out.println("Escriba la categoria que quiere buscar");
            var categoriaBuscada = teclado.nextLine();
            var categoria = Categoria.fromEspanol(categoriaBuscada);
            List<Serie> seriePorCategoria = repositorio.findByGenero(categoria);
            System.out.println("Series de la categoria " + categoriaBuscada);
            seriePorCategoria.forEach(System.out::println);
        }


        private void filtrarSeriesPorTemporadaYEvaluacion() {
            System.out.println("Escribe el numero de temporadas por el que quieres filtrar las series");
            var temporadasParaFiltrar = teclado.nextInt();
            teclado.nextLine();
            System.out.println("Escribe el valor minimo de evaluacion que quieres revisar");
            var evaluacionParaFiltrar = teclado.nextDouble();
            teclado.nextLine();
            List<Serie> filtroSeries = repositorio.findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(temporadasParaFiltrar, evaluacionParaFiltrar);
            System.out.println( "Series filtradas: ");
            filtroSeries.forEach(s -> System.out.println(s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
         }
}
