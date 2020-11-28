package com.tempura17.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.service.CalculadoraService;
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
@RequestMapping("/calculadoras")
public class CalculadoraController {

    @Autowired
    CalculadoraService calculadoraServ;

    @GetMapping
	public String listCalculadoras(ModelMap model)
	{
		model.addAttribute("calculadoras", calculadoraServ.findAll());
			return "calculadoras/calculadoraListing";
	}

	@GetMapping("/json")
	@ResponseBody
	public Collection<CalculadoraSalud> jsonCalculadoras()
	{
		return calculadoraServ.findAll();
	}

    @GetMapping("/new")
	public String NewCalculadora(ModelMap model){
		model.addAttribute("calculadora", new CalculadoraSalud());
		return "calculadoras/calcularIMC";
	}


	@PostMapping("/new")
	public String saveNewCalculadora(@Valid CalculadoraSalud calculadora, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL GUARDAR LA CALCULADORA");
			return "calculadoras/calcularIMC";

		}else {
			calculadoraServ.save(calculadora);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return listCalculadoras(model);

		}
	}
}