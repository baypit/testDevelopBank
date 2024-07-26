package com.bps.ejercicio;

import com.bps.ejercicio.dao.ClienteDao;
import com.bps.ejercicio.dao.CuentaDao;
import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EjercicioApplicationTests {
	@Autowired
	ClienteDao clienteDao;

	@Autowired
	CuentaDao cuentaDao;

	@Test
	void validarInsercionCliente() throws Exception {

		Cliente cliente = new Cliente();
		cliente.setContraseña("123");
		cliente.setNombre("Prueba");
		cliente.setIdentificacion("12345678");
		cliente.setEstado(true);

		clienteDao.registrar(cliente);
	}

	@Test
	void validarInsercionCuenta() throws Exception {

		Cuenta cuenta = new Cuenta();
		cuenta.setSaldo(123d);
		cuenta.setNumero(123);
		cuenta.setTipo("prueba");
		cuenta.setEstado(true);

		cuentaDao.registrar(cuenta);
	}

}
