package com.tempura17.web.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.tempura17.service.PolizaService;
import com.tempura17.service.AseguradoraService;
import com.tempura17.service.PacienteService;

import com.tempura17.model.Poliza;

import com.tempura17.model.Cobertura;
import com.tempura17.model.Paciente;
import com.tempura17.model.Aseguradora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/polizas")
public class PolizaREST {

    @Autowired
    private final PolizaService polizaService;

    @Autowired
    private final PacienteService pacienteService;

    @Autowired
    private final AseguradoraService aseguradoraService;

    private static final String PATH = "/api/polizas";


    public PolizaREST(PolizaService polizaService, PacienteService pacienteService, AseguradoraService aseguradoraService){
        this.polizaService = polizaService;
        this.pacienteService = pacienteService;
        this.aseguradoraService = aseguradoraService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Poliza>> all() {
        Collection<Poliza> polizas = new ArrayList<Poliza>();
        polizas.addAll(this.polizaService.findAll());
        if (polizas.isEmpty()) {
            return new ResponseEntity<Collection<Poliza>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Poliza>>(polizas, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Poliza> one(@PathVariable("id") Integer id) {
        Poliza poliza = this.polizaService.findById(id).get();
        if (poliza == null) {
            return new ResponseEntity<Poliza>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Poliza>(poliza, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id_aseguradora}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Poliza> create(@PathVariable("id_aseguradora") Integer id_aseguradora
                                                ,@RequestBody @Valid Poliza poliza
                                                , BindingResult bindingResult
                                                , UriComponentsBuilder ucBuilder){
        Aseguradora aseguradora = this.aseguradoraService.findById(id_aseguradora).get();
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (poliza == null) || (aseguradora==null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Poliza>(headers, HttpStatus.BAD_REQUEST);
        }
        poliza.setAseguradora(aseguradora);
        aseguradora.addPoliza(poliza);
        this.polizaService.save(poliza);
        this.aseguradoraService.save(aseguradora);
		headers.setLocation(ucBuilder.path(PATH).buildAndExpand(poliza.getId()).toUri());
		return new ResponseEntity<Poliza>(poliza, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Poliza> update(@PathVariable("id") Integer id, @RequestBody @Valid Poliza newPoliza, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (newPoliza == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Poliza>(headers, HttpStatus.BAD_REQUEST);
		}
		if(this.polizaService.findById(id).get() == null){
			return new ResponseEntity<Poliza>(HttpStatus.NOT_FOUND);
		}
        // polizas(id, aseguradora_id, name, precio, cobertura, duracion)
        Poliza updatedPoliza = this.polizaService.findById(id)
                    .map(poliza -> {
                            /*
                            String nombre = newAseguradora.getNombre() == null ? aseguradora.getNombre() : newAseguradora.getNombre();
                            aseguradora.setNombre(nombre);
                            */
                            String name = newPoliza.getName() == null ? poliza.getName() : newPoliza.getName();
                            poliza.setName(name);
                            BigDecimal precio = newPoliza.getPrecio() == null ? poliza.getPrecio() : newPoliza.getPrecio();
                            poliza.setPrecio(precio);
                            Cobertura cobertura = newPoliza.getCobertura() == null ? poliza.getCobertura() : newPoliza.getCobertura();
                            poliza.setCobertura(cobertura);
                            LocalDate duracion = newPoliza.getDuracion() == null ? poliza.getDuracion() : newPoliza.getDuracion();
                            poliza.setDuracion(duracion);
                            this.polizaService.save(poliza);
                            return poliza;
                    }) 
                    .orElseGet(() -> {
                        newPoliza.setId(id);
                        this.polizaService.save(newPoliza);
                        return newPoliza;
                    });

		return new ResponseEntity<Poliza>(updatedPoliza, HttpStatus.NO_CONTENT);
    }

    // deletePacienteDePoliza
    @RequestMapping(value = "/{id_poliza}/{id_paciente}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> deleteEspecialista(@PathVariable("id_poliza") Integer id_poliza
                                        , @PathVariable("id_paciente") Integer id_paciente){
        Poliza poliza = this.polizaService.findById(id_poliza).get();
        Paciente paciente = this.pacienteService.findById(id_paciente).get();
		if(poliza == null || paciente == null ){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.polizaService.deletePacienteDePoliza(id_paciente);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    // editCobertura -> algo redundate a mi parecer, al menos en este momento


    

}
