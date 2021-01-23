package com.tempura17.web;

import java.util.Collection;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


import com.tempura17.model.Justificante;
import com.tempura17.service.JustificanteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/justificantes")
public class JustificanteController {
    
    private	final JustificanteService justificanteService;

	@Autowired
	public JustificanteController(JustificanteService justificanteService){
		super();
		this.justificanteService = justificanteService;
    }
	
	@GetMapping
	public String listJustificantes(ModelMap model)
	{
		model.addAttribute("justificantes", justificanteService.findAll());
		return "justificantes/mostrarJustificantes";
	}
		
	

    @GetMapping("/json")
	@ResponseBody
	public Collection<Justificante> jsonJustificantes(){

		return justificanteService.findAll();
    }
    
    @GetMapping("/new")
	public String newJustificante(ModelMap model){
		model.addAttribute("justificante", new Justificante());
		return "justificantes/crearJustificante";
	}
	
	@PostMapping("/new")
	public String saveNewJustificante(@Valid Justificante justificante, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA");
			return "justificantes/crearJustificante";

		}else {
			justificanteService.save(justificante);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			return listJustificantes(model);

		}
	} 
    

    @GetMapping("/new/{id}")
	public String newJustificante(@PathVariable("id") int id, ModelMap model){
		model.addAttribute("justificante", new Justificante());
		return "justificantes/crearJustificante";
    }
    
    @PostMapping("/new/{id}")
	public String saveNewJustificante(@PathVariable("id") int id, @Valid Justificante justificante, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL GUARDAR EL JUSTIFICANTE");
			return "justificantes/crearJustificante";

		}else {
			this.justificanteService.createJustificanteForCitaId(justificante, id);
			model.addAttribute("message", "JUSTIFICANTE GUARDADO CORRECTAMENTE");
			return listJustificantes(model);

		}
	}
}
