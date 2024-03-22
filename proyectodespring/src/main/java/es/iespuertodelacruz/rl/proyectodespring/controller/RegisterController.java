package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rl.proyectodespring.dto.ClienteDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioClienteDTOSave;
import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioDTOSave;
import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.security.GestorDeJWT;
import es.iespuertodelacruz.rl.proyectodespring.service.ClienteService;
import es.iespuertodelacruz.rl.proyectodespring.service.RolService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin
@Api(tags="Register REST (v1)", description="Controlador para el registro de un nuevo usuario")
public class RegisterController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	RolService rolservice;
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@PostMapping(path = "/api/v1/register")
	@Transactional
	@ApiOperation(
			value= "Guarda un nuevo usuario que se autoregistra",
			notes= "Se crea a la vez un usuario y un cliente, por lo que es necesario los atributos de ambas clases. El nuevo usuario siempre va a ser un cliente."
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
	}
	/*
	@PostMapping(path = "/api/v1/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(
			value= "Guarda un nuevo usuario que se autoregistra",
			notes= "Se crea a la vez un usuario y un cliente, por lo que es necesario los atributos de ambas clases. El nuevo usuario siempre va a ser un cliente."
			)
	public ResponseEntity<?> save(
			@RequestParam("dni") String id_cliente,
			@RequestParam("username") String username, 
			@RequestParam("password") String pwd,
			@RequestParam("direccion") String direccion,
			@RequestParam("telefono") String telefono){
		Usuario usuario = new Usuario();
		usuario.setUsername(username);
		Optional<Role> rol = rolservice.findById(1);
		usuario.setRole(rol.get());
		String passwordHash = BCrypt.hashpw(pwd, BCrypt.gensalt(10));
		usuario.setPassword(passwordHash);
		Usuario save = usuarioService.save(usuario);
		
		if(save!=null) {
			Cliente cliente = new Cliente();
			cliente.setIdCliente(id_cliente);
			cliente.setNombre(username);
			cliente.setDireccion(direccion);
			cliente.setTelefono(telefono);
			cliente.setUsuario(save);
			
			Cliente clienteSave = clienteService.save(cliente);
			
			if(clienteSave!=null) {
				return ResponseEntity.ok(new ClienteDTO(clienteSave));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar un cliente");
			}
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar un usuario");
		}
		
	}*/

}
