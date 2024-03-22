package es.iespuertodelacruz.rl.proyectodespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;

public interface GestorRepository extends JpaRepository<Gestore, String> {
	
	/*
	@Query("DELETE FROM gestores WHERE dni = :dni")
	public boolean deleteGestor(@Param("dni") String dni);
	*/
}
