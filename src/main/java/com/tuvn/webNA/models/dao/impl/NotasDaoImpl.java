package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.NotasDao;
import com.tuvn.webNA.models.entities.Notas;

public class NotasDaoImpl extends GenericaDaoImpl<Notas> implements NotasDao{

	@Override
	public List<Notas> obtenerListaNotas(String idMatricula, Integer idTipo) {
		try {
		TypedQuery<Notas> consulta = this.entityManager
				.createQuery("SELECT n FROM Notas n WHERE n.idMatriculaDetalle.idMatricula.codigo = :idMat AND n.idTipoNota.idTipoNota = :idTip AND n.idMatriculaDetalle.matrEstado = 1 "
						+ "ORDER BY n.idMatriculaDetalle.idEstudiante.apellido ASC",Notas.class);
			consulta.setParameter("idMat", idMatricula);
			consulta.setParameter("idTip", idTipo);
		return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearNotas(Notas notas) {
		try {
			this.beginTransaction();
			this.create(notas);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarNotas(Notas notas) {
		try {
			this.beginTransaction();
			this.update(notas);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public Notas buscarPorId(Integer id) {
		try {
			TypedQuery<Notas> consulta = this.entityManager
					.createQuery("SELECT n FROM Notas n WHERE n.idNotas = :id",Notas.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Notas();
		}
	}
	
	@Override
	public List<Notas> buscarListadoTipoNotas(String idMatricula) {
		try {
			TypedQuery<Notas> consulta = this.entityManager
					.createQuery("SELECT n.idMatriculaDetalle.idMatricula.codigo, n.idTipoNota.idTipoNota, n.idTipoNota.tipoNombre, COUNT(n.idTipoNota.idTipoNota) FROM Notas n "
							+ "WHERE n.idMatriculaDetalle.idMatricula.codigo = :id AND n.idMatriculaDetalle.matrEstado = 1 "
							+ "GROUP BY n.idMatriculaDetalle.idMatricula.codigo, n.idTipoNota.idTipoNota, n.idTipoNota.tipoNombre",Notas.class);
			consulta.setParameter("id", idMatricula);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<Notas>();
		}
	}
	
	@Override
	public Notas verificarTipoNota(Integer idMatricula, Integer idTipoNota) {
		try {
			TypedQuery<Notas> consulta = this.entityManager
					.createQuery("SELECT n FROM Notas n WHERE n.idTipoNota.idTipoNota = :idTip AND n.idMatriculaDetalle.idMatricula.idMatricula = :idMat AND n.idMatriculaDetalle.matrEstado = 1 ",Notas.class);
			consulta.setParameter("idMat", idMatricula);
			consulta.setParameter("idTip", idTipoNota);
			consulta.setMaxResults(1);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println(e);
			return new Notas();
		}
	}

	@Override
	public List<Notas> listaNotasEstudiante(Integer idEstudiante) {
		try {
			TypedQuery<Notas> consulta = this.entityManager
					.createQuery("SELECT n FROM Notas n "
							+ "WHERE n.idMatriculaDetalle.idMatriculaDetalle = :idEst "
							+ "ORDER BY n.idTipoNota.idTipoNota ASC",Notas.class);
			consulta.setParameter("idEst", idEstudiante);
			return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<Notas>();
		}
	}
}
