package com.bps.ejercicio.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.bps.ejercicio.dao.ClienteDao;
import com.bps.ejercicio.models.Cliente;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;
    
    @GetMapping
    public List<Cliente> getClientes() {    	
    	return clienteDao.getClientes();
    }
    
    @PostMapping
    public ResponseEntity<Boolean> modificarClientes(@RequestBody Cliente cliente) {    	
    	 try {
			clienteDao.editar(cliente);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @PutMapping
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> registrarClientes(@Valid @RequestBody Cliente cliente, BindingResult bindingResult) {
    	 try {
             if(bindingResult.hasErrors())
                return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());

             clienteDao.registrar(cliente);
    		  return ResponseEntity.ok("Se ha registrado el cliente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminarPersona(@PathVariable Integer id) {
        try {
            clienteDao.eliminar(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
        }
    }
}
