package com.pinguicursos.framescreenmactch.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class <T> clase);
}
