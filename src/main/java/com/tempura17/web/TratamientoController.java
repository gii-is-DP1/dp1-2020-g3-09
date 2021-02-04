package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.tempura17.model.Acta;
import com.tempura17.model.Poliza;
import com.tempura17.model.Tratamiento;
import com.tempura17.service.ActaService;
import com.tempura17.service.PolizaService;
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

	private final PolizaService polizaService;


    @Autowired
    public TratamientoController(TratamientoService tratamientoService,ActaService actaService,PolizaService polizaService){
        super();
		this.tratamientoService = tratamientoService;
		this.actaService = actaService;
		this.polizaService = polizaService;
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
	
	
	@GetMapping("/new/{actaId}/{polizaId}")
	public String NewTratamientoId(ModelMap model) {
		model.addAttribute("tratamiento", new Tratamiento());
		return "tratamientos/tratamientosForm";
    }

    @PostMapping("/new/{actaId}/{polizaId}")
	public String saveNewTramientoId(@PathVariable("actaId") int actaId,@PathVariable("polizaId") int polizaId,@Valid Tratamiento tratamiento, 
						BindingResult binding, ModelMap model) {
		Acta acta = actaService.findById(actaId).get();
		Poliza poliza = polizaService.findById(polizaId).get();
		if (binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL GUARDAR EL TRATAMIENTO");
			return "tratamientos/tratamientosForm";

		} else {
			tratamiento.setActa(acta);
            tratamiento.setPoliza(poliza);
			tratamientoService.save(tratamiento);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return all(model);

		}
	}


	@GetMapping("/edit/{tratamientoId}")
	public String editTratamiento(@PathVariable("tratamientoId") int tratamientoId, ModelMap model){
		Tratamiento tratamiento = tratamientoService.findById(tratamientoId).get();
		model.addAttribute("tratamiento", tratamiento);
		return "tratamientos/tratamientosForm";
	}

	
	@PostMapping(value = "/edit/{tratamientoId}")
	public String processUpdateTratamientoForm(@PathVariable("tratamientoId") int tratamientoId,@Valid Tratamiento tratamientoModified,BindingResult binding, ModelMap model) {
		Tratamiento tratamiento = tratamientoService.findById(tratamientoId).get();
		if(binding.hasErrors()) {
			model.addAttribute("message", "ERROR AL MODIFICAR LOS DATOS");
			return "tratamientos/tratamientosForm";
		}
		else {
			BeanUtils.copyProperties(tratamientoModified, tratamiento, "id","acta","poliza");
			this.tratamientoService.save(tratamiento);
			model.addAttribute("message", "TRATAMIENTO MODIFICADO CORRECTAMENTE");
			return all(model);
		}
	}
}
