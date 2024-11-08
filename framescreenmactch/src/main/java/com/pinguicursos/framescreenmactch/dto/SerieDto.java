package com.pinguicursos.framescreenmactch.dto;

import com.pinguicursos.framescreenmactch.model.Categoria;


public record SerieDto(
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis ) {

}
