package com.tuvn.webNA.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "matricula")
public class Matricula implements Serializable{

	private static final long serialVersionUID = 1L;

	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_matricula")
	private Integer idMatricula;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profesor")
	private Profesor idProfesor;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_asignatura")
	private Asignatura idAsignatura;
	
	@Column(nullable = false, length = 1, name = "estado")
	private Integer estado = 1;
	
	@Column(length = 10, name = "codigo")
	private String codigo;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idMatricula")
	private List<MatriculaDetalle> lstMatriculaDetalles = new ArrayList<MatriculaDetalle>();
	
	public Matricula () {
		
	}

	public Integer getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(Integer idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Profesor getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Profesor idProfesor) {
		this.idProfesor = idProfesor;
	}

	public Asignatura getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Asignatura idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<MatriculaDetalle> getLstMatriculaDetalles() {
		return lstMatriculaDetalles;
	}

	public void setLstMatriculaDetalles(List<MatriculaDetalle> lstMatriculaDetalles) {
		this.lstMatriculaDetalles = lstMatriculaDetalles;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Matricula [idMatricula=" + idMatricula + ", idProfesor=" + idProfesor + ", idAsignatura=" + idAsignatura
				+ ", estado=" + estado + ", codigo=" + codigo + ", fechaCreacion=" + fechaCreacion
				+ ", lstMatriculaDetalles=" + lstMatriculaDetalles + "]";
	}
	
}
