package com.tempura17.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Audited
@Table(name = "actas")
public class Acta extends AuditableEntity{


    
    @ManyToOne
    @JoinColumn(name = "especialista_id")
    @JsonIgnore
    //@NotNull(message="El especialista no puede ser nulo")
    private Especialista especialista;

    @Size(min = 5, max = 300, message = "La descripción tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres")
    @NotNull
    private String descripcion;

    @Size(min = 5, max = 300, message = "La exploración tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres")
    @NotNull
    private String exploracion;

    @Size(min = 5, max = 300, message = "El diagnóstico tiene que tener un tamaño mínimo de 5 y máximo de 300 carácteres")
    @NotNull
    private String diagnostico;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cita_id", referencedColumnName = "id")
    //@NotNull(message="La cita no puede ser nula")
    private Cita cita;
    

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExploracion() {
        return exploracion;
    }

    public void setExploracion(String exploracion) {
        this.exploracion = exploracion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Especialista getEspecialista() {
        return especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    
    

}
