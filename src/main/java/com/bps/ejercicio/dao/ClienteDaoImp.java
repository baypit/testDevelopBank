package com.bps.ejercicio.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bps.ejercicio.models.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bps.ejercicio.models.Cliente;

@Repository
@Transactional
public class ClienteDaoImp implements ClienteDao {

    @PersistenceContext
    EntityManager entityManager;

	@Autowired
	PersonaDao personaDao;


    @Override
    @Transactional
    public List<Cliente> getClientes() {
        String query = "FROM Cliente";
        return entityManager.createQuery(query).getResultList();
    }



	@Override
	@Transactional
	public void editar(Cliente cliente) throws Exception {
		try {

			Query query2 = entityManager.createQuery("UPDATE Cliente c SET c.nombre = :nombre,"
					+ " c.direccion =:direccion, c.telefono = :telefono, c.contraseña= :contrasenia,  " +
			       "c.estado= :estado  WHERE id = :id")
			      .setParameter("nombre", cliente.getNombre())
			      .setParameter("direccion", cliente.getDireccion())
			      .setParameter("telefono", cliente.getTelefono())
			      .setParameter("contrasenia", cliente.getContraseña())
			      .setParameter("estado", cliente.isEstado())
			      .setParameter("id", cliente.getId());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
		} catch (Exception e) {
			System.out.println("Error modificando clientes: " + e.getMessage());
		}
		
		
	}



	@Override
	@Transactional
	public void registrar(Cliente cliente) throws Exception {
		try {
			entityManager.merge(cliente);

		} catch (Exception e) {
			System.out.println("Error registrando clientes: " + e.getMessage());
		}
		
	}

	@Override
	@Transactional
	public void eliminar(Integer idPersona) throws Exception {
		try {
			personaDao.eliminar(idPersona);

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			System.out.println("Error eliminar clientes: " + e.getMessage());
		}finally {
			entityManager.close();
		}

	}

}
