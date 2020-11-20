package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Paciente;
import com.tempura17.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class PacienteController {

    @Autowired
    PacienteService pacienteService;
	
	@Autowired
	public PacienteController(PacienteService pacienteService){
		this.pacienteService = pacienteService;
	}

	@GetMapping("/pacientes")
	public Collection<Paciente> all(){
		
		return pacienteService.findAll();
	}
    
}
