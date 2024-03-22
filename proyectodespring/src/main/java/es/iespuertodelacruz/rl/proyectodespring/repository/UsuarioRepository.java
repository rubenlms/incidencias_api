package es.iespuertodelacruz.rl.proyectodespring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.rl.proyectodespring.entities.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	@Query("SELECT us FROM Usuario us WHERE us.username = :username")
	Usuario findByName(@Param("username") String username);

}
