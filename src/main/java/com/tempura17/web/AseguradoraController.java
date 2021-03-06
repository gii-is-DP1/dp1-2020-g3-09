package com.tempura17.web;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tempura17.model.Especialista;
import com.tempura17.model.Poliza;

import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Aseguradora;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.PolizaService;
import com.tempura17.service.AseguradoraService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/aseguradoras")
public class AseguradoraController {

    private final AseguradoraService aseguradoraService;
	private final EspecialistaService especialistaService;
	private final PolizaService polizaService;
	
	@Autowired
	public AseguradoraController( AseguradoraService aseguradoraService, EspecialistaService especialistaService, PolizaService polizaService){
        super();
        this.aseguradoraService = aseguradoraService;
		this.especialistaService = especialistaService;
		this.polizaService = polizaService;

	}

	@GetMapping
	public String all(ModelMap model){
		List<Aseguradora> aseguradoras= aseguradoraService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("aseguradoras", aseguradoras);
		
		return "aseguradoras/Aseguradoras_list";
	}

	@GetMapping("/new")
	public String newAseguradora(ModelMap model){
		List<Especialista> especialistas = this.especialistaService.findAll().stream().collect(Collectors.toList());
		model.addAttribute("especialistas", especialistas);
		model.addAttribute("aseguradora", new Aseguradora());
		return "aseguradoras/Aseguradoras_form";
	}

	@PostMapping("/new")
	public String saveNewAseguradora(@Valid Aseguradora aseguradora, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL CREAR LA ASEGURADORA");
			return "aseguradoras/Aseguradoras_form";

		}else {
			aseguradoraService.save(aseguradora);
			model.addAttribute("message", "ASEGURADORA CREADA CON EXITO");
			return all(model);

		}
	}

	@GetMapping("{aseguradoraId}/perfil")
	public String aseguradoraPerfil(@PathVariable("aseguradoraId") int aseguradoraId, ModelMap model){
		Optional<Aseguradora> aseguradora = aseguradoraService.findById((aseguradoraId));

		if(aseguradora.isPresent()){
			model.addAttribute("aseguradora", aseguradora.get());
			List<Especialista> especialistas = aseguradora.get().getEspecialistas().stream().collect(Collectors.toList());
			model.addAttribute("especialistas", especialistas);
			return "aseguradoras/Aseguradoras_perfil";

		}else{
			model.addAttribute("message", "NO EXISTE ASEGURADORA CON ESE ID");
			return all(model);
		}
	}
	@GetMapping("/{aseguradoraId}/edit")
	public String editAseguradora(@PathVariable("aseguradoraId") int aseguradoraId, ModelMap model){

		Optional<Aseguradora> aseguradora = aseguradoraService.findById((aseguradoraId));

		if(aseguradora.isPresent()){
			model.addAttribute("aseguradora", aseguradora.get());
            List<Especialista> especialistas = aseguradora.get().getEspecialistas().stream().collect(Collectors.toList());
			model.addAttribute("especialistas", especialistas);
			return "aseguradoras/Aseguradoras_edit";

		}else{
			model.addAttribute("message", "NO EXISTE ASEGURADORA CON ESE ID");
			return all(model);
		}
	}

	@PostMapping("/{aseguradoraId}/edit")
	public String editAseguradora(@PathVariable("aseguradoraId") int aseguradoraId, @Valid Aseguradora aseguradoraModified, BindingResult binding, ModelMap model){
		Optional<Aseguradora> aseguradora = aseguradoraService.findById(aseguradoraId);

		if(binding.hasErrors()){
			model.addAttribute("message", binding.getAllErrors().toString());
			return "aseguradoras/Aseguradoras_edit";

		}else{
			BeanUtils.copyProperties(aseguradoraModified, aseguradora.get(), "id");
			aseguradoraService.save(aseguradora.get());
			model.addAttribute("message", "ASEGURADORA EDITADA CON EXITO");
			return all(model);
		}
	}

	@GetMapping("delete/{especialistaId}/{aseguradoraId}")
	public String deleteEspecialista(@PathVariable("aseguradoraId") int aseguradoraId,@PathVariable("especialistaId") int especialistaId, ModelMap model){

        aseguradoraService.deleteEspecialista(aseguradoraId, especialistaId);
        return "redirect:/aseguradoras/{aseguradoraId}/perfil";
	}
	
	@GetMapping("/deletePoliza/{aseguradoraId}/{polizaId}")
	public String deletePoliza(@PathVariable("aseguradoraId") int aseguradoraId, @PathVariable("polizaId") int polizaId, ModelMap model){
		Aseguradora aseguradora = this.aseguradoraService.findById(aseguradoraId).get();
		Poliza poliza = this.polizaService.findById(polizaId).get();
		aseguradora.removePoliza(poliza);
		poliza.setAseguradora(null);
		this.aseguradoraService.save(aseguradora);
		this.polizaService.save(poliza);
        return "redirect:/aseguradoras/{aseguradoraId}/perfil";
    }
    
}

