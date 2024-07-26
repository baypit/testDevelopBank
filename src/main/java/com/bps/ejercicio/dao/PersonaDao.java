package com.bps.ejercicio.dao;

import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Persona;

import java.util.List;

public interface PersonaDao {

    void eliminar(Integer idPersona) throws Exception;
    void registrar(Persona persona) throws Exception ;
    Cliente getPersonaId(Integer id);
    

}
