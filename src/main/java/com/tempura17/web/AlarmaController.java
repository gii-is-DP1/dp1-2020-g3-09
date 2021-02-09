package com.tempura17.web;


import java.text.ParseException;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Alarma;
import com.tempura17.model.Cita;
import com.tempura17.service.AlarmaService;
import com.tempura17.service.CitaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/alarmas")
public class AlarmaController {

	private	final AlarmaService alarmaService;

	private final CitaService citaService;

	@Autowired
	public AlarmaController(AlarmaService alarmaService,CitaService citaService){
		super();
		this.alarmaService = alarmaService;
		this.citaService = citaService;
    }

	@GetMapping
	public String listAlarmas(ModelMap model) {
		model.addAttribute("alarmas", alarmaService.findAll());
		return "alarmas/misAlarmas";
	}

	@GetMapping("/new/{citaId}")
	public String newAlarmaId(ModelMap model) {
		model.addAttribute("alarma", new Alarma());
		return "alarmas/crearAlarma";
	}

	@PostMapping("/new/{citaId}")
	public String saveNewAlarmaId(@PathVariable("citaId") int citaId, @Valid Alarma alarma,
			BindingResult binding, ModelMap model) throws ParseException {

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL GUARDAR LA ALARMA");
			return "alarmas/crearAlarma";

		}else {
			Cita cita = citaService.findById(citaId).get();
			int diasint = this.alarmaService.getDiasDiff(cita);
			alarma.setDias(diasint);
			alarma.setCita(cita);
			alarmaService.save(alarma);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return listAlarmas(model);

		}
	}

}
