package com.tuvn.webNA.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "notas")
public class Notas implements Serializable{

	private static final long serialVersionUID = 1L;

	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_notas")
	private Integer idNotas;
			
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tiponota")
	private TipoNota idTipoNota;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_matriculadetalle")
	private MatriculaDetalle idMatriculaDetalle;
	
	@Column(nullable = false, length = 2, precision = 2)
	private Double nota;
	
	@Column(nullable = false, length = 1,name = "not_estado")
	private Integer notEstado = 1;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();

	public Integer getIdNotas() {
		return idNotas;
	}

	public void setIdNotas(Integer idNotas) {
		this.idNotas = idNotas;
	}

	public TipoNota getIdTipoNota() {
		return idTipoNota;
	}

	public void setIdTipoNota(TipoNota idTipoNota) {
		this.idTipoNota = idTipoNota;
	}
	
	public MatriculaDetalle getIdMatriculaDetalle() {
		return idMatriculaDetalle;
	}

	public void setIdMatriculaDetalle(MatriculaDetalle idMatriculaDetalle) {
		this.idMatriculaDetalle = idMatriculaDetalle;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Integer getNotEstado() {
		return notEstado;
	}

	public void setNotEstado(Integer notEstado) {
		this.notEstado = notEstado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Notas [idNotas=" + idNotas + ", idTipoNota=" + idTipoNota + ", idMatriculaDetalle=" + idMatriculaDetalle
				+ ", nota=" + nota + ", notEstado=" + notEstado + ", fechaCreacion=" + fechaCreacion + "]";
	}

	
	
}
