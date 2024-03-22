package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
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

import es.iespuertodelacruz.rl.proyectodespring.dto.GestorDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.SeguimientoDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.UsuarioGestorDTOSave;
import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;
import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.SeguimientoTicket;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;
import es.iespuertodelacruz.rl.proyectodespring.service.GestorService;
import es.iespuertodelacruz.rl.proyectodespring.service.RolService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@CrossOrigin
@RequestMapping("/api/v3/gestores")
@Api(tags="Gestor REST (v1)", description="Controlador para v3 de Gestor")
public class GestorREST {
	
	@Autowired
	GestorService gestorService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	RolService rolService;
	
	@Autowired
	SeguimientoTicketService seguimientoService;
	
	@GetMapping
	@ApiOperation(
		value= "Obtener todos los gestores",
		notes= "Muestra todos los gestores pero no sus tickets o seguimientos asociados"
		)
	public ResponseEntity<?> findAll(){
		ArrayList<GestorDTO>gestores = new ArrayList<GestorDTO>();
		
		gestorService.findAll().forEach(gestor -> {
			Gestore g = gestor;
			GestorDTO gDTO = new GestorDTO(g);
			gestores.add(gDTO);
		});
		
		return ResponseEntity.ok().body(gestores);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener todos los gestor por su ID",
			notes= "Muestra el gestor pero no sus tickets o seguimientos asociados"
			)
	public ResponseEntity<?> findById(@PathVariable String id){
		Optional<Gestore>gestor = gestorService.findById(id);
		
		if(gestor.isPresent()) {
			GestorDTO gDTO = new GestorDTO(gestor.get());
			return ResponseEntity.ok(gDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
	
	
	@GetMapping("/{id}/seguimientos")
	@ApiOperation(
			value= "Obtener todos los seguimientos en los que ha participado ese gestor"
			)
	public ResponseEntity<?> findYourSeguimientos(@PathVariable String id){
		Optional<Gestore>gestor = gestorService.findById(id);
		
		if(gestor.isPresent()) {
			ArrayList<SeguimientoDTO>seguimientos = new ArrayList<>();
			
			seguimientoService.findYourSeguimientos(gestor.get())
				.forEach(seg -> {
					SeguimientoDTO sDTO = new SeguimientoDTO(seg);
					seguimientos.add(sDTO);
					});
			
			return ResponseEntity.ok().body(seguimientos); 
		
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@ApiOperation(
			value= "Elimina el gestor mediante su id"
			)
	public ResponseEntity<?> delete(@PathVariable String id){
		Optional<Gestore> gestor = gestorService.findById(id);
		String nombre = null;
		Usuario usuario = null;
		
		if(gestor.isPresent()) {
			nombre = gestor.get().getNombre();
			usuario = usuarioService.findByNombre(nombre);
			System.out.println("encuentra" + usuario.toString());
		}
		
		boolean delete = gestorService.deleteById(id);
		
		if (delete) {
			if(usuario!=null) {
				boolean deleteUser = usuarioService.deleteById(usuario.getId());
				
				if(deleteUser) {
					return ResponseEntity.ok("se ha borrado " + id);
				}else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("no se ha podido realizar el borrado del usuario");
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("no se ha podido encontrar el usuario");
			}
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("no se ha podido realizar el borrado del id " + id);
		}
	}
	
	@PostMapping
	@Transactional
	@ApiOperation(
			value= "Guarda un nuevo gestor en la base de datos"
			)
	public ResponseEntity<?> save(@RequestBody UsuarioGestorDTOSave ugDTO){
		
		Logger logger = Logger.getLogger("debug");
		logger.info("llega a post " + ugDTO.getDni());
		
		Usuario usuario = new Usuario();
		Optional<Role> rol = rolService.findById(2);
		usuario.setUsername(ugDTO.getNombre());
		String passwordHash = BCrypt.hashpw(ugDTO.getPassword(), BCrypt.gensalt(10));
		usuario.setPassword(passwordHash);
		usuario.setRole(rol.get());
		
		Usuario save = usuarioService.save(usuario);
		
		if(save!=null) {
			logger.info("guarda el usuario");
			Gestore newGestor = new Gestore();
			
			newGestor.setDni(ugDTO.getDni());
			newGestor.setNombre(ugDTO.getNombre());
			newGestor.setApellidos(ugDTO.getApellidos());
			newGestor.setUsuario(save);
			
			Gestore save2 = gestorService.save(newGestor);
			
			if(save2!=null) {
				logger.info("guarda el gestor");
				return ResponseEntity.ok(new GestorDTO(save2));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el usuario no es correcto");
		}
		
		/*
		Logger logger = Logger.getLogger("debug");
		logger.info("llega a post " + gestorDTO.getNombre());
		
		Optional<Usuario>usuario = usuarioService.findById(gestorDTO.getIdUsuario());
		logger.info("el usuario encontrado en DDBB " + usuario.get().getUsername());
		if(usuario.isPresent()) {
			Gestore newGestor = new Gestore();
			
			newGestor.setDni(gestorDTO.getDni());
			newGestor.setNombre(gestorDTO.getNombre());
			newGestor.setApellidos(gestorDTO.getApellidos());
			newGestor.setUsuario(usuario.get());
			logger.info(" se va a guardar " + newGestor.getNombre());
			Gestore save = gestorService.save(newGestor);
			logger.info(" se ha obtenido el id: " + save.getDni());
			GestorDTO gDTO = new GestorDTO(save);
			
			
			if(save!=null) {
				return ResponseEntity.ok(gDTO);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el usuario no es correcto");
		}
		
		*/
	}
	
	@PutMapping("/{id}")
	@ApiOperation(
			value= "Actualiza los valores del gestor que se le pasa por su ID"
			)
	public ResponseEntity<?> update(@PathVariable String id, @RequestBody GestorDTO gestorDTO){
		Optional<Gestore> gestorFound = gestorService.findById(id);
		Optional<Usuario> usuarioFound = usuarioService.findById(gestorDTO.getIdUsuario());
		
		if(gestorFound.isPresent() && usuarioFound.isPresent()) {
			Gestore gestorUpdate = gestorFound.get();
			gestorUpdate.setDni(gestorDTO.getDni());
			gestorUpdate.setNombre(gestorDTO.getNombre());
			gestorUpdate.setApellidos(gestorDTO.getApellidos());
			gestorUpdate.setUsuario(usuarioFound.get());
			
			Gestore save = gestorService.save(gestorUpdate);
			GestorDTO gDTO = new GestorDTO(save);
			
			return ResponseEntity.ok(gDTO);
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id o el usuario del registro no existe");
		}
	}
	
	
}
