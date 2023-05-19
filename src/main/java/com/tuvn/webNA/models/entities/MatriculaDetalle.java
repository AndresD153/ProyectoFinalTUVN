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
@Table(name = "matricula_detalle")
public class MatriculaDetalle implements Serializable{

	private static final long serialVersionUID = 1L;

	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_matricula_detalle")
	private Integer idMatriculaDetalle;
		
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estudiante")
	private Estudiante idEstudiante;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_matricula")
	private Matricula idMatricula;
	
	@Column(nullable = false, length = 1, name = "matr_estado")
	private Integer matrEstado = 1;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idMatriculaDetalle")
	private List<Asistencia> lstAsistencia = new ArrayList<Asistencia>();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idMatriculaDetalle")
	private List<Notas> lstNotas = new ArrayList<Notas>();
	
	public MatriculaDetalle () {
		
	}

	public Integer getIdMatriculaDetalle() {
		return idMatriculaDetalle;
	}

	public void setIdMatriculaDetalle(Integer idMatriculaDetalle) {
		this.idMatriculaDetalle = idMatriculaDetalle;
	}

	public Estudiante getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Estudiante idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	
	public Matricula getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(Matricula idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Integer getMatrEstado() {
		return matrEstado;
	}

	public void setMatrEstado(Integer matrEstado) {
		this.matrEstado = matrEstado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "MatriculaDetalle [idMatriculaDetalle=" + idMatriculaDetalle + ", idEstudiante=" + idEstudiante
				+ ", idMatricula=" + idMatricula + ", matrEstado=" + matrEstado + ", fechaCreacion=" + fechaCreacion
				+ "]";
	}

	
	
 	
}
