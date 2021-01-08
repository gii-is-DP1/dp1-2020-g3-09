package com.tempura17.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ManyToOne;



@Entity
@Table(name = "citas")
public class Cita extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El formato no puede ser nulo")
    private Formato formato;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La tipología no puede ser nula")
    private Tipologia tipo;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Enumerated(EnumType.STRING)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name ="paciente_id")
    @NotNull(message = "El paciente no puede ser nulo")
    @JsonIgnore
    private Paciente paciente;

    @ManyToOne 
    @JoinColumn(name = "especialista_id")
    @NotNull(message = "El especialista no puede ser nulo")
    @JsonIgnore
    private Especialista especialista;

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public Tipologia getTipo() {
        return tipo;
    }

    public void setTipo(Tipologia tipo) {
        this.tipo = tipo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /*public Historial getHistorial() {
        return historial;
    }

    public void setHistorial(Historial historial) {
        this.historial = historial;
    }*/

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }


    
    
}
