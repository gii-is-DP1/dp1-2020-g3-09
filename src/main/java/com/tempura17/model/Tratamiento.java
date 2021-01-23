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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tratamientos")
public class Tratamiento extends BaseEntity{

@Size(min = 10, max = 300, message = "La descripción tiene que tener un tamaño mínimo de 10 y máximo de 300 carácteres")
private String descripcion;

@Min(value = 1, message = "la duración no puede ser menor de 1 día")
private Integer duracion;

@ManyToOne
@JoinColumn( name = "poliza_id")
@JsonIgnore
//@NotNull(message = "La poliza no puede ser nula")
private Poliza poliza;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "acta_id", referencedColumnName = "id")
//@NotNull(message = "El acta no puede ser nulo")
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
