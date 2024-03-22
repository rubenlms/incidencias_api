package es.iespuertodelacruz.rl.proyectodespring.dto;

public class UsuarioGestorDTOSave {
	
	private String dni;
	private String password;
	private String nombre;
	private String apellidos;
	
	public UsuarioGestorDTOSave() {}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
}
