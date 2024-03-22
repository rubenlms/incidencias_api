package es.iespuertodelacruz.rl.proyectodespring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	/*
	@Query("INSERT INTO equipos_trabajo (id_ticket, dni) VALUES (:idticket, :dni)")
	public void saveEquipo(@Param("idticket") int idticket, @Param("dni") String dni);
	
	@Query("DELETE FROM equipos_trabajo WHERE id_ticket = :idticket")
	public void deleteEquipo(@Param("idticket") int idticket);
	*/
	
	@Query("SELECT t FROM Ticket t WHERE t.cliente = :cliente")
	List<Ticket> findTicketsByCliente(@Param("cliente") Cliente cliente);
	
	@Query("SELECT t FROM Ticket t WHERE t.cliente = :cliente AND t.idTicket = :idticket")
	Ticket findSingleTicketByCliente(@Param("cliente") Cliente cliente, @Param("idticket") Integer idticket);
}
