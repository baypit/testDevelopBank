package com.bps.ejercicio.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cuentas")
public class Cuenta {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CUENTAID")
	private Integer cuentaID;

    @NotNull(message = "No puede estar nulo el numero de cuenta")
    @Column(name="NUMERO")
    private Integer numero;

    @NotNull(message = "No puede estar nulo el tipo de cuenta")
    @Column(name="TIPO")
    private String tipo;

    @NotNull(message = "No puede estar nulo el saldo")
    @Column(name="SALDO")
    private Double saldo;

    @NotNull(message = "No puede estar nulo el estado")
    @Column(name="ESTADO")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clienteid")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta")
    private List<Movimiento> movimientos;

    public boolean isEstado() {
        return estado;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Integer getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(Integer cuentaID) {
        this.cuentaID = cuentaID;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
