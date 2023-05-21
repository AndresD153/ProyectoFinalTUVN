package com.tuvn.webNA.models.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Anotacion para definir la clase como entidad
@Entity
//Definir el alias de la tabla en la base de datos
@Table(name = "asignatura")
public class Asignatura implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Anotacion para definir la pimaryKey, con un autoincrementable y definir su nombre en la base de datos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_asignatura")
	private Integer idAsignatura;
	
	@Column(nullable = false, length = 50)
	private String nombre;
	
	@Column(nullable = false, length = 50)
	private String detalle;
	
	@Column(nullable = false, length = 1)
	private Integer estado = 1;
	
	//Crear una relacion Uno a Muchos con el campo IdAsignatura que se encuentra en la tabla Matricula
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idAsignatura")
	private List<Matricula> lstMatricula = new ArrayList<Matricula>();
	
	public Asignatura () {
		
	}

	//--------------------------------------------------------------------
	//--------------Metodos GET y SET ------------------------------------
	//--------------------------------------------------------------------
	public Integer getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Integer idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Asignatura [idAsignatura=" + idAsignatura + ", nombre=" + nombre + ", detalle=" + detalle + ", estado="
				+ estado + "]";
	}
	
	
	
}
