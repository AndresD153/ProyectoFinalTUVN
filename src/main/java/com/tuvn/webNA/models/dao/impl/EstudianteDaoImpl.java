package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.EstudianteDao;
import com.tuvn.webNA.models.entities.Estudiante;

public class EstudianteDaoImpl extends GenericaDaoImpl<Estudiante> implements EstudianteDao{

	@Override
	public List<Estudiante> obtenerListaEstudiantes() {
		try {
			TypedQuery<Estudiante> consulta = this.entityManager
					.createQuery("SELECT e FROM Estudiante e WHERE e.estado = 1 ORDER BY e.idEstudiante DESC", Estudiante.class);
			return consulta.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearEstudiante(Estudiante estudiante) {
		try {
			this.beginTransaction();
			this.create(estudiante);
			this.commit();
		} catch (Exception e) {
			this.rollback();
		}
		
	}

	@Override
	public void actualizarEstudianmte(Estudiante estudiante) {
		try {
			this.beginTransaction();
			this.update(estudiante);
			this.commit();
		} catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public Estudiante buscarPorId(Integer id) {
		try {
			TypedQuery<Estudiante> consulta = this.entityManager
					.createQuery("SELECT e FROM Estudiante e WHERE e.idEstudiante = :id", Estudiante.class);
					consulta.setParameter("id", id);
			return consulta.getSingleResult();
		} catch (Exception e) {
			//---ver el error
			System.out.println(e);
			return new Estudiante();
		}
	}

	@Override
	public List<Estudiante> obtenerEstudiantesCarrera(Integer idCarrera) {
		try {
			TypedQuery<Estudiante> consulta = this.entityManager
					.createQuery("SELECT e FROM Estudiante e WHERE e.idCarrera.idCarrera = :id ORDER BY e.apellido ASC", Estudiante.class);
			consulta.setParameter("id", idCarrera);
			return consulta.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

}
