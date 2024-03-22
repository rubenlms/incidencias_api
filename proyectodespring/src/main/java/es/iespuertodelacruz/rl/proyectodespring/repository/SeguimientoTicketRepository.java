package es.iespuertodelacruz.rl.proyectodespring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;
import es.iespuertodelacruz.rl.proyectodespring.entities.SeguimientoTicket;
import es.iespuertodelacruz.rl.proyectodespring.entities.Ticket;

public interface SeguimientoTicketRepository extends JpaRepository<SeguimientoTicket, Integer> {
	
	//pasar por un id
	@Query("DELETE FROM SeguimientoTicket s WHERE s.ticket.idTicket = 1")
	//"delete from Book b where b.title=:title"
	public boolean deleteSeguimientobyTicket(@Param("idTicket") Integer idTicket);
	
	@Query("SELECT s FROM SeguimientoTicket s WHERE s.ticket = :ticket")
	List<SeguimientoTicket> findbyTicket(@Param("ticket") Ticket ticket);
	
	@Query("SELECT s FROM SeguimientoTicket s WHERE s.gestore = :gestor")
	List<SeguimientoTicket> findYourSeguimientos(@Param("gestor") Gestore gestor);
}
