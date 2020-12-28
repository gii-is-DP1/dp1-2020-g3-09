package com.tempura17.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tratamientos")
public class Tratamiento extends BaseEntity{

private String descripcion;

private Integer duracion;

@ManyToOne
@JoinColumn( name = "poliza_id")
@JsonIgnore
private Poliza poliza;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "acta_id", referencedColumnName = "id")
private Acta acta;

public String getDescripcion() {
    return descripcion;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public Integer getDuracion() {
    return duracion;
}

public void setDuracion(Integer duracion) {
    this.duracion = duracion;
}

public Acta getActa() {
    return acta;
}

public void setActa(Acta acta) {
    this.acta = acta;
}

public Poliza getPoliza() {
    return poliza;
}

public void setPoliza(Poliza poliza) {
    this.poliza = poliza;
}
    


}
