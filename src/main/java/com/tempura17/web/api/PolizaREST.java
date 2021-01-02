package com.tempura17.web.api;

import java.util.List;
import java.util.stream.Collectors;

import com.tempura17.service.PolizaService;
import com.tempura17.model.Poliza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/polizas")
public class PolizaREST {

    @Autowired
    private final PolizaService polizaService;

    public PolizaREST(PolizaService polizaService){
        this.polizaService = polizaService;
    }

    @GetMapping
    public List<Poliza> getAll(){
        return this.polizaService.findAll()
                                 .stream()
                                 .collect(Collectors.toList());
        
    }
    

}
