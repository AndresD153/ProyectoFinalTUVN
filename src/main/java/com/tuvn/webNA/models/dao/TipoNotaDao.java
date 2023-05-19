package com.tuvn.webNA.models.dao;

import java.util.List;

import com.tuvn.webNA.models.entities.TipoNota;

public interface TipoNotaDao {
	
	public List<TipoNota> obtenerListaTipoNota();
	
	public void crearTipoNota(TipoNota tipoNota);
	
	public void actualizarTipoNota(TipoNota tipoNota);
	
	public TipoNota buscarPorId(Integer id);
}
