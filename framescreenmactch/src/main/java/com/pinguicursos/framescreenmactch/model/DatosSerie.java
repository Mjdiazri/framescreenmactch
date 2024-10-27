package com.pinguicursos.framescreenmactch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion) {
}
