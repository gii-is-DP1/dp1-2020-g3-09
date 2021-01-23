package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.tempura17.model.Acta;
import com.tempura17.model.Tratamiento;
import com.tempura17.service.ActaService;
import com.tempura17.service.TratamientoService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tratamientos")
public class TratamientoController {

	private final TratamientoService tratamientoService;
	
	private final ActaService actaService;


    @Autowired
    public TratamientoController(TratamientoService tratamientoService,ActaService actaService){
        super();
		this.tratamientoService = tratamientoService;
		this.actaService = actaService;
    }

    @GetMapping
	public String all(ModelMap model){
		List<Tratamiento> tratamiento = tratamientoService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("tratamientos", tratamiento);
		
		return "tratamientos/listTratamientos";
	}

    @GetMapping("/json")
	@ResponseBody
	public Collection<Tratamiento> jsonTratamientos(){

		return tratamientoService.findAll();
    }
    

    @GetMapping("/new")
	public String NewTratamiento(ModelMap model) {
		model.addAttribute("tratamiento", new Tratamiento());
		return "tratamientos/tratamientosForm";
    }
    
    @PostMapping("/new")
	public String saveNewTratamiento(@Valid Tratamiento tratamiento, BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL TRATAMIENTO");
			return "tratamientos/tratamientosForm";

		} else {
			tratamientoService.save(tratamiento);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return all(model);

		}
    }
	
	
	@GetMapping("/new/{actaId}")
	public String NewTratamientoId(ModelMap model) {
		model.addAttribute("tratamiento", new Tratamiento());
		return "tratamientos/tratamientosForm";
    }

    @PostMapping("/new/{actaId}")
	public String saveNewTramientoId(@PathVariable("actaId") int actaId,@Valid Tratamiento tratamiento, BindingResult binding, ModelMap model) {

		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL TRATAMIENTO");
			return "tratamientos/tratamientosForm";

		} else {
            Optional<Acta> acta = actaService.findById(actaId);
            Acta actas = acta.get();
            tratamiento.setActa(actas);
			tratamientoService.save(tratamiento);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return all(model);

		}
	}


	@GetMapping("/edit/{tratamientoId}")
	public String editTratamiento(@PathVariable("tratamientoId") int tratamientoId, ModelMap model){
		// Instaurar el uso de GenericTransofrmer en base al id
		Optional<Tratamiento> tratamientos = tratamientoService.findById(tratamientoId);
		if(tratamientos.isPresent()){
			model.addAttribute("tratamiento", tratamientos.get());
			return "tratamientos/tratamientosForm";

		}else{
			model.addAttribute("message", "NO EXISTE NINGUN TRATAMIENTO CON ESE ID");
			return all(model);
		}
	}

	
	@PostMapping(value = "/edit/{tratamientoId}")
	public String processUpdateTratamientoForm(@Valid Tratamiento tratamiento,BindingResult binding,@PathVariable("tratamientoId") int tratamientoId, ModelMap model) {
		Optional<Tratamiento> tratamientos = tratamientoService.findById(tratamientoId);
		if(binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL MODIFICAR LOS DATOS");
			return "tratamientos/tratamientosForm";
		}
		else {
			BeanUtils.copyProperties(tratamiento, tratamientos.get(), "id","acta","poliza");
			this.tratamientoService.save(tratamientos.get());
			model.addAttribute("message", "TRATAMIENTO MODIFICADO CORRECTAMENTE");
			return all(model);
		}
	}
}
