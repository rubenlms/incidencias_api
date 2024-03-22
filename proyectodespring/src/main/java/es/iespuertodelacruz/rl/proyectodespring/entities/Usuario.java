package es.iespuertodelacruz.rl.proyectodespring.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String password;

	private String username;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Cliente> clientes;

	//bi-directional many-to-one association to Gestore
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
	private List<Gestore> gestores;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Role role;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setUsuario(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setUsuario(null);

		return cliente;
	}

	public List<Gestore> getGestores() {
		return this.gestores;
	}

	public void setGestores(List<Gestore> gestores) {
		this.gestores = gestores;
	}

	public Gestore addGestore(Gestore gestore) {
		getGestores().add(gestore);
		gestore.setUsuario(this);

		return gestore;
	}

	public Gestore removeGestore(Gestore gestore) {
		getGestores().remove(gestore);
		gestore.setUsuario(null);

		return gestore;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}