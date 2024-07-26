package com.bps.ejercicio.dao;

import java.util.Date;
import java.util.List;

import com.bps.ejercicio.models.Cuenta;
import com.bps.ejercicio.models.Movimiento;

public interface MovimientoDao {

    List<Movimiento> getMovimientos();
    
    Movimiento getMovimientosNumeroCuenta(Integer numero);
    
    List<Movimiento> getMovimientoDebitoPorFecha(Date fechaInicio, Date fechaFin, Integer numeroCuenta);

    List<Cuenta> getMovimientoPorFecha(Date fechaInicio, Date fechaFin, Integer cuentaID);
    
    Movimiento getMovimientoActual(Integer idCuenta);
    
    void editar(Movimiento movimiento) throws Exception;
    
    void editarUltimoMovimiento(Integer numeroCuenta) throws Exception;
    
    void registrar(Movimiento movimiento) throws Exception ;
    

}
