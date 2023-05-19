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

@Entity
@Table(name = "asistencia")
public class Asistencia implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Definir el ID con la primary key y autoicremtable
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_asistencia")
		private Integer idAsistencia;
		
		@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
		@JoinColumn(name = "id_matricula_detalle")
		private MatriculaDetalle idMatriculaDetalle;
		
		@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
		@JoinColumn(name = "id_fechaasistencia")
		private FechaAsistencia idFechaAsistencia;
		
		@Column(nullable = false, length = 1)
		private Integer asistencia;
		
		@Column(nullable = false, name = "fecha_creacion")
		private Date fechaCreacion = new Date();
		
		public Asistencia () {
			
		}

		public Integer getIdAsistencia() {
			return idAsistencia;
		}

		public void setIdAsistencia(Integer idAsistencia) {
			this.idAsistencia = idAsistencia;
		}

		public MatriculaDetalle getIdMatriculaDetalle() {
			return idMatriculaDetalle;
		}

		public void setIdMatriculaDetalle(MatriculaDetalle idMatriculaDetalle) {
			this.idMatriculaDetalle = idMatriculaDetalle;
		}

		public FechaAsistencia getIdFechaAsistencia() {
			return idFechaAsistencia;
		}

		public void setIdFechaAsistencia(FechaAsistencia idFechaAsistencia) {
			this.idFechaAsistencia = idFechaAsistencia;
		}

		public Integer getAsistencia() {
			return asistencia;
		}

		public void setAsistencia(Integer asistencia) {
			this.asistencia = asistencia;
		}

		public Date getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(Date fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}

		@Override
		public String toString() {
			return "Asistencia [idAsistencia=" + idAsistencia + ", idMatriculaDetalle=" + idMatriculaDetalle
					+ ", idFechaAsistencia=" + idFechaAsistencia + ", asistencia=" + asistencia + ", fechaCreacion="
					+ fechaCreacion + "]";
		}

}
