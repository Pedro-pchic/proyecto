package com.pc.microservicio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.microservicio.entity.Cliente;
import com.pc.microservicio.implService.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/lista")
	public List<Cliente> findAll(){
		return clienteService.findAll();
	}
	@GetMapping("buscar/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable Long id){
		Optional<Cliente> cliente = clienteService.findById(id);
		return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
		Cliente nuevocliente = clienteService.save(cliente);
		return ResponseEntity.ok(nuevocliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente detallesCliente){
		Cliente clienteActualizado = clienteService.update(id, detallesCliente);
		if( clienteActualizado != null) {
			return ResponseEntity.ok(clienteActualizado);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteByI(@PathVariable Long id){
		clienteService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
