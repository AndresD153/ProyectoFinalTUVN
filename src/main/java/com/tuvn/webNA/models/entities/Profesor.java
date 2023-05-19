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
@Table(name = "profesor")
public class Profesor implements Serializable{
	

	private static final long serialVersionUID = 1L;

	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_profesor")
	private Integer idProfesor;
	
	@Column(nullable = false, length = 50)
	private String nombre;
	
	@Column(nullable = false, length = 50)
	private String apellido;
	
	@Column(nullable = false, unique = true, length = 10, name = "ci_profesor")
	private String cedula;
	
	@Column(nullable = false, length = 2)
	private Integer edad;
	
	@Column(nullable = false, length = 150)
	private String email;
	
	@Column(nullable = false, length = 75)
	private String profecion;
	
	@Column(nullable = false, length = 50)
	private String usuario;
	
	@Column(nullable = false, length = 50)
	private String password;
	
	@Column(nullable = false, length = 1)
	private Integer rol = 1;
	
	@Column(nullable = false, length = 1)
	private Integer estado = 1;
	
	@Column(nullable = false, name = "fecha_creacion")
	private Date fechaCreacion = new Date();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idProfesor")
	private List<Matricula> lstMatricula = new ArrayList<Matricula>();
	
	public Profesor () {
	
	}

	public Integer getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(Integer idProfesor) {
		this.idProfesor = idProfesor;
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

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfecion() {
		return profecion;
	}

	public void setProfecion(String profecion) {
		this.profecion = profecion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Profesor [idProfesor=" + idProfesor + ", nombre=" + nombre + ", apellido=" + apellido + ", cedula="
				+ cedula + ", edad=" + edad + ", email=" + email + ", profecion=" + profecion + ", usuario=" + usuario
				+ ", password=" + password + ", rol=" + rol + ", estado=" + estado + ", fechaCreacion=" + fechaCreacion
				+ ", lstMatricula=" + lstMatricula + "]";
	}

	

}
