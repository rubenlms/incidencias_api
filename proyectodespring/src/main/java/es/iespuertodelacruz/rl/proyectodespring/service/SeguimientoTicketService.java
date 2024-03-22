package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;
import es.iespuertodelacruz.rl.proyectodespring.entities.SeguimientoTicket;
import es.iespuertodelacruz.rl.proyectodespring.entities.Ticket;
import es.iespuertodelacruz.rl.proyectodespring.repository.SeguimientoTicketRepository;
import es.iespuertodelacruz.rl.proyectodespring.repository.TicketRepository;

@Service
public class SeguimientoTicketService implements iService<SeguimientoTicket, Integer> {
	
	@Autowired
	SeguimientoTicketRepository seguimientoRepository;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Override
	public Iterable<SeguimientoTicket> findAll() {
		List<SeguimientoTicket> findAll = seguimientoRepository.findAll();
		return findAll;
	}

	@Override
	public Page<SeguimientoTicket> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SeguimientoTicket> findById(Integer id) {
		Optional<SeguimientoTicket> findById = seguimientoRepository.findById(id);
		return findById;
	}

	@Override
	public SeguimientoTicket save(SeguimientoTicket obj) {
		return seguimientoRepository.save(obj);
	}

	@Override
	public boolean deleteById(Integer idSeguimiento) {
		boolean okborrado = false;
		
		if(idSeguimiento>0) {
			
			try {
				seguimientoRepository.deleteById(idSeguimiento);;
				okborrado = true;
			} catch(Exception e) {
				okborrado = false;
			}
			
		}
		
		return okborrado;
	}
	
	/**
	 * Borra todos los seguimientos de un ticket
	 * @param idTicket
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deletebyTicket(Integer idTicket) {
		boolean ok = false;
		boolean deleteSeguimientobyTicket = seguimientoRepository.deleteSeguimientobyTicket(idTicket);
		
		if(deleteSeguimientobyTicket) {
			ok = true;
		}
		/*
		if(idTicket>0) {
			
			try {
				Optional<Ticket> ticket = ticketRepository.findById(idTicket);
				
				if(ticket.isPresent()) {
					seguimientoRepository.deleteSeguimientobyTicket(ticket.get().getIdTicket());
					ok = true;
				}
				
			} catch(Exception e) {
				ok = false;
			}
		}*/
			
		return ok;
	}
	
	/**
	 * Devuelve los seguimientos de un ticket
	 * @param idTicket
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SeguimientoTicket> findbyTicket(Integer idTicket){
		Optional<Ticket> ticket = ticketRepository.findById(idTicket);
		List<SeguimientoTicket> seguimientosByTicket = null;
		
		if(ticket.isPresent()) {
			seguimientosByTicket  = seguimientoRepository.findbyTicket(ticket.get());
		}
		
		return seguimientosByTicket;
	}
	
	/**
	 * Devuelve todos los seguimientos de un cliente
	 * @param cliente
	 * @return
	 */
	public List<SeguimientoTicket> findbyCliente(Cliente cliente){
		List<Ticket>tickets = ticketRepository.findTicketsByCliente(cliente);
		ArrayList<SeguimientoTicket>seguimientos = new ArrayList<SeguimientoTicket>();
		
		for(Ticket t : tickets) {
			List<SeguimientoTicket> segByTicket = seguimientoRepository.findbyTicket(t);
			
			if(!segByTicket.isEmpty()) {
				for(SeguimientoTicket seg : segByTicket) {
					seguimientos.add(seg);
				}
			}
		}
		
		return seguimientos;
	}
	
	/**
	 * Devuelve todos los seguimientos en los que ha participado un gestor
	 * @param gestor
	 * @return
	 */
	public List<SeguimientoTicket> findYourSeguimientos(Gestore gestor){
		List<SeguimientoTicket> seguimientos = seguimientoRepository.findYourSeguimientos(gestor);
		return seguimientos;
	}

}
