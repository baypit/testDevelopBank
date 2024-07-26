package com.bps.ejercicio.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movimiento")
public class Movimiento  {

    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @NotNull(message = "No puede estar nulo la fecha de movimiento")
    @Column(name="FECHA")
    private Date fecha;

    @NotNull(message = "No puede estar nulo el tipo de movimiento")
    @Column(name="TIPO")
    private String tipoMovimiento;

    @NotNull(message = "No puede estar nulo el valor")
    @Column(name="VALOR")
    private Double valor;

    @NotNull(message = "No puede estar nulo el saldo")
    @Column(name="SALDO")
    private Double saldo;

    @NotNull(message = "No puede estar nulo el estado")
    @Column(name="ESTADO")
    private Boolean estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cuentaid")
	private Cuenta cuenta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
