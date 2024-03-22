package es.iespuertodelacruz.rl.proyectodespring.dto;

import es.iespuertodelacruz.rl.proyectodespring.entities.Cliente;

public class ClienteDTO {
	
	private static final long serialVersionUID = 1L;
	private String idCliente;
	private String direccion;
	private String nombre;
	private String telefono;
	private int idUsuario;
	
	public ClienteDTO(Cliente c) {
		super();
		this.idCliente = c.getIdCliente();
		this.nombre = c.getNombre();
		this.direccion = c.getDireccion();
		this.telefono = c.getTelefono();
		this.idUsuario = c.getUsuario().getId();
	}
	
	public ClienteDTO() {
		super();
	}
	
	//poner un metodo toEntity

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
