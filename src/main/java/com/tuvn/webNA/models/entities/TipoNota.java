package com.tuvn.webNA.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "tipo_nota")
public class TipoNota implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tiponota")
	private Integer idTipoNota;
	
	@Column(nullable = false, length = 50, name = "tipo_nombre")
	private String tipoNombre;
	
	@Column(nullable = false, length = 1,name = "tipo_estado")
	private Integer tipoEstado = 1;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idTipoNota")
	private List<Notas> lstNotas = new ArrayList<Notas>();
	
	public TipoNota () {
		
	}

	public Integer getIdTipoNota() {
		return idTipoNota;
	}

	public void setIdTipoNota(Integer idTipoNota) {
		this.idTipoNota = idTipoNota;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public Integer getTipoEstado() {
		return tipoEstado;
	}

	public void setTipoEstado(Integer tipoEstado) {
		this.tipoEstado = tipoEstado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "TipoNota [idTipoNota=" + idTipoNota + ", tipoNombre=" + tipoNombre + ", tipoEstado=" + tipoEstado
				+ ", fechaCreacion=" + fechaCreacion + "]";
	}
	
	
}
