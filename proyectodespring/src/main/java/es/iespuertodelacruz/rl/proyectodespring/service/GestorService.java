package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.repository.GestorRepository;
import es.iespuertodelacruz.rl.proyectodespring.repository.UsuarioRepository;

@Service
public class GestorService implements iService<Gestore, String> {
	
	@Autowired
	GestorRepository gestorRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public Iterable<Gestore> findAll() {
		List<Gestore> gestores = gestorRepository.findAll();
		return gestores;
	}

	@Override
	public Page<Gestore> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Gestore> findById(String id) {
		Optional<Gestore> gestor = gestorRepository.findById(id);
		return gestor;
	}

	@Override
	@Transactional
	public Gestore save(Gestore gestor) {
		return gestorRepository.save(gestor);
	}

	@Override
	public boolean deleteById(String idgestor) {
		
		boolean okBorrado = false;
		
		if(idgestor!=null) {
			try {
				gestorRepository.deleteById(idgestor);;
				okBorrado = true;
			}catch(Exception e) {
				okBorrado = false;
			}
			
		}
		
		return okBorrado;
	}



}
