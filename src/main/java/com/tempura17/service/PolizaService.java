package com.tempura17.service;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cobertura;
import com.tempura17.model.Poliza;
import com.tempura17.model.Paciente;
import com.tempura17.repository.PolizaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public Collection<Poliza> findAll(){
        return polizaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Poliza> findById(Integer id){
        return polizaRepository.findById(id);
    }

    @Transactional
    public void save(@Valid Poliza poliza){
      polizaRepository.save(poliza);
    }

    @Transactional
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

    // no es simetrico con el conjunto de pacientes que reside en poliza
    @Transactional
    public void deletePacienteDePoliza(Integer idPaciente){
    Paciente paciente = this.pacienteService.findById(idPaciente).get();

    if(paciente == null){
        System.out.println("paciente nulo");
    
    }else{
        Poliza poliza = paciente.getPoliza();
        poliza.getPacientes().remove(paciente);
        paciente.setPoliza(null);

        this.pacienteService.save(paciente);
        polizaRepository.save(poliza);
        }
    }
}
