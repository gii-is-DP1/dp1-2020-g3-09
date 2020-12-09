package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Alarma;
import com.tempura17.service.AlarmaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/alarmas")
public class AlarmaController {

    @Autowired
    AlarmaService alarmaServ;

    @GetMapping
	public String listAlarmas(ModelMap model){
		model.addAttribute("alarmas", alarmaServ.findAll());
			return "alarmas/misAlarmas";
	}

	@GetMapping("/json")
	@ResponseBody
	public Collection<Alarma> jsonAlarmas(){
		return alarmaServ.findAll();
    }

    @GetMapping("/new")
	public String NewAlarma(ModelMap model){
		model.addAttribute("alarma", new Alarma());
		return "alarmas/crearAlarma";
	}

	@PostMapping("/new")
	public String saveNewAlarma(@Valid Alarma alarma, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL GUARDAR LA ALARMA");
			return "alarmas/crearAlarma";

		}else {
			alarmaServ.save(alarma);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return listAlarmas(model);

		}
	}

	@GetMapping("/{id}/edit")
	public String editAlarma(@PathVariable("id") int id, ModelMap model){
		Optional<Alarma> alarma = alarmaServ.findById((id));

		if(alarma.isPresent()){
			model.addAttribute("alarma", alarma.get());
			return "alarmas/crearAlarma";

		}else{
			model.addAttribute("message", "NO EXISTE ALARMA CON ESE ID");
			return listAlarmas(model);
		}
	}

	@PostMapping("/{id}/edit")
	public String editAlarma(@PathVariable("id") int id, @Valid Alarma alarmaModified, BindingResult binding, ModelMap model){
		Optional<Alarma> alarma = alarmaServ.findById(id);

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA ALARMA");
			return listAlarmas(model);

		}else{
			BeanUtils.copyProperties(alarmaModified, alarma.get(), "id");
			alarmaServ.save(alarma.get());
			model.addAttribute("message", "BIEN MODIFICADA LA ALARMA");
			return listAlarmas(model);
		}
	}

}
