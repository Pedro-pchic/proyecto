package com.pc.microservicio.implService;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pc.microservicio.repository.FacturaRepository;
import com.pc.microservicio.dto.ClienteDTO;
import com.pc.microservicio.dto.ProductoFactura;
import com.pc.microservicio.dto.ProductoRequest;
import com.pc.microservicio.dto.ProductoDTO;
import lombok.extern.slf4j.Slf4j;

import com.pc.microservicio.dto.FacturaRequest;
import com.pc.microservicio.entity.Factura;

@RestController
@RequestMapping("/api/factura1")
public class FacturaController {
	
	@Autowired
	private IFactura facturaService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<ClienteDto> getClienteById(@PathVariable Integer id) {
	    String url = "http://localhost:8081/api/clientes/" + id; 
	    try {
	        ResponseEntity<ClienteDto> response = restTemplate.getForEntity(url, ClienteDto.class);
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
	
	@GetMapping("/productos/{idProducto}")
    public ResponseEntity<ProductoDto> getProductoById_Producto(@PathVariable Integer idProducto) {
        String url = "http://localhost:8082/api/productos/" + idProducto; 
        try {
            ResponseEntity<ProductoDto> response = restTemplate.getForEntity(url, ProductoDto.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body(null);
            }
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
	
/*	@PostMapping("/guardar")
	public ResponseEntity<Factura> guardarFactura(@RequestBody FacturaRequest facturaRequest) {
	    Factura factura = facturaService.crearFactura(facturaRequest);
	    return ResponseEntity.status(HttpStatus.CREATED).body(factura);
	}*/
	
	
	@PostMapping
	public ResponseEntity<Factura> crearFactura(@RequestBody FacturaRequest facturaRequest) {
//		ResponseEntity<ClienteDto> clienteResponse = getClienteById(facturaRequest.getClienteId());
	    if (facturaRequest.getClienteId() == null) {
	        return ResponseEntity.badRequest().body(null); // Cliente ID es nulo
	    }

		// Obtener el cliente usando el ID proporcionado
		String clienteUrl = "http://localhost:8081/api/clientes/" + facturaRequest.getClienteId();
		ResponseEntity<ClienteDto> response;
//		ResponseEntity<ClienteDto> clienteResponse = getClienteById(facturaRequest.getClienteId());
	    try {
	        response = restTemplate.getForEntity(clienteUrl, ClienteDto.class);
	    } catch (HttpClientErrorException e) {
	        return ResponseEntity.status(e.getStatusCode()).body(null); // Cliente no encontrado
	    }

	    if (response.getStatusCode() != HttpStatus.OK) {
	        return ResponseEntity.status(response.getStatusCode()).body(null);
	    }

	    ClienteDto cliente = response.getBody();
	    System.out.println("Datos del cliente obtenidos: " + cliente);

	    
	    // Calcular el total de la factura
	    double total = 0.0;
	    for (ProductoRequest productoRequest : facturaRequest.getProductosFactura()) {
	        String productoUrl = "http://localhost:8082/api/productos/" + productoRequest.getProductoId();
	        ResponseEntity<ProductoDto> productoResponse;
	        try {
	            productoResponse = restTemplate.getForEntity(productoUrl, ProductoDto.class);
	        } catch (HttpClientErrorException e) {
	            return ResponseEntity.status(e.getStatusCode()).body(null); // Producto no encontrado
	        }

	        if (productoResponse.getStatusCode() == HttpStatus.OK) {
	            ProductoDto producto = productoResponse.getBody();
	            double subtotal = producto.getPrecio() * productoRequest.getCantidad();
	            total += subtotal;
	        } else {
	            return ResponseEntity.status(productoResponse.getStatusCode()).body(null); // Manejo de error
	        }
	    }

	    // Crear la nueva factura
	    Factura nuevaFactura = new Factura();
	    nuevaFactura.setClienteId(facturaRequest.getClienteId());
	    nuevaFactura.setNumeroFactura(generarNumeroFactura());
	    nuevaFactura.setTotal(total);
	    nuevaFactura.setFecha(LocalDate.now());

	    // Guardar la factura usando el servicio
	    Factura facturaGuardada = facturaService.save(nuevaFactura);

	    return ResponseEntity.status(HttpStatus.CREATED).body(facturaGuardada);
	}

	private String generarNumeroFactura() {
	    return "FAC-" + System.currentTimeMillis(); // Ejemplo simple
	}

}