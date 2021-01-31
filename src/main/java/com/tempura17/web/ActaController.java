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
    

    /*@GetMapping("/new")
	public String NewActa(ModelMap model) {
		model.addAttribute("acta", new Acta());
		return "actas/actasForm";
    }
    
    @PostMapping("/new")
	public String saveNewActa(@Valid Acta acta, BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL ACTA");
			return "actas/actasForm";

		} else {
			actaService.save(acta);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return "actas/listActas";

		}
	}*/

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

		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL ACTA");
			return "actas/actasForm";

		} else {
            Optional<Cita> cita = citaService.findById(citaId);
            Cita citas = cita.get();
            acta.setCita(citas);
            Optional<Especialista> especialista = especialistaService.findById(especialistaId);
            Especialista especialistas = especialista.get();
            acta.setEspecialista(especialistas);
			actaService.save(acta);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return all(model);

		}
	}
	

	@GetMapping("/{actaId}/edit")
	public String editActa(@PathVariable("actaId") int actaId, ModelMap model){
		// Instaurar el uso de GenericTransofrmer en base al id
		Optional<Acta> actas = actaService.findById(actaId);
		if(actas.isPresent()){
			model.addAttribute("acta", actas.get());
			return "actas/actasForm";

		}else{
			model.addAttribute("message", "NO EXISTE NINGUN ACTA CON ESE ID");
			return all(model);
		}
	}

	
	@PostMapping(value = "/{actaId}/edit")
	public String processUpdateActaForm(@Valid Acta acta,BindingResult binding,@PathVariable("actaId") int actaId, ModelMap model) {
		Optional<Acta> actas = actaService.findById(actaId);
		if(binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL MODIFICAR LOS DATOS");
			return "actas/actasForm";
		}
		else {
			BeanUtils.copyProperties(acta, actas.get(), "id","cita","especialista");
			this.actaService.save(actas.get());
			model.addAttribute("message", "ACTA MODIFICADA CORRECTAMENTE");
			return all(model);
		}
	}
}
