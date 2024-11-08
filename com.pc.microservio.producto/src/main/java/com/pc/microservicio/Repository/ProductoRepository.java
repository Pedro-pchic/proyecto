package com.pc.microservicio.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.microservicio.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
