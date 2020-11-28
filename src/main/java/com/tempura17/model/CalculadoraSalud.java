package com.tempura17.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinTable;
import lombok.Data;

@Entity
@Data
@Table(name = "calculadora")
public class CalculadoraSalud extends BaseEntity{

    private Double peso;

    private Double altura;

    private Double imc;

    private String resultado;

    @ManyToOne
    @JoinColumn(name ="paciente_id")  
    @JsonIgnore
    private Paciente paciente;


    public CalculadoraSalud(){}


    public CalculadoraSalud(Double peso,Double altura,Double imc,String resultado,Paciente paciente) {
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.resultado = resultado;
        this.paciente = paciente;
    }

    public Double getPeso() {
        return this.peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return this.altura;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getimc() {
        return this.imc;
    }
    public void setimc(Double imc) {
        this.imc = imc;
    }

    public String getResultado() {
        return this.resultado;
    }
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


}