package com.tempura17.web.api;

import com.tempura17.service.AseguradoraService;
import com.tempura17.model.Aseguradora;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aseguradoras")
public class AseguradoraREST {
    
    @Autowired
    private final AseguradoraService aseguradoraService;

    public AseguradoraREST(AseguradoraService aseguradoraService){
        this.aseguradoraService = aseguradoraService;
    }

    @GetMapping
	public List<Aseguradora> all() {
		return this.aseguradoraService
						.findAll().stream()
						.collect(Collectors.toList());
			
		
	}
}
