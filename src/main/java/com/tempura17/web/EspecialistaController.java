package com.tempura17.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.ZoneId;

import com.tempura17.model.Especialista;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import com.tempura17.model.Paciente;
import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.PacienteService;



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
@RequestMapping("/especialistas")
public class EspecialistaController {

    
	private final EspecialistaService especialistaService;
	private final PacienteService pacienteService;
	private final CitaService citaService;
	private final AseguradoraService aseguradoraService;
	
	@Autowired
	public EspecialistaController(EspecialistaService especialistaService, CitaService citaService, 
	AseguradoraService aseguradoraService, 
	PacienteService pacienteService){
		super();
		this.especialistaService = especialistaService;
		this.citaService = citaService;
		this.aseguradoraService = aseguradoraService;
		this.pacienteService = pacienteService;
	}

	@GetMapping
	public String all(ModelMap model){
		List<Especialista> especialistas= especialistaService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("especialistas", especialistas);
		
		return "especialistas/Especialistas_list";
	}

    @GetMapping("/json")
	@ResponseBody
	public Collection<Especialista> jsonEspecialista()
	{
		
		return especialistaService.findAll();
	}

	@GetMapping("/new")
	public String newEspecialista(ModelMap model){
		List<Aseguradora> aseguradoras = this.aseguradoraService.findAll().stream().collect(Collectors.toList());
		Especialidad[] especialidad = Especialidad.values();
		Especialista especialista = new Especialista();
		especialista.setAseguradoras(new HashSet<>());
		model.addAttribute("aseguradoras", aseguradoras);
		model.addAttribute("especialidad", especialidad);
		model.addAttribute("especialista", especialista);
		return "especialistas/Especialista_form";
	}

	@PostMapping("/new")
	public String newEspecialista(@Valid Especialista especialista, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return "especialistas/Especialista_form";

		}else {
			String nombre = "Caser";
			Aseguradora aseguradoraPrueba = this.aseguradoraService.findByNombrAseguradora(nombre);
			especialista.addAseguradora(aseguradoraPrueba);
			aseguradoraPrueba.addEspecialista(especialista);
			especialistaService.save(especialista);
			this.aseguradoraService.save(aseguradoraPrueba);
			model.addAttribute("message", "Bien hecho");
			return all(model);

		}
	}

	@GetMapping("{especialistaId}/perfil")
	public String especialistaPerfil(@PathVariable("especialistaId") int especialistaId, ModelMap model){
		Optional<Especialista> especialista = especialistaService.findById((especialistaId));

		if(especialista.isPresent()){
			model.addAttribute("especialista", especialista.get());
			List<Cita> citas = especialista.get().getCitas().stream().collect(Collectors.toList());
			model.addAttribute("citas", citas);
			return "especialistas/Especialistas_perfil";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
	}
	@GetMapping("/{especialistaId}/edit")
	public String editEspecialista(@PathVariable("especialistaId") int especialistaId, ModelMap model){

		Optional<Especialista> especialista = especialistaService.findById((especialistaId));

		if(especialista.isPresent()){
			model.addAttribute("especialista", especialista.get());
			Especialidad[] especialidad = Especialidad.values();
			model.addAttribute("especialidad", especialidad);
			return "especialistas/Especialistas_edit";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
	}

	@PostMapping("/{especialistaId}/edit")
	public String editEspecialista(@PathVariable("especialistaId") int especialistaId, @Valid Especialista especialistaModified, BindingResult binding, ModelMap model){
		Optional<Especialista> especialista = especialistaService.findById(especialistaId);

		if(binding.hasErrors()){
			model.addAttribute("message", binding.getAllErrors().toString());
			return all(model);

		}else{
			BeanUtils.copyProperties(especialistaModified, especialista.get(), "id");
			especialistaService.save(especialista.get());
			Set<Aseguradora> aseguradoras = especialista.get().getAseguradoras();

			for(Aseguradora aseguradora: aseguradoras){
			aseguradora.addEspecialista(especialista.get());
			aseguradoraService.save(aseguradora);
			}

			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return all(model);
		}
	}

	@GetMapping("/cita/{especialistaId}/{pacienteId}")
	public String createCita(@PathVariable("especialistaId") int especialistaId,
							 @PathVariable("pacienteId") int pacienteId, ModelMap model){

		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("especialidad", especialidad);
		model.addAttribute("cita", new Cita());
		return "citas/Citas_form";
	}

	@PostMapping("/cita/{especialistaId}/{pacienteId}")
	public String saveNewCita(@PathVariable("especialistaId") int especialistaId, 
							  @PathVariable("pacienteId") int pacienteId, 
							  @Valid Cita cita, BindingResult binding, ModelMap model){

		Paciente paciente = this.pacienteService.findById(pacienteId).get();
		Especialista especialista = this.especialistaService.findById(especialistaId).get();						

		if(binding.hasErrors() || (cita == null) || (paciente == null) || (especialista == null)){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return all(model);

		}else {
			ZoneId defaultZoneId = ZoneId.systemDefault();
			//creating the instance of LocalDate using the day, month, year info
			LocalDate localDate = LocalDate.now();
			//local date + atStartOfDay() + default time zone + toInstant() = Date
			Date fecha = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
			cita.setFecha(fecha);
			cita.setEspecialista(especialista);
			cita.setPaciente(paciente);
			paciente.addCita(cita);
			especialista.addCita(cita); 
			citaService.save(cita);
			pacienteService.save(paciente);
			especialistaService.save(especialista);  
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			return all(model); 

		}
	}
	@GetMapping("cita/delete/{citaId}/{especialistaId}")
		public String deleteCita(@PathVariable("especialistaId") int especialistaId, @PathVariable("citaId") int citaId, ModelMap model){
			Cita cita = this.citaService.findById(citaId).get();
			// Inspeccionar esta modificación
			//especialistaService.deleteCita(citaId);
			Especialista especialista = cita.getEspecialista();
			Paciente paciente = cita.getPaciente();
			cita.setEspecialista(null);
			cita.setPaciente(null);
			paciente.getCitas().remove(cita);
			especialista.getCitas().remove(cita);
			this.pacienteService.save(paciente);
			this.citaService.save(cita);
			this.especialistaService.save(especialista);
			return "redirect:/especialistas/{especialistaId}/perfil";
		}
}
