package com.tempura17.web;

import java.util.Collection;

import com.tempura17.model.Cita;
import com.tempura17.service.CitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CitaController {
    

    @Autowired
    CitaService citaService;

    @Autowired
	public CitaController(CitaService citaService){
		this.citaService = citaService;
	}

	@GetMapping("/citas")
	@ResponseBody
	public Collection<Cita> all(){
		
		return citaService.findAll();
    }
    
}
