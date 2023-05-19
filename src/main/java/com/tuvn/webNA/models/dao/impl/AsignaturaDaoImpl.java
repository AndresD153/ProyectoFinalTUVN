package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.AsignaturaDao;
import com.tuvn.webNA.models.entities.Asignatura;

public class AsignaturaDaoImpl extends GenericaDaoImpl<Asignatura> implements AsignaturaDao{

	@Override
	public List<Asignatura> obtenerListaAsignatura() {
		try {
			TypedQuery<Asignatura> consulta = this.entityManager
					.createQuery("SELECT a FROM Asignatura a WHERE a.estado = 1 ORDER BY a.idAsignatura ASC", Asignatura.class);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearAsignatura(Asignatura asignatura) {
		try {
			this.beginTransaction();
			this.create(asignatura);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarAsignatura(Asignatura asignatura) {
		try {
			this.beginTransaction();
			this.update(asignatura);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
		
	}

	@Override
	public Asignatura busacrPorId(Integer id) {
		try {
			TypedQuery<Asignatura> consulta = this.entityManager
					.createQuery("SELECT a FROM Asignatura a WHERE a.idAsignatura = :id", Asignatura.class);
					consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Asignatura();
		}
	}

}
