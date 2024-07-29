package com.bps.ejercicio.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bps.ejercicio.models.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bps.ejercicio.models.Movimiento;

@Repository
@Transactional
public class MovimientoDaoImp implements MovimientoDao {

    @PersistenceContext
    EntityManager entityManager;

    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public List<Movimiento> getMovimientos() {
        String query = "FROM Movimiento";
        return entityManager.createQuery(query).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public Movimiento getMovimientosNumeroCuenta(Integer numero) {
 
        String query = "FROM Movimiento where numeroCuenta = :numero";
        List<Movimiento> lista = entityManager.createQuery(query)
                .setParameter("numero", numero)
                .getResultList();
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
        
    }

	@Override
	@Transactional
	public void editar(Movimiento movimiento) throws Exception {
		try {
			Query query2 = entityManager.createQuery("UPDATE Movimiento c SET c.tipoMovimiento =:tipoMovimiento,"
					+ " c.valor = :valor, c.estado= :estado  WHERE numeroCuenta = :numero")
			      //.setParameter("tipoMovimiento", movimiento.getNumeroCuenta())
			      .setParameter("valor", movimiento.getTipoMovimiento())
			      .setParameter("saldo", movimiento.getSaldo());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
		} catch (Exception e) {
			throw new Exception("Error modificando movimiento" + e.getMessage());
		}
		
		
	}

	@Override
	@Transactional
	public void registrar(Movimiento movimiento) throws Exception {
		try {
			Double saldoAnterior = 0D;
			String query = "FROM Movimiento where cuenta.cuentaID = :cuentaID";
	        List<Movimiento> lista = entityManager.createQuery(query)
	                .setParameter("cuentaID", movimiento.getCuenta().getCuentaID())
	                .getResultList();
	        if (!lista.isEmpty()) {
	        	Movimiento movimientoActual=lista.get(0);
	        	saldoAnterior = movimientoActual.getSaldo();
	        	
	        	Query query2 = entityManager.createQuery("UPDATE Movimiento c SET c.estado =0 WHERE  id =:id")
				      .setParameter("id", movimientoActual.getId());

		        int rowsUpdated = query2.executeUpdate();
		        System.out.println("entities Updated: " + rowsUpdated);

	        	
	        }
			movimiento.setFecha(new Date());
			movimiento.setSaldo(validarSaldo(movimiento, saldoAnterior));
			entityManager.merge(movimiento);

		} catch (Exception e) {
			throw new Exception("Error registrando movimiento" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Movimiento> getMovimientoDebitoPorFecha(Date fechaInicio, Date fechaFin, Integer numeroCuenta) {
		String query = "FROM Movimiento where numeroCuenta = :numero and fecha BETWEEN :fechaInicio and :fechaFin and tipoMovimiento = 'Debito'";
        List<Movimiento> lista = entityManager.createQuery(query)
                .setParameter("numero", numeroCuenta)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
       
        return lista;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Cuenta> getMovimientoPorFecha(Date fechaInicio, Date fechaFin, Integer cuentaID) {

		String query = "SELECT c, m FROM Cuenta c JOIN FETCH c.movimientos m WHERE c.cuentaID = :numero AND m.fecha BETWEEN :fechaInicio AND :fechaFin";
        List<Cuenta> lista = entityManager.createQuery(query)
                .setParameter("numero", cuentaID)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
       
        return lista;
	}
	
	@Override
	@Transactional
	public Movimiento getMovimientoActual(Integer numeroCuenta) {
		String query = "FROM Movimiento where cuenta.numero = :numero and estado = 1";
        List<Movimiento> lista = entityManager.createQuery(query)
                .setParameter("numero", numeroCuenta)
                .getResultList();
       
        if (lista.isEmpty()) {
            return null;
        }
        
        return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void editarUltimoMovimiento(Integer numeroCuenta) throws Exception {
		// TODO Auto-generated method stub
		String query = "FROM Movimiento where numeroCuenta = :numero and estado = 1";
        List<Movimiento> lista = entityManager.createQuery(query)
                .setParameter("numero", numeroCuenta)
                .getResultList();
        if (!lista.isEmpty()) {
        	Movimiento movimientoActual=lista.get(0);
        	
        	Query query2 = entityManager.createQuery("UPDATE Movimiento c SET c.estado =0 WHERE numeroCuenta = :numero and id =:id")
			      .setParameter("id", movimientoActual.getId());

	        int rowsUpdated = query2.executeUpdate();
	        System.out.println("entities Updated: " + rowsUpdated);
        	
        }
		
	}
	
	public Double validarSaldo(Movimiento movimiento, Double saldoAnterior) {
		
		if(movimiento.getTipoMovimiento().equals("Debito")) {
			return saldoAnterior - movimiento.getValor();
			
		}else if (movimiento.getTipoMovimiento().equals("Deposito")) {
			return saldoAnterior + movimiento.getValor();
		}
		return null;
		
	}
    
}
