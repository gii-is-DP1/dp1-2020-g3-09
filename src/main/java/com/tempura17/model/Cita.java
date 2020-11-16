package com.tempura17.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;



@Entity
@Data
@Table(name = "citas")
public class Cita extends BaseEntity{

    private Tipologia tipo;
    private Formato formato;
    private String especialidad;
    private String especialista;
    private LocalDateTime fecha;
    
}
