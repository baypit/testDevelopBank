package com.bps.ejercicio.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bps.ejercicio.models.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bps.ejercicio.models.Cuenta;

@Repository
@Transactional
public class CuentaDaoImp implements CuentaDao {

    @PersistenceContext
    EntityManager entityManager;

	@Autowired
	MovimientoDao movimientoDao;

    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public List<Cuenta> getCuentas() {
        String query = "Select cuentaID, numero, tipo, saldo, estado, cliente  FROM Cuenta ";
        return entityManager.createQuery(query).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public Cuenta getCuentaNumero(Integer numero) {
 
        String query = "FROM Cuenta where numero = :numero";
        List<Cuenta> lista = entityManager.createQuery(query)
                .setParameter("numero", numero)
                .getResultList();
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
        
    }

	@Override
	@Transactional
	public void editar(Cuenta cuenta) throws Exception {
		try {
			Query query2 = entityManager.createQuery("UPDATE Cuenta c SET c.tipo =:tipo,"
					+ " c.saldo = :saldo, c.estado= :estado  WHERE numero = :numero")
			      .setParameter("numero", cuenta.getNumero())
			      .setParameter("tipo", cuenta.getTipo())
			      .setParameter("saldo", cuenta.getSaldo())
			      .setParameter("estado", cuenta.getEstado());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
		} catch (Exception e) {
			throw new Exception("Error modificando cuenta" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public Cuenta registrar(Cuenta cuenta) throws Exception {
		try {
			Cuenta cuentaRegistrada = entityManager.merge(cuenta);

			if(cuentaRegistrada != null) {
				Movimiento movimientoRegistrar = new Movimiento();
				movimientoRegistrar.setCuenta(cuentaRegistrada);
				movimientoRegistrar.setSaldo(cuentaRegistrada.getSaldo());
				movimientoRegistrar.setTipoMovimiento("Deposito");
				movimientoRegistrar.setValor(cuentaRegistrada.getSaldo());
				movimientoRegistrar.setFecha(new Date());
				movimientoRegistrar.setEstado(true);
				movimientoDao.registrar(movimientoRegistrar);
			}

			return cuentaRegistrada;


		} catch (Exception e) {
			System.out.println("Error registrando clientes: " + e.getMessage());
		}
		return null;
	}
    
}
