package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.repository.RolRepository;

@Service
public class RolService implements iService<Role, Integer>{
	
	@Autowired
	RolRepository rolRepository;
	
	@Override
	public Iterable<Role> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Role> findById(Integer id) {
		return rolRepository.findById(id);
	}

	@Override
	public Role save(Role obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
