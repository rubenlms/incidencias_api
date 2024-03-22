package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.repository.ClienteRepository;


@Service
public class ClienteService implements iService<Cliente, String> {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public Iterable<Cliente> findAll() {
		List<Cliente> findAll = clienteRepository.findAll();
		return findAll;
	}

	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Cliente> findById(String id) {
		Optional<Cliente> findById = clienteRepository.findById(id);
		return findById;
	}
	
	public Cliente findByUsuario(Usuario usuario){
		Cliente cliente = clienteRepository.findClienteByUsuario(usuario);
		return cliente;
	}
	
	@Transactional
	@Override
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public boolean deleteById(String idcliente) {
		boolean okborrado = false;
		
		if(idcliente!=null) {
			try {
				clienteRepository.deleteById(idcliente);;
				okborrado = true;
			} catch(Exception e) {
				okborrado = false;
			}
			
		}
		return okborrado;
	}


}
