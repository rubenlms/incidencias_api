package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.service.RolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/roles")
@Api(tags="Rol REST (v3)", description="Controlador para los roles posibles de la aplicación")
public class RolREST {
	
	@Autowired
	RolService rolService;
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener un rol por su ID"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<Role> rol = rolService.findById(id);
		
		if(rol.isPresent()) {
			return ResponseEntity.ok(rol.get());
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
		
	}
}
