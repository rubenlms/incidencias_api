package es.iespuertodelacruz.rl.proyectodespring.dto;

import java.sql.Timestamp;


import es.iespuertodelacruz.rl.proyectodespring.entities.Ticket;

public class TicketDTO {
	
	private static final long serialVersionUID = 1L;
	private int idTicket;
	private String descripcion;
	private String estado;
	private Timestamp fechaFin;
	private Timestamp fechaInicio;
	private String cliente;
	private String idgestor;
	
	public TicketDTO(Ticket t) {
		super();
		this.idTicket = t.getIdTicket();
		this.descripcion = t.getDescripcion();
		this.estado = t.getEstado();
		this.fechaFin = t.getFechaFin();
		this.fechaInicio = t.getFechaInicio();
		this.cliente = t.getCliente().getIdCliente();
	}
	
	public Ticket toEntity(TicketDTO tDTO) {
		Ticket ticket = new Ticket();
		
		return ticket;
	}
	
	public TicketDTO() {
		super();
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
}
