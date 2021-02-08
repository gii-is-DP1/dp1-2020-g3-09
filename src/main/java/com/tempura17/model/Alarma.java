package com.tempura17.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "alarmas")
public class Alarma extends BaseEntity{
	
	private Integer dias;
	
	@OneToOne
	@JoinColumn(name ="cita_id")  
    @JsonIgnore
    private Cita cita;
	
	
	public Integer getDias() {
		return dias;
	}
	
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	public Cita getCita() {
		return this.cita;
	}
	
	public void setCita(Cita cita) {
		this.cita = cita;
	}

}