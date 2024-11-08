package com.pinguicursos.framescreenmactch.service;

import com.pinguicursos.framescreenmactch.dto.SerieDto;
import com.pinguicursos.framescreenmactch.model.Serie;
import com.pinguicursos.framescreenmactch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<SerieDto> convierteDatos (List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDto(s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getPoster(), s.getGenero(), s.getActores(), s.getSinopsis()))
                .collect(Collectors.toList());
    }
}
