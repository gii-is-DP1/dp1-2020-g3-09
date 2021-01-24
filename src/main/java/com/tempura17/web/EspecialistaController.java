package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.tempura17.model.Especialista;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.AseguradoraService;

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
	private final CitaService citaService;
	private final AseguradoraService aseguradoraService;
	
	@Autowired
	public EspecialistaController(EspecialistaService especialistaService, CitaService citaService, AseguradoraService aseguradoraService){
		super();
		this.especialistaService = especialistaService;
		this.citaService = citaService;
		this.aseguradoraService = aseguradoraService;
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
	public String newEspecialistas(ModelMap model){
		List<Aseguradora> aseguradoras = this.aseguradoraService.findAll().stream().collect(Collectors.toList());
		Especialidad[] especialidad = Especialidad.values();
		model.addAttribute("aseguradoras", aseguradoras);
		model.addAttribute("especialidad", especialidad);
		model.addAttribute("especialista", new Especialista());
		return "especialistas/Especialista_form";
	}

	@PostMapping("/new")
	public String saveNewCita(@Valid Especialista especialista, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return all(model);

		}else {
			especialistaService.save(especialista);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
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
			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return all(model);
		}
	}
	/*public ModelAndView showEspecialista(@PathVariable("especialistaId") int especialistaId) {
		ModelAndView mav = new ModelAndView("especialistas/Especialistas_perfil");
		mav.addObject(this.especialistaService.findById(especialistaId).get());
		return mav;
	}*/
    
}
