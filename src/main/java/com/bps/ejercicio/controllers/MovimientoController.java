package com.bps.ejercicio.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.bps.ejercicio.models.Cuenta;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
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

import com.bps.ejercicio.dao.MovimientoDao;
import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Movimiento;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/movimiento")
public class MovimientoController {

    
    @Autowired
    private MovimientoDao movimientoDao;
    
    @GetMapping
    public List<Movimiento> getMovimientos() {    	
    	return movimientoDao.getMovimientos();
    }

    @PostMapping
    public ResponseEntity<Boolean> modificarMovimientos(@RequestBody Movimiento movimiento) {    	
    	 try {
    		 movimientoDao.editar(movimiento);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @RequestMapping(value = "/reportes", method = RequestMethod.GET )
    public List<Cuenta> obtenerReporte(@RequestParam String fechaInicio, @RequestParam String fechaFin, @RequestParam Integer cuentaID ) {
    	
    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
    	Date fechaInicioD;
		try {
			fechaInicioD = formato.parse(fechaInicio);
			Date fechaFinD = formato.parse(fechaFin);
			List<Cuenta> cuentas =  movimientoDao.getMovimientoPorFecha(fechaInicioD,fechaFinD,cuentaID);
			List<Cuenta> returnCuenta = new ArrayList<>(0);
			if(CollectionUtils.isNotEmpty(cuentas)){
				for(Cuenta xr: cuentas){
					Cuenta c = new Cuenta();
					c.setTipo(xr.getTipo());
					c.setNumero(xr.getNumero());
					c.setSaldo(xr.getSaldo());
					
					returnCuenta.add(c);
				}

			}

			return returnCuenta;

		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
		return null;
    }
    
    /**
     * REST RegistroMovimiento con validaciones
     * @param movimiento
     * @return
     */
    @PutMapping
    public ResponseEntity<String> registrarMovimiento(@Valid @RequestBody Movimiento movimiento, BindingResult bindingResult) {
    	 try {

			 if(bindingResult.hasErrors())
				 return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());

    		 if(movimiento.getTipoMovimiento().equals("Debito")) {
    			 if(validarMontoDebitoCero(movimiento.getCuenta().getNumero())) {
    				 movimientoDao.registrar(movimiento);
        			 return ResponseEntity.ok("Se ha registrado con exito");
    			 }else {
    				 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Saldo no disponible").body(null);
    			 }
    		 }else {
				 movimientoDao.registrar(movimiento);
    		 }

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    	 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Error").body(null);
    }
    
    /**
     * Metodo que define la hora, minuto y segundo segn parametros
     * @param date
     * @param hours
     * @param min
     * @param sec
     * @return
     */
    public Date adicionarHoraMinutoSegundo(Date date, int hours, int min, int sec) {
    	
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, hours);
    	c.set(Calendar.MINUTE, min);
    	c.set(Calendar.SECOND,sec);
    	
        return c.getTime();
    }



    /**
     * Metodo para validar si debido es cero
     * @param numeroCuenta
     * @return
	 */
    public Boolean validarMontoDebitoCero(Integer numeroCuenta){
    	Movimiento movimientoActual = movimientoDao.getMovimientoActual(numeroCuenta);
    	if(movimientoActual != null && movimientoActual.getSaldo()>0)
    		return true;
    	return false;
    }

    
}
