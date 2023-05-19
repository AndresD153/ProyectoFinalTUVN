package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.FechaAsistenciaDao;
import com.tuvn.webNA.models.entities.FechaAsistencia;

public class FechaAsistenciaDaoImpl extends GenericaDaoImpl<FechaAsistencia> implements FechaAsistenciaDao{

	@Override
	public List<FechaAsistencia> obtenerListaFechaAsistencia() {
		try {
			TypedQuery<FechaAsistencia> consulta = this.entityManager
					.createQuery("SELECT fa FROM FechaAsistencia fa ORDER BY fa.idFechaAsistencia ASC", FechaAsistencia.class);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public FechaAsistencia crearFechaAsistencia(FechaAsistencia fechaAsistencia) {
		try {
			this.beginTransaction();
			this.create(fechaAsistencia);
			this.commit();
			return fechaAsistencia;
		}catch (Exception e) {
			return new FechaAsistencia();
		}
	}

	@Override
	public void actualizarFechaAsistencia(FechaAsistencia fechaAsistencia) {
		try {
			this.beginTransaction();
			this.update(fechaAsistencia);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public FechaAsistencia buscarPorId(Integer id) {
		try {
			TypedQuery<FechaAsistencia> consulta = this.entityManager
					.createQuery("SELECT fa FROM FechaAsistencia fa WHERE fa.idFechaAsistencia = :id", FechaAsistencia.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new FechaAsistencia();
		}
	}
	
	@Override
	public FechaAsistencia validarFecha(Date fecha) {
		try {
			TypedQuery<FechaAsistencia> consulta = this.entityManager
					.createQuery("SELECT fa FROM FechaAsistencia fa WHERE fa.fecha = :fecha", FechaAsistencia.class);
			consulta.setParameter("fecha", fecha);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new FechaAsistencia();
		}
	}

}
