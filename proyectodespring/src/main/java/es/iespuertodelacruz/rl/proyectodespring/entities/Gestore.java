package es.iespuertodelacruz.rl.proyectodespring.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gestores database table.
 * 
 */
@Entity
@Table(name="gestores")
@NamedQuery(name="Gestore.findAll", query="SELECT g FROM Gestore g")
public class Gestore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String dni;

	private String apellidos;

	private String nombre;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	//bi-directional many-to-one association to SeguimientoTicket
	@OneToMany(mappedBy="gestore")
	private List<SeguimientoTicket> seguimientoTickets;

	//bi-directional many-to-many association to Ticket
	@ManyToMany(mappedBy="gestores")
	private List<Ticket> tickets;

	public Gestore() {
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<SeguimientoTicket> getSeguimientoTickets() {
		return this.seguimientoTickets;
	}

	public void setSeguimientoTickets(List<SeguimientoTicket> seguimientoTickets) {
		this.seguimientoTickets = seguimientoTickets;
	}

	public SeguimientoTicket addSeguimientoTicket(SeguimientoTicket seguimientoTicket) {
		getSeguimientoTickets().add(seguimientoTicket);
		seguimientoTicket.setGestore(this);

		return seguimientoTicket;
	}

	public SeguimientoTicket removeSeguimientoTicket(SeguimientoTicket seguimientoTicket) {
		getSeguimientoTickets().remove(seguimientoTicket);
		seguimientoTicket.setGestore(null);

		return seguimientoTicket;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}