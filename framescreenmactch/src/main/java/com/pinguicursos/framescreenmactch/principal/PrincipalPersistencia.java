package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.model.DatosTemporada;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalPersistencia {

        private Scanner teclado = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private final String URL_BASE = "https://www.omdbapi.com/?apikey=64058bad&t=";
        private ConvierteDatos conversor = new ConvierteDatos();
        private List<DatosSerie> datosSerie = new ArrayList<>();

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
            DatosSerie datosSerie = getDatosSerie();
            List<DatosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
        }
        private void buscarSerieWeb() {
            DatosSerie dates = getDatosSerie();
            datosSerie.add(dates);
            System.out.println(dates);
        }

         private void mostrarSeriesBuscadas() {
            datosSerie.forEach(System.out::println);
        }
}
