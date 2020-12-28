package com.tempura17.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cobertura;
import com.tempura17.model.Poliza;
import com.tempura17.model.Paciente;
import com.tempura17.repository.PolizaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolizaService {

    private PolizaRepository polizaRepository;
    private AseguradoraService aseguradoraService;
    private PacienteService pacienteService;

    @Autowired
    public PolizaService(PolizaRepository polizaRepository, AseguradoraService aseguradoraService, PacienteService pacienteService){
        this.polizaRepository = polizaRepository;
        this.aseguradoraService = aseguradoraService;
        this.pacienteService = pacienteService;
    }

    public Collection<Poliza> findAll(){
        return polizaRepository.findAll();
    }

    public Optional<Poliza> findById(Integer id){
        return polizaRepository.findById(id);
    }

    public void deletePoliza(Integer idPoliza){
        Optional<Poliza> poliza = polizaRepository.findById(idPoliza);
        
        if(poliza.get() == null){
          System.out.println("poliza nula");
        
        }else{
          Poliza p1 = poliza.get();
          Optional<Aseguradora> aseguradora = aseguradoraService.findById(p1.getAseguradora().getId());
        
          if(aseguradora.get() == null){
            System.out.println("Aseguradora nula");

          }else{

            Aseguradora a1 = aseguradora.get();

            p1.getPacientes().stream()
                             .forEach(paciente -> {
                               paciente.setPoliza(null);
                               this.pacienteService.save(paciente);
                             });


            //pacientes.addAll(p1.getPacientes());
            /*for(Paciente p: pacientes){
            Paciente pp = this.pacienteService.findById(p.getId()).get();
            p.setPoliza(null)
            pp.setPoliza(null);
            this.pacienteService.save(p);
            }*/
  
            a1.getPolizas().remove(p1);
            this.aseguradoraService.save(a1);
            this.polizaRepository.delete(p1);
          }
        }
      }

    public void deletePacienteDePoliza(Integer idPaciente){
    Optional<Paciente> paciente = this.pacienteService.findById(idPaciente);
    
    if(paciente.get() == null){
        System.out.println("paciente nulo");
    
    }else{
        Paciente p1 = paciente.get();
        p1.setPoliza(null);
        this.pacienteService.save(p1);
        }
    }


    public void editCobertura(Integer idPoliza, String cobertura){
        Optional<Poliza> poliza = polizaRepository.findById(idPoliza);
  
        if(poliza.get() == null){
          System.out.println("Poliza nula");
        }else{
          Poliza p1 = poliza.get();
          p1.setCobertura(Cobertura.valueOf(cobertura));
          this.polizaRepository.save(p1);
        }
      }


    
}
