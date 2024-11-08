package com.pc.microservicio.Iservice;

import java.util.List;
import java.util.Optional;

import com.pc.microservicio.entity.Cliente;

public interface IService {
	//crear cliente 
		Cliente save(Cliente cliente);
		
		//obtener todo los clientes 
		List<Cliente> findAll();
		
		//obtener clients por id
		Optional<Cliente> findById(Long id);
		
		//actualizar cliente 
		Cliente update(Long id, Cliente cliente);
		
		//eliminar clientes por Id
		void deleteById(Long id);
}