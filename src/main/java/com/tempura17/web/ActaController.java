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

import com.tempura17.model.Acta;
import com.tempura17.model.Cita;
import com.tempura17.model.Especialidad;
import com.tempura17.service.ActaService;
import com.tempura17.service.CitaService;
import com.tempura17.service.EspecialistaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/actas")
public class ActaController {

    private final ActaService actaService;

    private final CitaService citaService;

    private final EspecialistaService especialistaService;

	@Autowired
	public ActaController(ActaService actaService,CitaService citaService,EspecialistaService especialistaService){
		super();
        this.actaService = actaService;
        this.citaService = citaService;
        this.especialistaService = especialistaService;
    }

	@GetMapping
	public String all(ModelMap model){
		List<Acta> actas = actaService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("actas", actas);
		
		return "actas/listActas";
	}
	
	@GetMapping("/json")
	@ResponseBody
	public Collection<Acta> jsonActas(){

		return actaService.findAll();
	}
    
    @GetMapping("/new/{citaId}/{especialistaId}")
	public String NewActaId(ModelMap model) {
		model.addAttribute("acta", new Acta());
		return "actas/actasForm";
    }

    @PostMapping("/new/{citaId}/{especialistaId}")
	public String saveNewActaId(@PathVariable("citaId") int citaId,@PathVariable("especialistaId") int especialistaId,@Valid Acta acta, BindingResult binding, ModelMap model) {
		Cita cita = citaService.findById(citaId).get();
		Especialista especialista = especialistaService.findById(especialistaId).get();
		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL ACTA");
			return "actas/actasForm";

		} else {
            acta.setCita(cita);
			acta.setEspecialista(especialista);
			especialista.addActa(acta);
			actaService.save(acta);
			especialistaService.save(especialista);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return all(model);

		}
	}
	

	@GetMapping("/{actaId}/edit")
	public String editActa(@PathVariable("actaId") int actaId, ModelMap model){
		Acta acta = actaService.findById(actaId).get();
		model.addAttribute("acta", acta);
		return "actas/actasForm";
	}

	
	@PostMapping(value = "/{actaId}/edit")
	public String processUpdateActaForm(@PathVariable("actaId") int actaId,@Valid Acta actaModified,BindingResult binding,ModelMap model) {
		Acta acta = actaService.findById(actaId).get();
		if(binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL MODIFICAR LOS DATOS");
			return "actas/actasForm";
		}
		else {
			String descripcion = actaModified.getDescripcion()==null ? acta.getDescripcion():actaModified.getDescripcion();
			acta.setDescripcion(descripcion);
			String exploracion = actaModified.getExploracion()==null ? acta.getExploracion():actaModified.getExploracion();
			acta.setExploracion(exploracion);
			String diagnostico = actaModified.getDiagnostico()==null ? acta.getDiagnostico():actaModified.getDiagnostico();
			acta.setDiagnostico(diagnostico);
			this.actaService.save(acta);
			Especialista especialista = acta.getEspecialista();
			this.especialistaService.save(especialista);
			model.addAttribute("message", "ACTA MODIFICADA CORRECTAMENTE");
			return all(model);
		}
	}
}
