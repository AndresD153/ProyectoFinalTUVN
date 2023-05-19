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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Defnir el nombre de la entidad con anotaciones
@Entity
@Table(name = "fecha_asistencia")
public class FechaAsistencia  implements Serializable{

	private static final long serialVersionUID = 1L;

	//Definir el ID con la primary key y autoicremtable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fechaasistencia")
	private Integer idFechaAsistencia;
		
	@Temporal(TemporalType.DATE)
	private Date fecha = new Date();
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "idFechaAsistencia")
	private List<Asistencia> lstAsistencia = new ArrayList<Asistencia>();
	
	public FechaAsistencia () {
		
	}

	public Integer getIdFechaAsistencia() {
		return idFechaAsistencia;
	}

	public void setIdFechaAsistencia(Integer idFechaAsistencia) {
		this.idFechaAsistencia = idFechaAsistencia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "FechaAsistencia [idFechaAsistencia=" + idFechaAsistencia + ", fecha=" + fecha + "]";
	}
	
	
}
