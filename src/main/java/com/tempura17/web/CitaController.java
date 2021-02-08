package com.tempura17.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.tempura17.model.Especialista;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Cita;
import com.tempura17.model.Paciente;
import com.tempura17.model.Especialidad;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PacienteService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/citas")
@Slf4j
public class CitaController {
	
	// Dado que Spring gestiona una unica inyección de las dependencias es necesario declarlo como CONSTANTE
	private	final CitaService citaService;

	private final EspecialistaService especialistaService;

	private final PacienteService pacienteService;

	@Autowired
	public CitaController(CitaService citaService, EspecialistaService especialistaService, PacienteService pacienteService){
		super();
		this.citaService = citaService;
		this.especialistaService = especialistaService;
		this.pacienteService = pacienteService;
	}

	@GetMapping
	public String all(ModelMap model){
		List<Cita> citas = citaService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("citas", citas);
		
		return "citas/Citas_list";
	}

	@GetMapping("/historial/{pacienteId}")
	public String historial(@PathVariable("pacienteId") int pacienteId, ModelMap model){
		List<Cita> citas = pacienteService.findById(pacienteId).get().getCitas().stream()
									  .collect(Collectors.toList());
		model.addAttribute("citas", citas);
		
		return "citas/Citas_list";
	}

    @GetMapping("/json")
	@ResponseBody
	public Collection<Cita> jsonCitas(){

		return citaService.findAll();
	}

	@GetMapping("/new/{pacienteId}")
	public String saveNewCita(ModelMap model){
		List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("especialistas", especialistas);
		model.addAttribute("especialidad", especialidad);
		model.addAttribute("cita", new Cita());
		return "citas/Citas_form";
	}

	@PostMapping("/new/{pacienteId}")
	public String saveNewCita(@PathVariable("pacienteId") int pacienteId, @Valid Cita cita, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
			Especialidad[] especialidad = Especialidad.values();
			model.addAttribute("especialistas", especialistas);
			model.addAttribute("especialidad", especialidad);
			model.addAttribute("cita",cita);
			return "citas/Citas_form";

		}else {
			Paciente paciente = pacienteService.findById(pacienteId).get();
			paciente.addCita(cita);
			cita.setPaciente(paciente);
			citaService.save(cita);
			pacienteService.save(paciente);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			Set<Cita> citas = this.pacienteService.findById(pacienteId).get().getCitas();
			model.addAttribute("citas", citas);
			return "redirect:/pacientes/{pacienteId}/perfil";

		}
	}


	@GetMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, ModelMap model){
		// Instaurar el uso de GenericTransofrmer en base al id
		Optional<Cita> cita = citaService.findById((citaId));

		model.addAttribute("cita", cita.get());
		List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("especialistas", especialistas);
		model.addAttribute("especialidad", especialidad);
		return "citas/Citas_edit";
	}

	@PostMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, @Valid Cita citaModified, BindingResult binding, ModelMap model){
		Cita cita = citaService.findById(citaId).get();

		if(binding.hasErrors()){
			model.addAttribute("message", binding.getAllErrors().toString());
			List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
			Especialidad[] especialidad = Especialidad.values();
			model.addAttribute("especialistas", especialistas);
			model.addAttribute("especialidad", especialidad);
			return "citas/Citas_edit";

		}else{
			BeanUtils.copyProperties(citaModified, cita, "id", "paciente");
			citaService.save(cita);
			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return "redirect:/especialistas/" + cita.getEspecialista().getId() + "/perfil";
		}
	}

	@GetMapping("/{citaId}/delete")
	public String deleteCita(@PathVariable("citaId") int citaId, ModelMap model){
		Optional<Cita> cita = citaService.findById(citaId);
		
		if(cita.isPresent()){
			citaService.delete(cita.get());
			model.addAttribute("message", "ENHORABUENA HAS BORRADO ALGO, QUE PENA QUE NO SE PUEDA HACER CONTIGO LO MISMO");
			return all(model);

		}else{
			model.addAttribute("message","NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
	}

	@GetMapping("/{especialistaId}/{pacienteId}")
	public String filterBy(@PathVariable("especialistaId") int especialistaId,
						   @PathVariable("pacienteId") int pacienteId, ModelMap model){
	
		Set<Cita> citas = this.citaService.findByPacienteId(pacienteId).stream()
											.filter(x -> x.getEspecialista().getId() == especialistaId)
											.collect(Collectors.toSet());												
		model.addAttribute("citas", citas);
		Especialista especialista = especialistaService.findById(especialistaId).get();
		model.addAttribute("especialista",especialista);
		Paciente paciente = pacienteService.findById(pacienteId).get();
		model.addAttribute("paciente",paciente);

		return "citas/Citas_list";
	}

	@GetMapping("/{citaId}")
	public String findById(@PathVariable("citaId") Integer citaId, ModelMap model){
		Cita cita = this.citaService.findById(citaId).get();
		model.addAttribute("cita", cita);
		return "citas/Citas_detalles";
	}


	@GetMapping("/new/{especialistaId}/{pacienteId}")
	public String saveNewCitaForPaciente(@PathVariable("especialistaId") int especialistaId,
	@PathVariable("pacienteId") int pacienteId,ModelMap model){
		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("especialidad", especialidad);
		Cita cita = new Cita();
		model.addAttribute("cita", cita);
		return "citas/Citas_especialista";
	}

	@PostMapping("/new/{especialistaId}/{pacienteId}")
	public String saveNewCitaForPaciente(@PathVariable("especialistaId") int especialistaId,
	@PathVariable("pacienteId") int pacienteId,@Valid Cita cita, BindingResult binding, ModelMap model){
		cita.setEspecialista(this.especialistaService.findById(especialistaId).get());
		cita.setPaciente(this.pacienteService.findById(pacienteId).get());
		model.addAttribute("especialista", cita.getEspecialista());
		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return filterBy(especialistaId, pacienteId, model);

		}else {
			Paciente paciente = cita.getPaciente();
			paciente.addCita(cita);
			cita.setPaciente(paciente);
			Especialista especialista = cita.getEspecialista();
			especialista.addCita(cita);
			cita.setEspecialista(especialista);
			citaService.save(cita);
			pacienteService.save(paciente);
			especialistaService.save(especialista);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			return filterBy(especialistaId, pacienteId, model);

		}
	}




}
