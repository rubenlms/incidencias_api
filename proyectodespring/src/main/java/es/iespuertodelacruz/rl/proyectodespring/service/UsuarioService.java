package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.repository.UsuarioRepository;

@Service
public class UsuarioService implements iService<Usuario, Integer>{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public Iterable<Usuario> findAll() {
		List<Usuario> findAll = usuarioRepository.findAll();
		return findAll;
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		Optional<Usuario> findById = usuarioRepository.findById(id);
		return findById;
	}
	
	@Transactional(readOnly=true)
	public Usuario findByNombre(String username) {
		Usuario usuario = usuarioRepository.findByName(username);
		return usuario;
	}

	@Override
	public Usuario save(Usuario usuario) {
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public boolean deleteById(Integer id) {
		boolean ok = false;
		try {
			usuarioRepository.deleteById(id);
			ok = true;
		} catch(Exception e) {
			ok = false;
		}
		
		return ok;
	}
	
	

}
