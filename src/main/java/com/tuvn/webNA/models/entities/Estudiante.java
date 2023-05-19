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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estudiante")
	private Integer idEstudiante;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_carrera")
	private Carrera idCarrera;
	
	@Column(nullable = false, length = 50)
	private String nombre;
	
	@Column(nullable = false, length = 50)
	private String apellido;
	
	@Column(nullable = false, unique = true, name = "ci_estudiante")
	private String ciEstudiante;
	
	@Column(nullable = false, length = 150)
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();
	
	@Column(nullable = false, length = 1)
	private Integer estado = 1;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idEstudiante")
	private List<MatriculaDetalle> lstEstudiante = new ArrayList<MatriculaDetalle>();
	
	public Estudiante () {
		
	}

	public Integer getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Integer idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCiEstudiante() {
		return ciEstudiante;
	}

	public void setCiEstudiante(String ciEstudiante) {
		this.ciEstudiante = ciEstudiante;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}



	public Carrera getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Carrera idCarrera) {
		this.idCarrera = idCarrera;
	}

	public List<MatriculaDetalle> getLstEstudiante() {
		return lstEstudiante;
	}

	public void setLstEstudiante(List<MatriculaDetalle> lstEstudiante) {
		this.lstEstudiante = lstEstudiante;
	}

	@Override
	public String toString() {
		return "Estudiante [idEstudiante=" + idEstudiante + ", idCarrera=" + idCarrera + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", ciEstudiante=" + ciEstudiante + ", email=" + email
				+ ", fechaNacimiento=" + fechaNacimiento + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado
				+ ", lstEstudiante=" + lstEstudiante + "]";
	}

	


}
