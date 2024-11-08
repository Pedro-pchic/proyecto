package com.pc.microservicio.implService;
import com.pc.microservicio.entity.Factura;

import java.util.List;
import java.util.Optional;

import com.pc.microservicio.dto.FacturaRequest;
public interface IFactura {
	
	List<Factura> findAll();
	Optional<Factura> findByNit(String nit);
	Factura save(Factura factura);
	Factura update(Integer id, Factura factura);
	Factura crearFactura(FacturaRequest facturaRequest);
	Integer deleteById(Integer id);
	
}
