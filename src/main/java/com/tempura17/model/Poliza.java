package com.tempura17.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "polizas")
public class Poliza extends NamedEntity{

    @Column(precision = 8, scale =2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private Cobertura cobertura;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate  duracion;

    @ManyToOne
    @JoinColumn(name = "aseguradora_id")
    @JsonIgnore
    private Aseguradora aseguradora;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poliza", fetch = FetchType.EAGER)
    private Set<Paciente> pacientes;

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Cobertura getCobertura() {
        return cobertura;
    }

    public void setCobertura(Cobertura cobertura) {
        this.cobertura = cobertura;
    }

    public LocalDate  getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalDate duracion) {
        this.duracion = duracion;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    
    
}
