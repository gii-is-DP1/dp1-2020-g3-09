package com.tempura17.web;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.tempura17.model.Cita;
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
@RequestMapping("/citas")
public class CitaController {

    @Autowired
	CitaService citaServ;

	PacienteController pacienteController;
	


	@GetMapping
	public String listCitas(ModelMap model)
	{
		model.addAttribute("citas", citaServ.findAll());
			return "citas/History";
	}

    @GetMapping("/json")
	@ResponseBody
	public Collection<Cita> jsonCitas()
	{
		
		return citaServ.findAll();
	}

	@GetMapping("/new")
	public String editNewCita(ModelMap model){
		model.addAttribute("cita", new Cita());
		return "citas/Citas_Form";
	}

	@PostMapping("/new")
	public String saveNewCita(@Valid Cita cita, BindingResult binding, ModelMap model){

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return listCitas(model);

		}else {
			citaServ.save(cita);
			model.addAttribute("message", "ENHORABUENA BIEN COPIADO");
			return listCitas(model);

		}
	}


	@GetMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, ModelMap model){
		Optional<Cita> cita = citaServ.findById((citaId));

		if(cita.isPresent()){
			model.addAttribute("cita", cita.get());
			return "citas/Citas_Form";

		}else{
			model.addAttribute("message", "NO EXISTE CITA CON ESE ID RETRASADO");
			return listCitas(model);
		}
	}

	@PostMapping("/{citaId}/edit")
	public String editCita(@PathVariable("citaId") int citaId, @Valid Cita citaModified, BindingResult binding, ModelMap model){
		Optional<Cita> cita = citaServ.findById(citaId);

		if(binding.hasErrors()){
			model.addAttribute("message", "ERROR AL PASARLE LA CITA GILIPOLLAS");
			return listCitas(model);

		}else{
			BeanUtils.copyProperties(citaModified, cita.get(), "id", "paciente");
			citaServ.save(cita.get());
			model.addAttribute("message", "BIEN AÑADIDA LA CITA MONGOLO");
			return listCitas(model);
		}
	}

	@GetMapping("/{citaId}/delete")
	public String deleteCita(@PathVariable("citaId") int citaId, ModelMap model){
		Optional<Cita> cita = citaServ.findById(citaId);
		
		if(cita.isPresent()){
			citaServ.delete(cita.get());
			model.addAttribute("message", "ENHORABUENA HAS BORRADO ALGO, QUE PENA QUE NO SE PUEDA HACER CONTIGO LO MISMO");
			return listCitas(model);

		}else{
			model.addAttribute("message","NO EXISTE CITA CON ESE ID RETRASADO");
			return listCitas(model);
		}
	}
    
}
