package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.AsistenciaDao;
import com.tuvn.webNA.models.entities.Asistencia;

public class AsistenciaDaoImpl extends GenericaDaoImpl<Asistencia> implements AsistenciaDao{

	@Override
	public List<Asistencia> obtenerListaAsistencia() {
		try{
			TypedQuery<Asistencia> consulta = this.entityManager
					.createQuery("SELECT as FROM Asistencia as ORDER BY as.idAsistencia ASC", Asistencia.class);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearAsistencia(Asistencia asistencia) {
		try {
			this.beginTransaction();
			this.create(asistencia);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarAsistencia(Asistencia asistencia) {
		try {
			this.beginTransaction();
			this.update(asistencia);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public Asistencia buscarPorId(Integer id) {
		try {
			TypedQuery<Asistencia> consulta = this.entityManager
					.createQuery("SELECT as FROM Asistencia as WHERE as.idAsistencia = :id", Asistencia.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Asistencia();
		}
	}
	
	@Override
	public Asistencia buscarPrimeraAsistencia(Date fecha, String idMatricula) {
		try {
			TypedQuery<Asistencia> consulta = this.entityManager
					.createQuery("SELECT a FROM Asistencia a WHERE a.idMatriculaDetalle.idMatricula.codigo = :cod "
							+ "AND a.idFechaAsistencia.fecha = :fecha", Asistencia.class);
			consulta.setParameter("cod", idMatricula);
			consulta.setParameter("fecha", fecha);
			consulta.setMaxResults(1);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Asistencia();
		}
	}
	
	@Override
	public List<Asistencia> obtenerListaAsistenciasDia(String idMatricula) {
		try{
			TypedQuery<Asistencia> consulta = this.entityManager
					.createQuery("SELECT a.idMatriculaDetalle.idMatricula.codigo,  a.idFechaAsistencia.idFechaAsistencia, a.idFechaAsistencia.fecha, COUNT(a.idFechaAsistencia.idFechaAsistencia) FROM Asistencia a "
							+ "WHERE a.idMatriculaDetalle.idMatricula.codigo = :id "
							+ "GROUP BY a.idMatriculaDetalle.idMatricula.codigo,  a.idFechaAsistencia.idFechaAsistencia, a.idFechaAsistencia.fecha "
							+ "ORDER BY a.idFechaAsistencia.idFechaAsistencia DESC" , Asistencia.class);
			consulta.setParameter("id", idMatricula);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<Asistencia> obtenerListaAsistenciaEstudiante(Integer fecha,String idMatricula) {
		try{
			TypedQuery<Asistencia> consulta = this.entityManager
					.createQuery("SELECT a FROM Asistencia a "
							+ "WHERE a.idMatriculaDetalle.idMatricula.codigo = :cod AND a.idFechaAsistencia.idFechaAsistencia = :fecha "
							+ "ORDER BY a.idMatriculaDetalle.idEstudiante.apellido ASC" , Asistencia.class);
			consulta.setParameter("cod", idMatricula);
			consulta.setParameter("fecha", fecha);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public List<Object> obtenerReporteAsistencia(String idMatricula) {
		try{
			TypedQuery<Object> consulta = this.entityManager
					.createQuery("SELECT a.idMatriculaDetalle.idEstudiante.apellido, a.idMatriculaDetalle.idEstudiante.nombre, COUNT(a.idMatriculaDetalle.idEstudiante.idEstudiante) FROM Asistencia a "
							+ "WHERE a.idMatriculaDetalle.idMatricula.codigo = :cod AND (a.asistencia = 2 OR a.asistencia = 1)"
							+ "GROUP BY a.idMatriculaDetalle.idEstudiante.apellido, a.idMatriculaDetalle.idEstudiante.nombre, a.idMatriculaDetalle.idEstudiante.idEstudiante" , Object.class);
			consulta.setParameter("cod", idMatricula);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

}
