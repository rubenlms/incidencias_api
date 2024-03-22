package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import es.iespuertodelacruz.rl.proyectodespring.service.ClienteService;
import es.iespuertodelacruz.rl.proyectodespring.service.GestorService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.TicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import es.iespuertodelacruz.rl.proyectodespring.dto.SeguimientoDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.TicketDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.TicketDTOv2;
import es.iespuertodelacruz.rl.proyectodespring.entities.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/tickets")
@Api(tags="Ticket REST (v2)", description="Controlador para los tickets de un cliente")
public class TicketRESTv2 {
	
	@Autowired
	TicketService ticketService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	GestorService gestorService;
	@Autowired
	SeguimientoTicketService seguimientoService;
	@Autowired
	UsuarioService usuarioService;
	
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
			value= "Obtener todos los tickets del cliente que ha iniciado sesión"
			)
	public ResponseEntity<?> findAll(){
		Cliente cliente = getCurrentCliente();
		ArrayList<TicketDTO>tickets = new ArrayList<TicketDTO>();
		ticketService.findbyCliente(cliente.getIdCliente()).forEach(t -> {
			TicketDTO tDTO = new TicketDTO(t);
			tickets.add(tDTO);
		});

		return ResponseEntity.ok().body(tickets);
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener un ticket a través de su ID",
			notes= "Para poder mostrarse deberá ser un ticket que esté asociado al cliente que ha iniciado sesión"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<Ticket>ticket = ticketService.findById(id);
		
		if(ticket.isPresent() && ticket.get().getCliente().equals(getCurrentCliente())) {
			TicketDTO tDTO = new TicketDTO(ticket.get());
			return ResponseEntity.ok(tDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
	
	@GetMapping("/{id}/seguimientos")
	@ApiOperation(
			value= "Obtener todos los seguimientos del ticket que se le pasa su ID por parámetro",
			notes= "Muestra solo aquellos seguimientos del ticket que se le pasa previa comprobación de que sea del cliente que ha iniciado sesión"
			)
	public ResponseEntity<?> findAllSeguimientos(@PathVariable Integer id){
		ArrayList<SeguimientoDTO> seguimientos = new ArrayList<SeguimientoDTO>();
		Optional<Ticket>ticket = ticketService.findById(id);
		
		if(ticket.get().getCliente().equals(getCurrentCliente())) {
			seguimientoService.findbyTicket(id).forEach(s -> {
				SeguimientoDTO sDTO = new SeguimientoDTO(s);
				seguimientos.add(sDTO);
			});
		}
		
		return ResponseEntity.ok().body(seguimientos);
		
	}
	
	
	@PostMapping
	@ApiOperation(
			value= "Guardar un nuevo ticket",
			notes= "Un cliente podrá crear sus propios tickets"
			)
	public ResponseEntity<?> save(@RequestBody TicketDTOv2 tDTO){
		
		Ticket ticket = new Ticket();
		Gestore gestor = asignarGestor();
		Cliente clienteTicket = getCurrentCliente();
		
		if(clienteTicket!=null && gestor!=null) {
			ticket.setCliente(clienteTicket);
			ticket.setDescripcion(tDTO.getDescripcion());
			ticket.setEstado(tDTO.getEstado());
			ticket.setFechaInicio(tDTO.getFechaInicio());
			ticket.setFechaFin(tDTO.getFechaFin());
			ticket.setIdTicket(tDTO.getIdTicket());
			ArrayList<Gestore>gestores = new ArrayList<>();
			gestores.add(gestor);
			ticket.setGestores(gestores);
			
			Ticket save = ticketService.save(ticket);
			TicketDTO ticketDTO = new TicketDTO(save);
			
			if(save!=null) {
				return ResponseEntity.ok(ticketDTO);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el cliente o el gestor no existe");
		}
		
	}
	
	/**
	 * Devuelve de forma aleatoria un gestor registrado
	 * @return
	 */
	public Gestore asignarGestor() {
		ArrayList<Gestore>gestores = new ArrayList<>();
		gestorService.findAll().forEach(gestor -> {
			gestores.add(gestor);
		});
		
		int random = (int) (Math.random()*gestores.size());
		Gestore gestor = gestores.get(random);
		
		return gestor;
	}
	
}
