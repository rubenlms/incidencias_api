package es.iespuertodelacruz.rl.proyectodespring.dto;

import javax.persistence.Id;

import es.iespuertodelacruz.rl.proyectodespring.entities.Gestore;

public class GestorDTO {
	
	private static final long serialVersionUID = 1L;

	private String dni;
	private String apellidos;
	private String nombre;
	private int idUsuario;
	
	public GestorDTO(Gestore g) {
		super();
		this.dni = g.getDni();
		this.apellidos = g.getApellidos();
		this.nombre = g.getNombre();
		this.idUsuario = g.getUsuario().getId();
	}
	
	public GestorDTO() {
		super();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
