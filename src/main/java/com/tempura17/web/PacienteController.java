package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.service.PacienteService;
import com.tempura17.service.CitaService;
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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
	PacienteService pacienteService;
	
	@Autowired
	CitaService citaService;
	
	@Autowired
	public PacienteController(PacienteService pacienteService, CitaService citaService){
		this.pacienteService = pacienteService;
		this.citaService = citaService;
	}
	
    /*
    @GetMapping
	public String listPacientes(ModelMap model)
	{
		model.addAttribute("pacientes",pacienteServ.findAll());
		return "pacientes/pacientesListing";
	}
	*/
	@GetMapping
	@ResponseBody
	public List<Paciente> all(){
		
		return pacienteService.findAll().stream()
							  .collect(Collectors.toList());
	}

	@GetMapping(value="/{pacienteId}/citas")
	public String getPacienteCitas(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		model.addAttribute("citas", citaService.findByPacienteId(pacienteId));
		return "citas/History";
	}

	@GetMapping(value="/{pacienteId}/citas/json")
	@ResponseBody
	public Collection<Cita> getPacienteCitasJson(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		return citaService.findByPacienteId(pacienteId);
	}

}
