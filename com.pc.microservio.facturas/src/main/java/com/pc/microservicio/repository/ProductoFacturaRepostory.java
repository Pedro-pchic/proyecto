package com.pc.microservicio.repository;

import org.springframework.stereotype.Repository;
import com.pc.microservicio.entity.ProductoFactura;

@Repository
public interface ProductoFacturaRepostory JpaRepository<ProductoFactura, Integer> {
	
}
