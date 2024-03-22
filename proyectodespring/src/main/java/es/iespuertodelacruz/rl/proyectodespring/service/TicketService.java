package es.iespuertodelacruz.rl.proyectodespring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iespuertodelacruz.rl.proyectodespring.entities.*;
import es.iespuertodelacruz.rl.proyectodespring.repository.ClienteRepository;
import es.iespuertodelacruz.rl.proyectodespring.repository.TicketRepository;

@Service
public class TicketService implements iService<Ticket, Integer>{
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public Iterable<Ticket> findAll() {
		List<Ticket> tickets = ticketRepository.findAll();
		return tickets;
	}

	@Override
	public Page<Ticket> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Ticket> findById(Integer id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		return ticket;
	}

	@Override
	public Ticket save(Ticket ticket) {
		Ticket saved = ticketRepository.save(ticket);
		return saved;
	}

	@Override
	public boolean deleteById(Integer idTicket) {
		boolean okborrado = false;
		
		if(idTicket>0) {
			
			try {
				ticketRepository.deleteById(idTicket);;
				okborrado = true;
			} catch(Exception e) {
				okborrado = false;
			}	
		}
		
		return okborrado;
	}
	
	@Transactional(readOnly = true)
	public List<Ticket> findbyCliente(String idCliente){
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		List<Ticket> tickets = null;
		
		if(cliente.isPresent()) {
			tickets = ticketRepository.findTicketsByCliente(cliente.get());
		}
		
		return tickets;
	}
	
	@Transactional(readOnly = true)
	public Ticket findbyIdandCliente(String idCliente, Integer idTicket) {
		Ticket ticket = new Ticket();
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		
		if(cliente.isPresent() && idTicket>0) {
			ticket = ticketRepository.findSingleTicketByCliente(cliente.get(), idTicket);
		}
		
		return ticket;
	}

}
