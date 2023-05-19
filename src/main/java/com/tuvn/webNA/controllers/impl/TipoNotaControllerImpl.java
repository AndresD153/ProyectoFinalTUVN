package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.TipoNotaController;
import com.tuvn.webNA.models.dao.TipoNotaDao;
import com.tuvn.webNA.models.dao.impl.TipoNotaDaoImpl;
import com.tuvn.webNA.models.entities.TipoNota;

public class TipoNotaControllerImpl implements TipoNotaController{

	private TipoNotaDao tipoNotaDao;
	@Override
	public List<TipoNota> obtenerListaTipoNota() {
		tipoNotaDao = new TipoNotaDaoImpl();
		return tipoNotaDao.obtenerListaTipoNota();
	}

	@Override
	public void crearTipoNota(TipoNota tipoNota) {
		tipoNotaDao = new TipoNotaDaoImpl();
		tipoNotaDao.crearTipoNota(tipoNota);
	}

	@Override
	public void actualizarTipoNota(TipoNota tipoNota) {
		tipoNotaDao = new TipoNotaDaoImpl();
		tipoNotaDao.actualizarTipoNota(tipoNota);
	}

	@Override
	public TipoNota buscarPorId(Integer id) {
		tipoNotaDao = new TipoNotaDaoImpl();
		return tipoNotaDao.buscarPorId(id);
	}

}
