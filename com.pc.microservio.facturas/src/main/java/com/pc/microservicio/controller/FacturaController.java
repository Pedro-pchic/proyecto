package com.pc.microservicio.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.pc.microservicio.implService.IFactura;
import com.gestion.mercado.factura.dto.ClienteDTO;
import com.gestion.mercado.factura.dto.ProductoDTO;
import com.pc.microservicio.dto.FacturaRequest;
import com.pc.microservicio.dto.ProductoRequest;
import com.pc.microservicio.entity.Factura;
@RestController
@RequestMapping("api/cliente/factura")
public class FacturaController {
	
	@Autowired
	private IFactura facturaService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("api/clientes/{id}")
	public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Integer id) {
	    String url = "http://localhost:8081/api/clientes/" + id; 
	    try {
	        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(url, ClienteDTO.class);
	        if (response.getStatusCode() == HttpStatus.OK) {
	            return ResponseEntity.ok(response.getBody());
	        } else {
	            return ResponseEntity.status(response.getStatusCode())
	                                 .body(null);
	        }
	    } catch (HttpClientErrorException e) {
	    	System.out.println("Error al obtener cliente: " + e.getResponseBodyAsString());
	        return ResponseEntity.status(e.getStatusCode())
	                             .body(null);
	    }
	}
	
	@GetMapping("api/productos/{idProducto}")
    public ResponseEntity<ProductoDTO> getProductoById_Producto(@PathVariable Integer idProducto) {
        String url = "http://localhost:8082/api/productos/" + idProducto; 
        try {
            ResponseEntity<ProductoDTO> response = restTemplate.getForEntity(url, ProductoDTO.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
	
	
	
	@PostMapping
	public ResponseEntity<Factura> crearFactura(@RequestBody FacturaRequest facturaRequest) {

	    if (facturaRequest.getClienteId() == null) {
	        return ResponseEntity.badRequest().body(null); 
	    }

		
		String clienteUrl = "http://localhost:8081/api/clientes/" + facturaRequest.getClienteId();
		ResponseEntity<ClienteDTO> response;

	    try {
	        response = restTemplate.getForEntity(clienteUrl, ClienteDTO.class);
	    } catch (HttpClientErrorException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null); 
	    }

	    if (response.getStatusCode() != HttpStatus.OK) {
	        return ResponseEntity.status(response.getStatusCode()).body(null);
	    }

	    ClienteDTO cliente = response.getBody();
	    System.out.println("Datos del cliente obtenidos: " + cliente);

	    

	    double total = 0.0;
	    for (ProductoRequest productoRequest : facturaRequest.getProductosFactura()) {
	        String productoUrl = "http://localhost:8082/api/productos/" + productoRequest.getProductoId();
	        ResponseEntity<ProductoDTO> productoResponse;
	        try {
	            productoResponse = restTemplate.getForEntity(productoUrl, ProductoDTO.class);
	        } catch (HttpClientErrorException e) {
	            return ResponseEntity.status(e.getStatusCode()).body(null); 
	        }

	        if (productoResponse.getStatusCode() == HttpStatus.OK) {
	            ProductoDTO producto = productoResponse.getBody();
	            double subtotal = producto.getPrecio() * productoRequest.getCantidad();
	            total += subtotal;
	        } else {
	            return ResponseEntity.status(productoResponse.getStatusCode()).body(null); 
	        }
	    }

	    
	    Factura nuevaFactura = new Factura();
	    nuevaFactura.setClienteId(facturaRequest.getClienteId());
	    nuevaFactura.setNumeroFactura(generarNumeroFactura());
	    nuevaFactura.setTotal(total);
	    nuevaFactura.setFecha(LocalDate.now());

	    
	    Factura facturaGuardada = facturaService.save(nuevaFactura);

	    return ResponseEntity.status(HttpStatus.CREATED).body(facturaGuardada);
	}

	private String generarNumeroFactura() {
	    return "FAC-" + System.currentTimeMillis(); // Ejemplo simple
	}

}