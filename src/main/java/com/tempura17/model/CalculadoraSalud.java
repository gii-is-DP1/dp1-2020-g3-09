package com.tempura17.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "calculadora")
public class CalculadoraSalud extends BaseEntity{

    private Double peso;

    private Double altura;

    private Double imc;


}