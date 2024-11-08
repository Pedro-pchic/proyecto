package com.pc.microservicio.IService;

import java.util.List;
import java.util.Optional;
import com.pc.microservicio.entity.Producto;

public interface IService {
	Producto save(Producto producto);
	//obtener todo los prodducto
	List<Producto> findAll();
	//obtener producto por id
	Optional<Producto> findById(Long id);
	//actualizar producto
	Producto update(Long id, Producto producto);
	//eliminar producto por Id
	void deleteById(Long id);
}
