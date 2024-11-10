package com.pinguicursos.framescreenmactch.control;

import com.pinguicursos.framescreenmactch.dto.EpisodioDto;
import com.pinguicursos.framescreenmactch.dto.SerieDto;
import com.pinguicursos.framescreenmactch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")

public class SerieController {

    @Autowired
    private SerieService servicio;


    @GetMapping()
    public List<SerieDto> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDto> obtenerTop5(){
        return servicio.obtenerTop5();
    }

    @GetMapping("/{id}")
    public SerieDto obtenerPorId(@PathVariable long id){
        return servicio.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDto> obtenerTodasLasTemporadas(@PathVariable long id){
        return servicio.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDto> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return servicio.obtenerTemporadasPorNumero(id, numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDto> obtenerSeriesPorCategoria(@PathVariable String nombreGenero){
        return servicio.obtenerSeriesPorCategoria(nombreGenero);
    }

    @GetMapping("/lanzamientos")
    public List<SerieDto> obtenerLanzamientosMasRecientes(){
        return  servicio.obtenerLanzamientosMasRecientes();
    }

}
