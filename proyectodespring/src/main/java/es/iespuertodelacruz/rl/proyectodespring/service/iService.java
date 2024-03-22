package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

public interface iService <T,E> {
	
	Iterable<T> findAll();
	Page<T> findAll(Pageable pageable);
	Optional<T> findById(E id);
	T save(T obj);
	boolean deleteById(E id);
	
}
