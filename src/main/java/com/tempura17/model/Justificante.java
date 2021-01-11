package com.tempura17.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.OneToOne;



@Entity
@Table(name = "justificantes")
public class Justificante extends BaseEntity{
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="cita_id")  
    @JsonIgnore
    private Cita cita;

    @NotNull
    private String motivo;


    
    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getMotivo(){
        return motivo;
    }

    public void setMotivo(String motivo){
        this.motivo = motivo;
    }
}
