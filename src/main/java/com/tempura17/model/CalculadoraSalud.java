package com.tempura17.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.ManyToOne;


@Entity
@Table(name = "calculadora")
public class CalculadoraSalud extends BaseEntity{

    @NotNull
    private Double peso;

    @NotNull
    private Double altura;

    private Double imc;

    private String resultado;

    @ManyToOne
    @JoinColumn(name ="paciente_id")  
    @JsonIgnore
    private Paciente paciente;


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