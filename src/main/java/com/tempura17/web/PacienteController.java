package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Paciente;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Cita;
import com.tempura17.service.PacienteService;
import com.tempura17.service.CalculadoraService;
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
	PacienteService pacienteServ;
	
	@Autowired
	CalculadoraService calculadoraServ;

	@Autowired
	CitaService citaServ;
    
    @GetMapping
	public String listPacientes(ModelMap model)
	{
		model.addAttribute("pacientes",pacienteServ.findAll());
		return "pacientes/pacientesListing";
	}

	@GetMapping("/json")
	@ResponseBody
	public Collection<Paciente> jsonPacientes()
	{
		
		return pacienteServ.findAll();
	}

	@GetMapping(value="/{pacienteId}/calculadoras")
	public String getPacienteCalculadora(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		model.addAttribute("calculadoras", calculadoraServ.findByPacienteId(pacienteId));
		return "calculadoras/calculadoraListing";
	}

	@GetMapping(value="/{pacienteId}/calculadoras/json")
	@ResponseBody
	public Collection<CalculadoraSalud> getPacienteCalculadoraJson(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		return calculadoraServ.findByPacienteId(pacienteId);
	}
	
	/*@GetMapping(value="/{pacienteId}/citas")
	public String getPacienteCitas(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		model.addAttribute("citas", citaServ.findByPacienteId(pacienteId));
		return "citas/History";
	}

	@GetMapping(value="/{pacienteId}/citas/json")
	@ResponseBody
	public Collection<Cita> getPacienteCitasJson(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		return citaServ.findByPacienteId(pacienteId);
	}*/
    
}
