package com.bps.ejercicio.controllers;


import java.util.Date;
import java.util.List;

import com.bps.ejercicio.dao.PersonaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bps.ejercicio.dao.ClienteDao;
import com.bps.ejercicio.dao.CuentaDao;
import com.bps.ejercicio.dao.MovimientoDao;
import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Cuenta;
import com.bps.ejercicio.models.Movimiento;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/cuenta")
public class CuentaController {

    @Autowired
    private CuentaDao cuentaDao;
    
    @Autowired
    private PersonaDao clienteDao;
    
    @Autowired
    private MovimientoDao movimientoDao;
    
    @GetMapping
    public List<Cuenta> getCuentas() {
    	return cuentaDao.getCuentas();
    }
    
    @PostMapping
    public ResponseEntity<Boolean> modificarCuentas(@RequestBody Cuenta cuenta) {    	
    	 try {
    		 cuentaDao.editar(cuenta);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @PutMapping
    public ResponseEntity<String> registrarCuentas(@Valid @RequestBody Cuenta cuenta, BindingResult bindingResult) {
    	 try {
			 if(bindingResult.hasErrors())
				 return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());

    		 if(validarCliente(cuenta.getCliente().getId()) && validarCuenta(cuenta.getNumero())) {
    			 cuentaDao.registrar(cuenta);

        		 return ResponseEntity.ok("Se ha registrado el movimiento con extiyo");
    		 }else {
    			 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Cliente no registrado").body(null);
    		 }
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    /**
     * Metodo para validar cliente para cuenta
     * @param idCliente
     * @return
     */
    public Boolean validarCliente(Integer idCliente) {
    	Cliente cliente = clienteDao.getPersonaId(idCliente);
    	if(cliente!= null)
    		return true;
    	return false;
    }
    
    /**
     * MEtodo para validar existencia de cuenta
     * @param numeroCuenta
     * @return
     */
    public Boolean validarCuenta(Integer numeroCuenta) {
    	Cuenta cuenta = cuentaDao.getCuentaNumero(numeroCuenta);
    	if(cuenta!= null)
    		return false;
    	return true;
    }
    
    
    @RequestMapping(value = "/listarCuentaNumero", method = RequestMethod.GET)
    public Cuenta getCuentaPorNumero(@RequestParam int numeroCuenta) {
    	return cuentaDao.getCuentaNumero(numeroCuenta);
    }
   
    
}
