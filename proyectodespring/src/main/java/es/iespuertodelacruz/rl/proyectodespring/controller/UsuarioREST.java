package es.iespuertodelacruz.rl.proyectodespring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioDTOSave;
import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.service.RolService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/usuarios")
@Api(tags="Usuario REST (v3)", description="Controlador para los usuarios")
public class UsuarioREST {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolservice;
	
	@GetMapping
	@ApiOperation(
			value= "Obtiene todos los usuarios registrados"
			)
	public ResponseEntity<?> findAll(){
		ArrayList<UsuarioDTO>usuarios = new ArrayList<UsuarioDTO>();
		usuarioService.findAll().forEach(us -> {
			UsuarioDTO uDTO = new UsuarioDTO(us);
			usuarios.add(uDTO);
		});
		return ResponseEntity.ok().body(usuarios);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtiene un usuario por su ID"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<Usuario>usuario = usuarioService.findById(id);
		
		if(usuario.isPresent()) {
			UsuarioDTO uDTO = new UsuarioDTO(usuario.get());
			return ResponseEntity.ok(uDTO);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
		
	}
	
	@PutMapping("/{id}")
	@ApiOperation(
			value= "Actualiza el usuario por su ID"
			)
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UsuarioDTOSave uDTO){
		Optional<Usuario> usuario = usuarioService.findById(id);
		
		if(usuario.isPresent()) {
			Usuario usuarioUpdate = new Usuario();
			//System.out.println("se trae " + uDTO.getUsername() + uDTO.getPassword());
			usuarioUpdate.setId(usuario.get().getId());
			usuarioUpdate.setUsername(uDTO.getUsername());
			Optional<Role> rol = rolservice.findById(uDTO.getIdrol());
			usuarioUpdate.setRole(rol.get());
			String passwordHash = BCrypt.hashpw(uDTO.getPassword(), BCrypt.gensalt(10));
			usuarioUpdate.setPassword(passwordHash);
			//System.out.println("se recibe " + usuario.getUsername() + " " + usuario.getPassword());
			Usuario updated = usuarioService.save(usuarioUpdate);
			
			if(updated!=null) {
				return ResponseEntity.ok(new UsuarioDTO(updated));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al actualizar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe ese usuario");
		}
	}
	
	@PostMapping
	@ApiOperation(
			value= "Guarda un nuevo usuario",
			notes= "Podrá decidirse desde v3 si va a ser admin o user"
			)
	public ResponseEntity<?> save(@RequestBody UsuarioDTOSave uDTO){
		Usuario usuario = new Usuario();
		//System.out.println("se trae " + uDTO.getUsername() + uDTO.getPassword());
		usuario.setUsername(uDTO.getUsername());
		Optional<Role> rol = rolservice.findById(uDTO.getIdrol());
		usuario.setRole(rol.get());
		String passwordHash = BCrypt.hashpw(uDTO.getPassword(), BCrypt.gensalt(10));
		usuario.setPassword(passwordHash);
		//System.out.println("se recibe " + usuario.getUsername() + " " + usuario.getPassword());
		Usuario save = usuarioService.save(usuario);
		
		if(save!=null) {
			return ResponseEntity.ok(new UsuarioDTO(save));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(
			value= "Borra el usuario que se le pasa por ID"
			)
	public ResponseEntity<?> delete(@PathVariable Integer id){
		boolean delete = usuarioService.deleteById(id);
		
		if (delete) {
			return ResponseEntity.ok("se ha borrado " + id);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("no se ha podido realizar el borrado del id " + id);
		}
	}
}
