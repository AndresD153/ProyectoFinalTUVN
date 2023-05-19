package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.MatriculaDao;
import com.tuvn.webNA.models.entities.Matricula;

public class MatriculaDaoImpl extends GenericaDaoImpl<Matricula> implements MatriculaDao{

	@Override
	public List<Matricula> obtenerListaMatricula(Integer id) {
		try {
			TypedQuery<Matricula> consulta = this.entityManager
					.createQuery("SELECT m FROM Matricula m WHERE m.idProfesor.idProfesor =:id ORDER BY m.idMatricula ASC", Matricula.class);
			consulta.setParameter("id", id);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearMatricula(Matricula matricula) {
		try {
			this.beginTransaction();
			this.create(matricula);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarMatricula(Matricula matricula) {
		try {
			this.beginTransaction();
			this.update(matricula);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public Matricula buscarPorId(String id) {
		try {
			TypedQuery<Matricula> consulta = this.entityManager
					.createQuery("SELECT m FROM Matricula m WHERE m.codigo = :id",Matricula.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Matricula();
		}
	}

}
