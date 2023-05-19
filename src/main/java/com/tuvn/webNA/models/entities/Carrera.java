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

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "carrera")
public class Carrera  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrera")
	private Integer idCarrera;
	
	@Column(nullable = false, length = 25)
	private String nombre;
	
	@Column(nullable = false, length = 1)
	private Integer estado = 1;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idCarrera")
	private List<Estudiante> lstEsudiante = new ArrayList<Estudiante>();
	
	public Carrera () {
		
	}

	public Integer getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Carrera [idCarrera=" + idCarrera + ", nombre=" + nombre + ", estado=" + estado + "]";
	}
	
	
}
