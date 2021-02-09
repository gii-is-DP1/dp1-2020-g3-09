package com.tempura17.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tempura17.model.Poliza;
import com.tempura17.model.Aseguradora;
import com.tempura17.model.Cobertura;
import com.tempura17.model.Paciente;
import com.tempura17.model.Tratamiento;
import com.tempura17.service.PacienteService;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.TratamientoService;
import com.tempura17.service.PolizaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/polizas")
public class PolizaController {

    private final AseguradoraService aseguradoraService;
    private final PolizaService polizaService;
    private final PacienteService pacienteService;
    private final TratamientoService tratamientoService;

    

    

	
	@Autowired
    public PolizaController( AseguradoraService aseguradoraService, PolizaService polizaService, PacienteService pacienteService,
                                                                                                TratamientoService tratamientoService){
        super();
        this.aseguradoraService = aseguradoraService;
        this.pacienteService = pacienteService;
        this.polizaService = polizaService;
        this.tratamientoService = tratamientoService;

    }
    
    @GetMapping
	public String all(ModelMap model){
		List<Poliza> polizas= polizaService.findAll().stream()
									  .collect(Collectors.toList());
		model.addAttribute("polizas", polizas);
		
		return "polizas/Polizas_list";
	}

    @GetMapping("new/{aseguradoraId}")
	public String newPoliza(@PathVariable("aseguradoraId") int aseguradoraId, ModelMap model){
		Aseguradora aseguradora = aseguradoraService.findById((aseguradoraId)).get();

           List<Cobertura> coberturas = Arrays.asList(Cobertura.values());
            model.addAttribute("aseguradora", aseguradora);
            model.addAttribute("coberturas", coberturas);
            model.addAttribute("poliza", new Poliza());
			return "polizas/Polizas_form";
    }
    
	@PostMapping("new/{aseguradoraId}")
    public String newPoliza(@PathVariable("aseguradoraId") int aseguradoraId, @Valid Poliza poliza, BindingResult binding, 
                                                                                                                    ModelMap model){

		if(binding.hasErrors()){

			model.addAttribute("message", binding.getAllErrors().toString());
			return "redirect:/aseguradoras/{aseguradoraId}/perfil";

		}else {
            Aseguradora aseguradora = aseguradoraService.findById((aseguradoraId)).get();
            poliza.setAseguradora(aseguradora);
            aseguradora.addPoliza(poliza);

            polizaService.save(poliza);
            aseguradoraService.save(aseguradora);
           
			model.addAttribute("message", "POLIZA AÑADIDA CON EXITO");
			return "redirect:/aseguradoras/{aseguradoraId}/perfil";

		}
    }
    
    @GetMapping("edit/{aseguradoraId}/{polizaId}")
	public String editPoliza(@PathVariable("aseguradoraId") int aseguradoraId, @PathVariable("polizaId") int polizaId, ModelMap model){

        Poliza poliza = polizaService.findById((polizaId)).get();

           List<Cobertura> coberturas = Arrays.asList(Cobertura.values());
           List<Paciente> pacientes = pacienteService.findAll().stream().collect(Collectors.toList());
           List<Tratamiento> tratamientos = tratamientoService.findAll().stream().collect(Collectors.toList());

            model.addAttribute("pacientes", pacientes);
            model.addAttribute("coberturas", coberturas);
            model.addAttribute("tratamientos", tratamientos);
            model.addAttribute("poliza", poliza);
			return "polizas/Polizas_edit";
    }
    
	@PostMapping("edit/{aseguradoraId}/{polizaId}")
    public String editPoliza(@PathVariable("aseguradoraId") int aseguradoraId, @PathVariable("polizaId") int polizaId, 
                                                        @Valid Poliza polizaModified, BindingResult binding,  ModelMap model){

        Poliza poliza = polizaService.findById((polizaId)).get(); 
        Aseguradora aseguradora = aseguradoraService.findById((aseguradoraId)).get();
		if(binding.hasErrors()){

			model.addAttribute("message", binding.getAllErrors().toString());
			return "redirect:/aseguradoras/{aseguradoraId}/perfil";

		}else {
            BeanUtils.copyProperties(polizaModified, poliza, "id");
            poliza.setAseguradora(aseguradora);

            List<Paciente> newPacientes = poliza.getPacientes().stream().collect(Collectors.toList());
            for(Paciente p: newPacientes){
                p.setPoliza(poliza);
                pacienteService.save(p);
            }

            List<Tratamiento> newTratamientos = poliza.getTratamientos().stream().collect(Collectors.toList());
            for(Tratamiento t: newTratamientos){
                t.setPoliza(poliza);
                tratamientoService.save(t);
            }
            polizaService.save(poliza);


			model.addAttribute("message", "POLIZA EDITADA CON EXITO");
			return "redirect:/aseguradoras/{aseguradoraId}/perfil";

        } 
    }
    @GetMapping("delete/{pacienteId}/{aseguradoraId}")
	public String deletePaciente(@PathVariable("aseguradoraId") int aseguradoraId,@PathVariable("pacienteId") int pacienteId, ModelMap model){

        polizaService.deletePacienteDePoliza(pacienteId);
        model.addAttribute("message", "PACIENTE BORRADO CON EXITO");
        return "redirect:/aseguradoras/{aseguradoraId}/perfil";
    }

    
}
