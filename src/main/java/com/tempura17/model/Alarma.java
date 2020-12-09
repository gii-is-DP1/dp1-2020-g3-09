package com.tempura17.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;



@Entity
@Table(name = "alarmas")
public class Alarma extends BaseEntity{
	
	@PositiveOrZero
	private Integer diasAntelacion;
	
	@OneToOne
	private Cita cita;
	
	
	private String mensaje;
	
	public Integer getDiasAntelacion() {
		return diasAntelacion;
	}
	
	public void setDiasAntelacion(Integer diasAntelacion) {
		this.diasAntelacion = diasAntelacion;
	}
	
	public Cita getCita() {
		return this.cita;
	}
	
	public void setCita(Cita cita) {
		this.cita = cita;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}