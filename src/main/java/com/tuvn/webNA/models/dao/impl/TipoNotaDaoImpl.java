package com.tuvn.webNA.models.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.tuvn.webNA.models.dao.TipoNotaDao;
import com.tuvn.webNA.models.entities.TipoNota;

public class TipoNotaDaoImpl extends GenericaDaoImpl<TipoNota> implements TipoNotaDao{

	@Override
	public List<TipoNota> obtenerListaTipoNota() {
		try {
			TypedQuery<TipoNota> consulta = this.entityManager
				.createQuery("SELECT tn FROM TipoNota tn WHERE tn.tipoEstado = 1 ORDER BY tn.idTipoNota ASC", TipoNota.class);
		return consulta.getResultList();
		}catch (Exception e) {
			System.out.println(e);
			return new ArrayList<>();
		}
	}

	@Override
	public void crearTipoNota(TipoNota tipoNota) {
		try {
			this.beginTransaction();
			this.create(tipoNota);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public void actualizarTipoNota(TipoNota tipoNota) {
		try {
			this.beginTransaction();
			this.update(tipoNota);
			this.commit();
		}catch (Exception e) {
			this.rollback();
		}
	}

	@Override
	public TipoNota buscarPorId(Integer id) {
		try {
			TypedQuery<TipoNota> consulta = this.entityManager
					.createQuery("SELECT tn FROM TipoNota tn WHERE tn.idTipoNota = :id", TipoNota.class);
			consulta.setParameter("id", id);
			return consulta.getSingleResult();
		}catch (Exception e) {
			System.out.println();
			return new TipoNota();
		}
	}

}
