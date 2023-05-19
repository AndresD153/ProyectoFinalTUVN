package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.MatriculaDetalleDao;
import com.tuvn.webNA.models.entities.MatriculaDetalle;

public class MatriculaDetalleDaoImpl extends GenericaDaoImpl<MatriculaDetalle> implements MatriculaDetalleDao{

	@Override
	public List<MatriculaDetalle> obtenerListaMatriculaDetalle(Integer idMatricula) {
		try {
			TypedQuery<MatriculaDetalle> consulta = this.entityManager.
					createQuery("SELECT md FROM MatriculaDetalle md WHERE md.idMatricula.idMatricula =:id AND md.matrEstado = 1 ORDER BY md.idEstudiante.apellido ASC",MatriculaDetalle.class);
			consulta.setParameter("id", idMatricula);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<MatriculaDetalle>();
		}
	}

	@Override
	public void crearMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
		try {
			this.beginTransaction();
			this.create(matriculaDetalle);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
		try {
			this.beginTransaction();
			this.update(matriculaDetalle);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public MatriculaDetalle buscarPorId(Integer id) {
		try {
			TypedQuery<MatriculaDetalle> consulta = this.entityManager
					.createQuery("SELECT md FROM MatriculaDetalle md WHERE md.idMatriculaDetalle = :id",MatriculaDetalle.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new MatriculaDetalle();
		}
	}

	@Override
	public MatriculaDetalle verificarMatricula(Integer id, Integer idMatricula) {
		try {
			TypedQuery<MatriculaDetalle> consulta = this.entityManager
					.createQuery("SELECT md FROM MatriculaDetalle md WHERE md.idEstudiante.idEstudiante = :idEst AND md.idMatricula.idMatricula =:idMat AND md.matrEstado = 1",MatriculaDetalle.class);
			consulta.setParameter("idEst", id);
			consulta.setParameter("idMat", idMatricula);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new MatriculaDetalle();
		}
	}

	@Override
	public void eliminarMatriculaDetalle(MatriculaDetalle mat) {
		try {
			this.beginTransaction();
			this.update(mat);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
		
	}


}
