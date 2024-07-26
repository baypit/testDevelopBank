package com.bps.ejercicio.dao;

import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Persona;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class PersonaDaoImp implements PersonaDao {

    @PersistenceContext
    EntityManager entityManager;


	@Override
	@Transactional
	public void registrar(Persona persona) throws Exception {
		try {
			entityManager.merge(persona);
		} catch (Exception e) {
			System.out.println("Error registrando clientes: " + e.getMessage());
		}
		
	}

	@Override
	@Transactional
	public Cliente getPersonaId(Integer id) {

		String query = "FROM Persona where id = :id ";
		List<Cliente> lista = entityManager.createQuery(query)
				.setParameter("id", id)
				.getResultList();
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);

	}

	@Override
	@Transactional
	public void eliminar(Integer idPersona) throws Exception {
		try {

			Persona personaEliminated = getPersonaId(idPersona);
			entityManager.remove(personaEliminated);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Error eliminando persona: " + e.getMessage());
		}finally {
			entityManager.close();
		}
	}

}
