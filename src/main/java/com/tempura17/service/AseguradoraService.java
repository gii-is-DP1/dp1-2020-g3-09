package com.tempura17.service;

import com.tempura17.repository.AseguradoraRepository;
import com.tempura17.model.Aseguradora;
import com.tempura17.model.Paciente;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AseguradoraService {
    
    private AseguradoraRepository aseguradoraRepository;
    private PacienteService pacienteService;

    @Autowired
    public AseguradoraService(AseguradoraRepository aseguradoraRepository, PacienteService pacienteService){
        this.aseguradoraRepository = aseguradoraRepository;
        this.pacienteService = pacienteService;
    }

    public Collection<Aseguradora> findAll(){
        return aseguradoraRepository.findAll();
      }
      
      public Optional<Aseguradora> findById(Integer id){
        return aseguradoraRepository.findById(id);
      }

      public void deletePaciente(Integer idAseguradora, Integer idPaciente){
        Optional<Paciente> paciente= pacienteService.findById(idPaciente);

        if (paciente.get() == null){

        System.out.println("paciente nulo");

        }else{
          Paciente p1 = paciente.get();
          p1.setAseguradora(null);
          this.pacienteService.save(p1);

          Optional<Aseguradora> aseguradora= aseguradoraRepository.findById(idAseguradora);

        if (aseguradora.get() == null){

          System.out.println("aseguradora nula");
        
        }else{
          Aseguradora a1 = aseguradora.get();
          a1.getPacientes().remove(p1);
        }

        

      }
      
  }
}
