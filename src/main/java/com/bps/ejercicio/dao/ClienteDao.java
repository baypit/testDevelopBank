package com.bps.ejercicio.dao;

import java.util.List;

import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Persona;

public interface ClienteDao {

    List<Cliente> getClientes();


    void editar(Cliente cliente) throws Exception;

    void eliminar(Integer idPersona) throws Exception;
    
    void registrar(Cliente cliente) throws Exception ;
    

}
