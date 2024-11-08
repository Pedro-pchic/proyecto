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

import com.pc.microservicio.entity.Producto;
import com.pc.microservicio.implService.ProductoService;
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
	@Autowired
	private ProductoService productoService;
	//lista producto
	@GetMapping("/lista")
	public List<Producto> findAll(){
		return productoService.findAll();
	}
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Producto> findById(@PathVariable Long id){
		Optional<Producto> producto = productoService.findById(id);
		return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/save")
	public ResponseEntity<Producto> save(@RequestBody Producto producto) {
		Producto nuevoproducto = productoService.save(producto);
		return ResponseEntity.ok(nuevoproducto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto detallesProducto){
		Producto productoActualizado = productoService.update(id, detallesProducto);
		if( productoActualizado != null) {
			return ResponseEntity.ok(productoActualizado);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteByI(@PathVariable Long id){
		productoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}