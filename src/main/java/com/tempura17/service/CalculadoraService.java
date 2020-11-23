package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.CalculadoraSalud;
import com.tempura17.repository.CalculadoraRepository;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

    @Autowired
    CalculadoraRepository repo;
    
    public Collection<CalculadoraSalud> findAll(){
		return repo.findAll();
    }
    
    public CalculadoraSalud findById(long id){
        return repo.findById(id);
    }

    private static Double calcularIMC(Double peso,Double altura){
        Double imc=peso/(altura*altura);
        return imc;
    }
	
    
}
