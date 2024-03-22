package es.iespuertodelacruz.rl.proyectodespring.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import es.iespuertodelacruz.rl.proyectodespring.dto.SeguimientoDTO;
import es.iespuertodelacruz.rl.proyectodespring.entities.*;
import es.iespuertodelacruz.rl.proyectodespring.service.GestorService;
import es.iespuertodelacruz.rl.proyectodespring.service.SeguimientoTicketService;
import es.iespuertodelacruz.rl.proyectodespring.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/seguimientos")
@Api(tags="SeguimientoTicket REST (v3)", description="Controlador para los seguimientos de los tickets")
public class SeguimientoTicketREST {
	
	@Autowired
	SeguimientoTicketService seguimientoService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	GestorService gestorService;
	
	@GetMapping
	@ApiOperation(
			value= "Obtener todos los seguimientos de la base de datos",
			notes= "Muestra todos los gestores pero no la información completa de su ticket o del gestor que la ha realizado."
			)
	public ResponseEntity<?> findAll(){
		ArrayList<SeguimientoDTO>seguimientos = new ArrayList<SeguimientoDTO>();
		seguimientoService.findAll().forEach(s -> {
			SeguimientoTicket seg = s;
			SeguimientoDTO sDTO = new SeguimientoDTO(seg);
			seguimientos.add(sDTO);
		});
		
		return ResponseEntity.ok().body(seguimientos);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(
			value= "Obtener un seguimiento por su ID"
			)
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Optional<SeguimientoTicket> segTicket = seguimientoService.findById(id);
		
		if(segTicket.isPresent()) {
			SeguimientoDTO sDTO = new SeguimientoDTO(segTicket.get());
			return ResponseEntity.ok(sDTO);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el id del registro no existe");
		}
	}
	
	@PostMapping
	@ApiOperation(
			value= "Guarda un nuevo seguimiento"
			)
	public ResponseEntity<?> save(@RequestBody SeguimientoDTO sDTO){
		SeguimientoTicket seg = new SeguimientoTicket();
		
		Optional<Gestore> gestor = gestorService.findById(sDTO.getGestor());
		Optional<Ticket> ticket = ticketService.findById(sDTO.getIdTicket());
		
		if(ticket.isPresent() && gestor.isPresent()) {
			if(!(ticket.get().getGestores().contains(gestor.get()))){
				ticket.get().getGestores().add(gestor.get());
			}
			seg.setComentario(sDTO.getComentario());
			seg.setFecha(sDTO.getFecha());
			seg.setGestore(gestor.get());
			seg.setTicket(ticket.get());
			
			SeguimientoTicket save = seguimientoService.save(seg);
			
			if(save!=null) {
				SeguimientoDTO segDTO = new SeguimientoDTO(save);
				
				return ResponseEntity.ok(segDTO);
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el ticket o el gestor no son correctos");
		}
		
	}
	
	@PutMapping("/{id}")
	@ApiOperation(
			value= "Actualiza el seguimiento asociado al ID que se pasa por parámetro"
			)
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody SeguimientoDTO sDTO){
		Optional<SeguimientoTicket> seguimiento = seguimientoService.findById(id);
		Optional<Gestore> gestor = gestorService.findById(sDTO.getGestor());
		Optional<Ticket> ticket = ticketService.findById(sDTO.getIdTicket());
		
		if(seguimiento.isPresent() && ticket.isPresent() && gestor.isPresent()) {
			SeguimientoTicket segUpdate = seguimiento.get();
			segUpdate.setComentario(sDTO.getComentario());
			segUpdate.setFecha(sDTO.getFecha());
			segUpdate.setGestore(gestor.get());
			segUpdate.setTicket(ticket.get());
			
			SeguimientoTicket save = seguimientoService.save(segUpdate);
			
			if(save!=null) {
				SeguimientoDTO segDTO = new SeguimientoDTO(save);
				return ResponseEntity.ok(segDTO);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("algo ha fallado al guardar");
			}
			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se encontró un seguimiento con ese id");
		}
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(
			value= "Borra el seguimiento asociado al ID que se le pasa por parámetro"
			)
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		boolean delete = seguimientoService.deleteById(id);

		if (delete) {
			return ResponseEntity.ok("se ha borrado " + id);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("no se ha podido realizar el borrado del id " + id);
		}

	}
}
