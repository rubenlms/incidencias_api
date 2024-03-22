package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.Ticket;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.dto.*;
import es.iespuertodelacruz.rl.proyectodespring.service.ClienteService;
import es.iespuertodelacruz.rl.proyectodespring.service.RolService;
import es.iespuertodelacruz.rl.proyectodespring.service.TicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/clientes")
@Api(tags="Cliente REST (v3)", description="Controlador para v3 de Cliente")
public class ClienteREST {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolservice;
	
	@GetMapping
	@ApiOperation(
			value= "Obtener todos los clientes",
			notes= "Mostrará todos los clientes pero solo su información sin entrar en sus tickets"
			)
	public ResponseEntity<?> getAll(){
		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
		clienteService.findAll()
			.forEach(c -> {
				Cliente cliente = c;
				ClienteDTO cDTO = new ClienteDTO(cliente);
				clientes.add(cDTO); 
			});
		
		return ResponseEntity.ok().body(clientes);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtiene un cliente por su ID"
			)
	public ResponseEntity<?> findById(@PathVariable String id){
		Optional<Cliente> cliente = clienteService.findById(id);

		if(cliente.isPresent()) {
			ClienteDTO cDTO = new ClienteDTO(cliente.get());
			return ResponseEntity.ok(cDTO);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
		
	}
	
	/**
	 * Devuelve todos los ticket de un cliente
	 * @param id del cliente
	 * @return
	 */
	@GetMapping("/{id}/tickets")
	@ApiOperation(
			value= "Obtener todos los tickets de un cliente",
			notes= "Muestra todos los tickets que estén asociados a un cliente buscando por su ID"
			)
	public ResponseEntity<?> findAllTickets(@PathVariable String id){
		ArrayList<TicketDTO>tickets = new ArrayList<TicketDTO>();
		
		ticketService.findbyCliente(id)
			.forEach(t -> {
					Ticket ticket = t;
					TicketDTO tDTO = new TicketDTO(ticket);
					tickets.add(tDTO);
				});
		
		return ResponseEntity.ok().body(tickets);
		
	}
	
	@GetMapping("/{id}/tickets/{id2}")
	@ApiOperation(
			value= "Obtener el ticket de un cliente concreto",
			notes= "Muestra el ticket buscando por su ID que está asociado al cliente al que se le pasa su ID"
			)
	public ResponseEntity<?> findTicketFromCliente(@PathVariable String id, @PathVariable Integer id2){
		Ticket ticket = ticketService.findbyIdandCliente(id, id2);
		
		if(ticket!=null) {
			TicketDTO tDTO = new TicketDTO(ticket);
			return ResponseEntity.ok(tDTO);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe un ticket o un cliente");
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(
			value= "Borra un cliente por su ID"
			)
	public ResponseEntity<?> delete(@PathVariable String id){
		Optional<Cliente> cliente = clienteService.findById(id);
		String nombre = null;
		Usuario usuario = null;
		
		if(cliente.isPresent()) {
			nombre = cliente.get().getNombre();
			usuario = usuarioService.findByNombre(nombre);
		}
		
		boolean delete = clienteService.deleteById(id);

		if (delete) {
			boolean deleteUser = usuarioService.deleteById(usuario.getId());
			
			if(deleteUser) {
				return ResponseEntity.ok("se ha borrado " + id);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("no se ha podido realizar el borrado de usuario");
			} 
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("no se ha podido realizar el borrado del id " + id);
		}
			
	}
	
	@PostMapping
	@Transactional
	@ApiOperation(
			value= "Guarda un nuevo cliente en la base de datos"
			)
	public ResponseEntity<?> save(@RequestBody UsuarioClienteDTOSave clienteDTO){
		
		Usuario usuario = new Usuario();
		usuario.setUsername(clienteDTO.getNombre());
		String passwordHash = BCrypt.hashpw(clienteDTO.getPassword(), BCrypt.gensalt(10));
		usuario.setPassword(passwordHash);
		Optional<Role> rol = rolservice.findById(1);
		usuario.setRole(rol.get());
		
		Usuario save = usuarioService.save(usuario);
		
		if(save!=null) {
			Cliente cliente = new Cliente();
			cliente.setNombre(clienteDTO.getNombre());
			cliente.setIdCliente(clienteDTO.getIdCliente());
			cliente.setDireccion(clienteDTO.getDireccion());
			cliente.setUsuario(save);
			cliente.setTelefono(clienteDTO.getTelefono());
			
			Cliente save2 = clienteService.save(cliente);
			
			if(save2!=null) {
				return ResponseEntity.ok(new ClienteDTO(save2));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo guardar el usuario");
		}
		/*
		Optional<Usuario> usuario = usuarioService.findById(clienteDTO.getIdUsuario());
		
		if(usuario.isPresent()) {
			Cliente cliente = new Cliente();
			//recoger el id de usuario y buscar por id
			cliente.setIdCliente(clienteDTO.getIdCliente());
			cliente.setNombre(clienteDTO.getNombre());
			cliente.setDireccion(clienteDTO.getDireccion());
			cliente.setTelefono(clienteDTO.getTelefono());
			cliente.setUsuario(usuario.get());
			
			Cliente save = null;
			save = clienteService.save(cliente);
			ClienteDTO cDTO = new ClienteDTO(save);
			
			if(save!=null) {
				return ResponseEntity.ok(cDTO);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id de usuario no existe");
		}
		*/
	}
	
	@PutMapping("/{id}")
	@ApiOperation(
			value= "Actualiza un cliente mediante su ID registrado previamente"
			)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody ClienteDTO clienteDTO){
		Optional<Cliente> cliente = clienteService.findById(id);
		Optional<Usuario> usuario = usuarioService.findById(clienteDTO.getIdUsuario());
		
		if(cliente.isPresent() && usuario.isPresent()) {
			Cliente clienteUpdate = cliente.get();
			clienteUpdate.setIdCliente(id);
			clienteUpdate.setNombre(clienteDTO.getNombre());
			clienteUpdate.setDireccion(clienteDTO.getDireccion());
			clienteUpdate.setTelefono(clienteDTO.getTelefono());
			clienteUpdate.setUsuario(usuario.get());
			clienteService.save(clienteUpdate);
			ClienteDTO cDTO = new ClienteDTO(clienteUpdate);
			
			return ResponseEntity.ok(cDTO);
		} else {
			return
					ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id o el usuario del registro no existe");
		}
	}

}
