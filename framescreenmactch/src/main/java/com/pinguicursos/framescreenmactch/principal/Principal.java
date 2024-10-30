package com.pinguicursos.framescreenmactch.principal;

import com.pinguicursos.framescreenmactch.model.DatosEpisodio;
import com.pinguicursos.framescreenmactch.model.DatosSerie;
import com.pinguicursos.framescreenmactch.model.DatosTemporada;
import com.pinguicursos.framescreenmactch.service.ConsumoAPI;
import com.pinguicursos.framescreenmactch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        listaTemporadas.forEach(t -> t.datosEpisodios().forEach(e -> System.out.println(e.titulo())));
    }
}
