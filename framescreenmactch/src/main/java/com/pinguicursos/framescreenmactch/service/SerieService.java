package com.pinguicursos.framescreenmactch.service;

import com.pinguicursos.framescreenmactch.dto.EpisodioDto;
import com.pinguicursos.framescreenmactch.dto.SerieDto;
import com.pinguicursos.framescreenmactch.model.Categoria;
import com.pinguicursos.framescreenmactch.model.Serie;
import com.pinguicursos.framescreenmactch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {


    @Autowired
    private SerieRepository repository;

    public List<SerieDto> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }

    public List<SerieDto> obtenerTop5(){
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public  List<SerieDto> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public SerieDto obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDto(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDto> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                        .map(e -> new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDto> obtenerTemporadasPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream()
                .map(e -> new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDto> obtenerSeriesPorCategoria(String nombreGenero){
        Categoria categoria = Categoria.fromEspanol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));

    }


    public List<SerieDto> convierteDatos (List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDto(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }



}
