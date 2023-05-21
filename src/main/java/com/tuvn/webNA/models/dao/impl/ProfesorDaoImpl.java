package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.ProfesorDao;
import com.tuvn.webNA.models.entities.Profesor;

public class ProfesorDaoImpl extends GenericaDaoImpl<Profesor> implements ProfesorDao{

	@Override
	public List<Profesor> obtenerListaProfesor() {
		try{
			TypedQuery<Profesor> consulta = this.entityManager.
					createQuery("SELECT p FROM Profesor p WHERE p.estado = 1 ORDER BY p.idProfesor DESC", Profesor.class);
			return consulta.getResultList();
		}catch (Exception e) {
			//---ver el error
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public boolean crearProfesor(Profesor profesor) {
		try {
			this.beginTransaction();
			this.create(profesor);
			this.commit();
			return true;
		}catch (Exception e) {
			this.rollback();
			return false;
		}
		
	}

	@Override
	public void actualizarProfesor(Profesor profesor) {
		try {
			this.beginTransaction();
			this.update(profesor);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
		
	}

	@Override
	public Profesor buscarPorId(Integer id) {
		try {
			TypedQuery<Profesor> consulta = this.entityManager
					.createQuery("SELECT p FROM Profesor p WHERE p.idProfesor = :id", Profesor.class);
					consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			//---ver el error
			System.out.println(e);
			return new Profesor();
		}
	}

	@Override
	public Profesor iniciarProfesor(String usuario, String password) {
		try {
			TypedQuery<Profesor> consulta = this.entityManager
					.createQuery("SELECT p FROM Profesor p WHERE p.usuario = :user AND p.password = :pwd", Profesor.class);
					consulta.setParameter("user", usuario);
					consulta.setParameter("pwd", password);
			return consulta.getSingleResult();
		}catch (Exception e) {
			//---ver el error
			System.out.println(e);
			return new Profesor();
		}
	}
	
}
