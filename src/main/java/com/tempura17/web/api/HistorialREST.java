package com.tempura17.web.api;

import java.util.List;
import java.util.stream.Collectors;

import com.tempura17.model.Historial;
import com.tempura17.service.HistorialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/historiales")
public class HistorialREST {

    @Autowired
    private final HistorialService historialService;

    public HistorialREST(HistorialService historialService){
        this.historialService = historialService;
    }

    @GetMapping
    public List<Historial> getAll(){
        return this.historialService.findAll()
                                    .stream()
                                    .collect(Collectors.toList());
    }
    
}
