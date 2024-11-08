package com.pc.microservicio.implService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pc.microservicio.IService.IService;
import com.pc.microservicio.Repository.ProductoRepository;
import com.pc.microservicio.entity.Producto;
@Service
public class ProductoService  implements IService {
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return productoRepository.findAll();
	}

	@Override
	public Optional<Producto> findById(Long id) {
		return productoRepository.findById(id);
	}

	@Override
	public Producto update(Long id, Producto detallesproducto) {
		Optional<Producto> productoOptional = productoRepository.findById(id);
		if (productoOptional.isPresent()) {
			Producto productoExistente = productoOptional.get();
			productoExistente.setNombre(detallesproducto.getNombre());
			productoExistente.setPrecio(detallesproducto.getPrecio());
			productoExistente.setStock(detallesproducto.getStock());
			return productoRepository.save(productoExistente);
		}else {
			return null;
		}
	}
		@Override
		public void deleteById(Long id) {
			productoRepository.deleteById(id);
			
		}

}

	

	