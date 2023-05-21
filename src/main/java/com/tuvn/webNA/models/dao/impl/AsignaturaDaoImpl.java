package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.AsignaturaDao;
import com.tuvn.webNA.models.entities.Asignatura;

//Implementacion de la interfaz AsignaturaDao que hereda la inplementacion de la interfaz GenericaDao

public class AsignaturaDaoImpl extends GenericaDaoImpl<Asignatura> implements AsignaturaDao{

	@Override
	public List<Asignatura> obtenerListaAsignatura() {
		//Definicion de una consulta par obtener la lista de las asignaturas registradas y activas
		//retorna una lista con la informacion deseada en caso de que la consulta sea exitosa, caso contrario retorna una lista vacia
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
		//definicion de la consulta para ingresar nuevos registros
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
		////definicion de la consulta para actualizar registros existentes
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
		//Definicion de la consulta para realizar una busqueda unica a travez de un parametro
		//retorna un objeto con la informacion deseada en caso de que la consulta sea exitosa, caso contrario retorna un objeto vacio
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
