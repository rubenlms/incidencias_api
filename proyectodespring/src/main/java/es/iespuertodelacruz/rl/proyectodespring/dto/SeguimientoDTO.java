package es.iespuertodelacruz.rl.proyectodespring.dto;

import java.sql.Timestamp;

import es.iespuertodelacruz.rl.proyectodespring.entities.SeguimientoTicket;

public class SeguimientoDTO {
	
	private static final long serialVersionUID = 1L;
	
	private int idSeguimiento;
	private String comentario;
	private Timestamp fecha;
	private String gestor;
	private int idTicket;
	
	public SeguimientoDTO(SeguimientoTicket seg) {
		super();
		this.idSeguimiento = seg.getIdSeguimiento();
		this.comentario = seg.getComentario();
		this.fecha = seg.getFecha();
		this.gestor = seg.getGestore().getDni();
		this.idTicket = seg.getTicket().getIdTicket();
	}
	
	public SeguimientoDTO() {
		super();
	}

	public int getIdSeguimiento() {
		return idSeguimiento;
	}

	public void setIdSeguimiento(int idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}
	
}
