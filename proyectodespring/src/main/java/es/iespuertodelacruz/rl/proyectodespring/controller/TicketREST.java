package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rl.proyectodespring.service.ClienteService;
import es.iespuertodelacruz.rl.proyectodespring.service.GestorService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import es.iespuertodelacruz.rl.proyectodespring.dto.SeguimientoDTO;
import es.iespuertodelacruz.rl.proyectodespring.dto.TicketDTO;
import es.iespuertodelacruz.rl.proyectodespring.entities.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/tickets")
@Api(tags="Ticket REST (v3)", description="Controlador para los tickets registrados")
public class TicketREST {
	
	@Autowired
	TicketService ticketService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	GestorService gestorService;
	@Autowired
	SeguimientoTicketService seguimientoService;
	
	@GetMapping
	@ApiOperation(
			value= "Obtener todos los tickets registrados"
			)
	public ResponseEntity<?> findAll(){
		
		ArrayList<TicketDTO>tickets = new ArrayList<TicketDTO>();
		ticketService.findAll().forEach(t -> {
			Ticket ticket = t;
			TicketDTO tDTO = new TicketDTO(ticket);
			tickets.add(tDTO);
			});
		
		return ResponseEntity.ok().body(tickets);
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener un ticket por su ID"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<Ticket>ticket = ticketService.findById(id);
		
		if(ticket.isPresent()) {
			TicketDTO tDTO = new TicketDTO(ticket.get());
			return ResponseEntity.ok(tDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
	

	@GetMapping("/{id}/seguimientos")
	@ApiOperation(
			value= "Obtener todos los seguimientos del ticket que se le pasa ID por parámetro."
			)
	public ResponseEntity<?> findAllSeguimientos(@PathVariable Integer id){
		ArrayList<SeguimientoDTO> seguimientos = new ArrayList<SeguimientoDTO>();
		
		seguimientoService.findbyTicket(id).forEach(s -> {
				SeguimientoTicket seg = s;
				SeguimientoDTO sDTO = new SeguimientoDTO(seg);
				seguimientos.add(sDTO);
			});
		
		return ResponseEntity.ok().body(seguimientos);
		
	}
	
	
	@PostMapping
	@ApiOperation(
			value= "Guardar un nuevo ticket"
			)
	public ResponseEntity<?> save(@RequestBody TicketDTO tDTO){
		System.out.println("entro en function"); 
		Ticket ticket = new Ticket();
		System.out.println("id cliente" + tDTO.getCliente());
		//Gestore gestor = asignarGestor();
		Optional<Cliente> clienteTicket = clienteService.findById(tDTO.getCliente());
		
		System.out.println("se encuentra " + clienteTicket.get().getNombre());
		if(clienteTicket.isPresent()) {
			System.out.println("entro al bucle");
			ticket.setCliente(clienteTicket.get());
			ticket.setDescripcion(tDTO.getDescripcion());
			ticket.setEstado(tDTO.getEstado());
			ticket.setFechaInicio(tDTO.getFechaInicio());
			ticket.setFechaFin(tDTO.getFechaFin());
			ticket.setIdTicket(tDTO.getIdTicket());
			/*ArrayList<Gestore>gestores = new ArrayList<>();
			gestores.add(gestor);
			ticket.setGestores(gestores);*/
			
			Ticket save = ticketService.save(ticket);
			TicketDTO ticketDTO = new TicketDTO(save);
			System.out.println("muestro el guardado" + save.toString());
			
			if(save!=null) {
				return ResponseEntity.ok(ticketDTO);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el cliente o el gestor no existe");
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(
			value= "Borra un ticket por su ID"
			)
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Optional<Ticket> ticket = ticketService.findById(id);
		
		if(ticket.isPresent()) {
			List<SeguimientoTicket> seguimientoTickets = seguimientoService.findbyTicket(id);
			
			if(seguimientoTickets.size()>0 || seguimientoTickets!=null) {
				seguimientoTickets.forEach(
						seg -> {
							seguimientoService.deleteById(seg.getIdSeguimiento());
							}
						);	
			}
			
			boolean delete = ticketService.deleteById(id);

			if (delete) {
				return ResponseEntity.ok("se ha borrado " + id);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se ha podido realizar el borrado del id " + id);
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe el ticket " + id);
		}
		
		
		
	}
	
	@PutMapping("/{id}")
	@ApiOperation(
			value= "Actualiza el ticket que se le pasa por parámetro"
			)
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TicketDTO tDTO){
		Optional<Ticket> found = ticketService.findById(id);
		Optional<Cliente> clienteTicket = clienteService.findById(tDTO.getCliente());
		
		if(found.isPresent() && clienteTicket.isPresent()) {
			Ticket ticketUpdate = found.get();
			ticketUpdate.setDescripcion(tDTO.getDescripcion());
			ticketUpdate.setFechaInicio(tDTO.getFechaInicio());
			ticketUpdate.setFechaFin(tDTO.getFechaFin());
			ticketUpdate.setEstado(tDTO.getEstado());
			ticketUpdate.setCliente(clienteTicket.get());
			Ticket save = ticketService.save(ticketUpdate);
			
			TicketDTO ticketDTO = new TicketDTO(save);
			return ResponseEntity.ok(ticketDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
	
	public Gestore asignarGestor() {
		ArrayList<Gestore>gestores = new ArrayList<>();
		gestorService.findAll().forEach(gestor -> {
			gestores.add(gestor);
		});
		
		int random = (int) (Math.random()*gestores.size());
		System.out.println("aleatorio " + random);
		Gestore gestor = gestores.get(random);
		System.out.println(gestor.getNombre());
		
		return gestor;
	}
	
	
}
