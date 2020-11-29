package com.tempura17.web;

import org.springframework.stereotype.Controller;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Cita;
import com.tempura17.model.Especialista;
import com.tempura17.service.EspecialistaService;
import com.tempura17.service.CitaService;
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
@RequestMapping("/especialistas")
public class EspecialistaController {

    @Autowired
    EspecialistaService especialistaService;

    @GetMapping("/json")
	@ResponseBody
	public Collection<Especialista> jsonEspecialista()
	{
		
		return especialistaService.findAll();
	}
    
}
