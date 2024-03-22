package es.iespuertodelacruz.rl.proyectodespring.dto;

import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;

public class UsuarioDTO {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private int rol;
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(Usuario u) {
		super();
		this.id = u.getId();
		this.username = u.getUsername();
		this.rol = u.getRole().getId();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol.getId();
	}
	
	
	

}
