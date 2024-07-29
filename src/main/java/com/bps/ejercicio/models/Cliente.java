package com.bps.ejercicio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name="personaid")
public class Cliente extends Persona{


    @NotNull(message = "No puede estar nulo la contraseña")
	@Column(name="CONTRASENIA")
    private String contraseña;

    @NotNull(message = "No puede estar nulo el estado")
    @Column(name="ESTADO")
    private boolean estado;

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;


    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
