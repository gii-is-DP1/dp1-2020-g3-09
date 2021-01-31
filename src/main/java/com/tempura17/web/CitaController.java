package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tempura17.model.Especialista;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/citas")
public class CitaController {
	
	// Dado que Spring gestiona una unica inyección de las dependencias es necesario declarlo como CONSTANTE
	private	final CitaService citaService;

	private final EspecialistaService especialistaService;

	@Autowired
	public CitaController(CitaService citaService, EspecialistaService especialistaService){
		super();
		this.citaService = citaService;
		this.especialistaService = especialistaService;
	}

	@GetMapping
	public String all(ModelMap model){
		List<Cita> citas = citaService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("citas", citas);
		
		return "citas/History";
	}

    @GetMapping("/json")
	@ResponseBody
	public Collection<Cita> jsonCitas(){

		return citaService.findAll();
	}

	@GetMapping("/new")
	public String editNewCita(ModelMap model){
		List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("especialistas", especialistas);
		model.addAttribute("especialidad", especialidad);
		model.addAttribute("cita", new Cita());
		return "citas/Citas_Form";
	}

	@PostMapping("/new")
	public String saveNewCita(@Valid Cita cita, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return all(model);

		}else {
			citaService.save(cita);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			return all(model);

		}
	}


	@GetMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, ModelMap model){
		// Instaurar el uso de GenericTransofrmer en base al id
		Optional<Cita> cita = citaService.findById((citaId));

		if(cita.isPresent()){
			model.addAttribute("cita", cita.get());
			List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
			Especialidad[] especialidad = Especialidad.values();
			model.addAttribute("especialistas", especialistas);
			model.addAttribute("especialidad", especialidad);
			return "citas/Citas_edit";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return all(model);
		}
	}

	@PostMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, @Valid Cita citaModified, BindingResult binding, ModelMap model){
		Optional<Cita> cita = citaService.findById(citaId);

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return all(model);

		}else{
			BeanUtils.copyProperties(citaModified, cita.get(), "id", "paciente");
			citaService.save(cita.get());
			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return all(model);
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

}
