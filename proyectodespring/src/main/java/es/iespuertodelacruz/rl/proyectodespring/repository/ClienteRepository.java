package es.iespuertodelacruz.rl.proyectodespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
	
	@Query("SELECT c FROM Cliente c WHERE c.usuario = :usuario")
	Cliente findClienteByUsuario(@Param("usuario") Usuario usuario);
}
