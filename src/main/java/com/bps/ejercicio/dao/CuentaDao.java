package com.bps.ejercicio.dao;

import java.util.List;

import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Cuenta;

public interface CuentaDao {

    List<Cuenta> getCuentas();
    
    Cuenta getCuentaNumero(Integer numero);
    
    void editar(Cuenta cuenta) throws Exception;
    
    Cuenta registrar(Cuenta cuenta) throws Exception ;
    

}
