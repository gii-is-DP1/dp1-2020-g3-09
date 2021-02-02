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
	private final PacienteService pacienteService;
	
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

	@GetMapping
	public String all(ModelMap model){
		
		List<Paciente> pacientes = pacienteService.findAll().stream()
							  .collect(Collectors.toList());
		model.addAttribute("pacientes", pacientes);

		return "pacientes/Pacientes_list";
	}

	@GetMapping("{pacienteId}/perfil")
	public String pacientePerfil(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		Optional<Paciente> paciente = pacienteService.findById((pacienteId));

		if(paciente.isPresent()){
			model.addAttribute("paciente", paciente.get());
			List<Cita> citas = paciente.get().getCitas().stream().collect(Collectors.toList());
			model.addAttribute("citas", citas);
			return "pacientes/Pacientes_perfil";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
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


	@GetMapping("/{pacienteId}/edit")
	public String editPaciente(@PathVariable("pacienteId") int pacienteId, ModelMap model){

		Optional<Paciente> paciente = pacienteService.findById((pacienteId));

		if(paciente.isPresent()){
			model.addAttribute("paciente", paciente.get());
			return "pacientes/Pacientes_edit";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
	}

	@PostMapping("/{pacienteId}/edit")
	public String editPaciente(@PathVariable("pacienteId") int pacienteId, @Valid Paciente pacienteModified, BindingResult binding, ModelMap model){
		Optional<Paciente> paciente = pacienteService.findById(pacienteId);

		if(binding.hasErrors()){
			model.addAttribute("message", binding.getAllErrors().toString());
			return all(model);

		}else{
			BeanUtils.copyProperties(pacienteModified, paciente.get(), "id");
			pacienteService.save(paciente.get());
			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return all(model);
		}
	}


	@GetMapping("/{pacienteId}/delete")
	public String deleteAPaciente(@PathVariable("pacienteId") int id, ModelMap model){
		Optional<Paciente> paciente = pacienteService.findById(id);
		
		if(paciente.isPresent()){
			pacienteService.deleteById(id);
			model.addAttribute("message", "ALARMA BORRADA CORRECTAMENTE");
			return all(model);

		}else{
			model.addAttribute("message","NO EXISTE NINGUNA ALARMA CON ESE ID");
			return all(model);
		}
	}
}
