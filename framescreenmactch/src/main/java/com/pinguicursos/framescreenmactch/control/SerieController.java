package com.pinguicursos.framescreenmactch.control;

import com.pinguicursos.framescreenmactch.dto.SerieDto;
import com.pinguicursos.framescreenmactch.repository.SerieRepository;
import com.pinguicursos.framescreenmactch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieService servicio;


    @GetMapping("/series")
    public List<SerieDto> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/series/top5")
    public List<SerieDto> obtenerTop5(){
        return servicio.obtenerTop5();
    }


}
