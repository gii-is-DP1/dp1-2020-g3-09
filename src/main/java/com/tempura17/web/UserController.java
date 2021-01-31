package com.tempura17.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Paciente;
import com.tempura17.model.User;
import com.tempura17.service.PacienteService;
import com.tempura17.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {



	private final UserService userService;
    
    private final PacienteService pacienteService;

	@Autowired
	public UserController(UserService userService, PacienteService pacienteService) {
        this.userService = userService;
        this.pacienteService = pacienteService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(ModelMap model) {
		model.addAttribute("paciente", new Paciente());
		return "users/usersForm";
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Paciente paciente, BindingResult binding, ModelMap model) {
		
		if (binding.hasErrors()) {
            model.addAttribute("message", "ERROR AL CREAR EL USUARIO");
			return "users/usersForm";
		}
		else {
            pacienteService.savePaciente(paciente);
            model.addAttribute("message", "USUARIO CREADO CORRECTAMENTE");
			return "welcome";
		}
	}
}