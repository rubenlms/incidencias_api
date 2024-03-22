package es.iespuertodelacruz.rl.proyectodespring.dto;

import es.iespuertodelacruz.rl.proyectodespring.entities.Role;
import es.iespuertodelacruz.rl.proyectodespring.entities.Usuario;

public class UsuarioDTOSave {
	
	private static final long serialVersionUID = 1L;
	
	//private int id;
	private String username;
	private String password;
	//private Role rol;
	private int idrol;
	
	public UsuarioDTOSave() {}
	
	public UsuarioDTOSave(Usuario u) {
		super();
		//this.id = u.getId();
		this.username = u.getUsername();
		this.password = u.getPassword();
		//this.rol = u.getRole();
		this.idrol = u.getRole().getId();
	}
	/*
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	/*
	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}*/

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdrol() {
		return idrol;
	}

	public void setIdrol(int idrol) {
		this.idrol = idrol;
	}

}
