package com.pc.microservicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.microservicio.entity.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
	List<Factura> findByClienteId(Long ClienteId);
}

