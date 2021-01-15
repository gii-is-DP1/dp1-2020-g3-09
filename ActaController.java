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
    

    @GetMapping("/new")
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
    }
    
    @GetMapping("/new/{citaId}/{especialistaId}")
	public String NewActaId(ModelMap model) {
		model.addAttribute("actas", new Acta());
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
			return "actas/listActas";

		}
    }

    
}
