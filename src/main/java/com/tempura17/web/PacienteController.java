package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.model.CalculadoraSalud;
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
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
	PacienteService pacienteService;
	
	@Autowired
	CitaService citaService;

	@Autowired
	CalculadoraService calculadoraService;
	
	@Autowired
	public PacienteController(PacienteService pacienteService, CitaService citaService, CalculadoraService calculadoraService){
		this.pacienteService = pacienteService;
		this.citaService = citaService;
		this.calculadoraService = calculadoraService;
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



	@GetMapping("/{id}/calculadoras")
	public String getPacienteCalculadora(@PathVariable("id") int id, ModelMap model){
		model.addAttribute("calculadoras", calculadoraService.findByPacienteId(id));
		return "calculadoras/calculadoraListing";
	}

	@GetMapping("/{id}/calculadoras/json")
	@ResponseBody
	public CalculadoraSalud getPacienteCalculadoraJson(@PathVariable("id") int id, ModelMap model){
		return calculadoraService.findByPacienteId(id);
	}
	
	@GetMapping(value="/{pacienteId}/citas")
	public String getPacienteCitas(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		model.addAttribute("citas", pacienteService.findById(pacienteId));
		return "citas/History";
	}

    
	@GetMapping(value="/{pacienteId}/citas/json")
	@ResponseBody
	public Collection<Cita> getPacienteCitasJson(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		return this.pacienteService.findById(pacienteId).get().getCitas();
	}

	@GetMapping("/{pacienteId}")
	public String showPaciente(@PathVariable("pacienteId") int pacienteId, ModelMap model) {
		Optional<Paciente> paciente = pacienteService.findById(pacienteId);
		if(paciente.isPresent()){
			model.addAttribute("pacientes", paciente.get());
			return "pacientes/datosPacientes";
		}else{
			 return "welcome";
		}
	}


	@GetMapping("/{pacienteId}/edit")
	public String editCita(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		// Instaurar el uso de GenericTransofrmer en base al id
		Optional<Paciente> paciente = pacienteService.findById((pacienteId));
		if(paciente.isPresent()){
			model.addAttribute("paciente", paciente.get());
			return "users/usersForm";

		}else{
			model.addAttribute("message", "NO EXISTE EL PACIENTE");
			return "welcome";
		}
	}

	
	@PostMapping(value = "/{pacienteId}/edit")
	public String processUpdatePacienteForm(@Valid Paciente pacienteModificado,BindingResult binding,@PathVariable("pacienteId") int pacienteId,ModelMap model) {
		Optional<Paciente> paciente = pacienteService.findById(pacienteId);
		if(binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL MODIFICAR LOS DATOS");
			return "users/usersForm";
		}
		else {
			BeanUtils.copyProperties(pacienteModificado, paciente.get(), "id", "firstName", "lastName", "dni", "sexo");
			pacienteModificado.setId(pacienteId);
			this.pacienteService.save(paciente.get());
			model.addAttribute("message", "PERFIL MODIFICADO CORRECTAMENTE");
			return "welcome";
		}
	}
}
