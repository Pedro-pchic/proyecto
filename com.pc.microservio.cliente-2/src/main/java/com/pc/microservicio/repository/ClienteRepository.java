package com.pc.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.microservicio.entity.Cliente;
@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

}
