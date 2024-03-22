package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rl.proyectodespring.dto.SeguimientoDTO;
import es.iespuertodelacruz.rl.proyectodespring.entities.*;
import es.iespuertodelacruz.rl.proyectodespring.service.ClienteService;
import es.iespuertodelacruz.rl.proyectodespring.service.GestorService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.TicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/seguimientos")
@Api(tags="SeguimientoTicket REST (v2)", description="Controlador para los seguimientos de los tickets de un cliente")
public class SeguimientoTicketRESTv2 {
	
	@Autowired
	SeguimientoTicketService seguimientoService;
	@Autowired
	TicketService ticketService;
	@Autowired
	GestorService gestorService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ClienteService clienteService;
	
	/**
	 * Devuelve el cliente actual registrado, buscando por el usuario registrado
	 * @return
	 */
	private Cliente getCurrentCliente() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		Usuario usuario = usuarioService.findByNombre(username);
		Cliente cliente = clienteService.findByUsuario(usuario);
		
		return cliente;
	}
	
	@GetMapping
	@ApiOperation(
			value= "Obtener todos los seguimientos de los tickets del cliente que ha iniciado sesión",
			notes= "Muestra solo aquellos seguimientos de sus propios tickets"
			)
	public ResponseEntity<?> findAll(){
		ArrayList<SeguimientoDTO>seguimientos = new ArrayList<SeguimientoDTO>();
		Cliente cliente = getCurrentCliente();
		seguimientoService.findbyCliente(cliente).forEach(s -> {
			SeguimientoDTO sDTO = new SeguimientoDTO(s);
			seguimientos.add(sDTO);
		});
		
		return ResponseEntity.ok().body(seguimientos);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener un seguimiento por su ID",
			notes= "Se comprueba asimismo que el seguimiento que se pretende mostrar está asociado a un ticket de ese mismo cliente que ha iniciado sesión"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<SeguimientoTicket> segTicket = seguimientoService.findById(id);
		Cliente cliente = getCurrentCliente();
		
		if(segTicket.isPresent()) {
			Optional<Ticket> ticket = ticketService.findById(segTicket.get().getTicket().getIdTicket());
			
			if(ticket.isPresent() && ticket.get().getCliente().equals(cliente)) {
				SeguimientoDTO sDTO = new SeguimientoDTO(segTicket.get());
				return ResponseEntity.ok(sDTO);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no puedes ver el seguimiento");
			}
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
}
