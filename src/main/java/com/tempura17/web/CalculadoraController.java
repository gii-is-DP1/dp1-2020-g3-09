package com.tempura17.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.model.Paciente;
import com.tempura17.service.CalculadoraService;
import com.tempura17.service.PacienteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/calculadoras")
public class CalculadoraController {

	private final CalculadoraService calculadoraService;

	private final PacienteService pacienteService;
	
	@Autowired
	public CalculadoraController(CalculadoraService calculadoraService,PacienteService pacienteService){
		super();
		this.calculadoraService = calculadoraService;
		this.pacienteService = pacienteService;
	}

    @GetMapping
	public String listCalculadoras(ModelMap model)
	{
		model.addAttribute("calculadoras", calculadoraService.findAll());
			return "calculadoras/calculadoraListing";
	}

	@GetMapping("/json")
	@ResponseBody
	public Collection<CalculadoraSalud> jsonCalculadoras()
	{
		return calculadoraService.findAll();
	}

    @GetMapping("/new/{pacienteId}")
	public String NewCalculadora(@PathVariable("pacienteId") Integer pacienteId,ModelMap model){
		model.addAttribute("calculadora", new CalculadoraSalud());
		return "calculadoras/calcularIMC";
	}


	@PostMapping("/new/{pacienteId}")
	public String saveNewCalculadora(@PathVariable("pacienteId") Integer pacienteId,@Valid CalculadoraSalud calculadora, BindingResult binding, ModelMap model){
		Paciente paciente = this.pacienteService.findById(pacienteId).get();
		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL GUARDAR LA CALCULADORA");
			return "calculadoras/calcularIMC";

		}else {
			//Añadir al servicio
			Double peso=calculadora.getPeso();
			Double altura=calculadora.getAltura();
			Double imc=(peso/Math.pow(altura, 2.));
			String resultado="";
			if(imc<18.5){
				resultado="Peso inferior al normal";
			}else if(imc>=18.5 && imc<=24.9){
				resultado="Peso normal";
			}else if(imc>=25 && imc<=29.9){
				resultado="Peso superior al normal";
			}else{
				resultado="Obesidad";
			}
			calculadora.setPaciente(paciente);
			calculadora.setResultado(resultado);
			calculadora.setimc(imc);
			paciente.addCalculadora(calculadora);
			calculadoraService.save(calculadora);
			model.addAttribute("message", "SE HA GUARDADO CORRECTAMENTE");
			return listCalculadoras(model);

		}
	}
}