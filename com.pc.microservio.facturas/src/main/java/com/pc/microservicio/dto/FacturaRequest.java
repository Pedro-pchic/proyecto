package com.pc.microservicio.dto;

import java.util.List;

public class FacturaRequest {
	private Long clienteId;
	private List<ProductoRequest> productos;
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public List<ProductoRequest> getProductos() {
		return productos;
	}
	public void setProductos(List<ProductoRequest> productos) {
		this.productos = productos;
	}
	public ProductoRequest[] getProductosFactura() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
