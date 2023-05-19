package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.CarreraDao;
import com.tuvn.webNA.models.entities.Carrera;

public class CarreraDaoImpl extends GenericaDaoImpl<Carrera> implements CarreraDao{

	@Override
	public List<Carrera> obtenerListaCarrera() {
		try {
			TypedQuery<Carrera> consulta = this.entityManager
					.createQuery("SELECT c FROM Carrera c WHERE c.estado = 1 ORDER BY c.idCarrera DESC",Carrera.class);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearCarrera(Carrera carrera) {
		try {
		this.beginTransaction();
		this.create(carrera);
		this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarCarrera(Carrera carrera) {
		try {
			this.beginTransaction();
			this.update(carrera);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public Carrera buscarPorId(Integer id) {
		try {TypedQuery<Carrera> consulta = this.entityManager
				.createQuery("SELECT c FROM Carrera c WHERE c.idCarrera  = :id", Carrera.class);
		consulta.setParameter("id", id);
		return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Carrera();
		}
	}

	
}
