package com.tempura17.service;

import com.tempura17.repository.AseguradoraRepository;
import com.tempura17.model.Aseguradora;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AseguradoraService {
    
    private AseguradoraRepository aseguradoraRepository;

    @Autowired
    public AseguradoraService(AseguradoraRepository aseguradoraRepository){
        this.aseguradoraRepository = aseguradoraRepository;
    }

    public Collection<Aseguradora> findAll(){
        return aseguradoraRepository.findAll();
      }
      
      public Optional<Aseguradora> findById(Integer id){
        return aseguradoraRepository.findById(id);
      }
}
