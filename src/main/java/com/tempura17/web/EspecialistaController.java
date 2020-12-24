package com.tempura17.web;

import org.springframework.stereotype.Controller;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Especialista;
import com.tempura17.service.EspecialistaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
