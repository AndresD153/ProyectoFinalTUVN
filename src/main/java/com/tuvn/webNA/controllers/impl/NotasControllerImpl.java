package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.NotasController;
import com.tuvn.webNA.models.dao.NotasDao;
import com.tuvn.webNA.models.dao.impl.NotasDaoImpl;
import com.tuvn.webNA.models.entities.Notas;

public class NotasControllerImpl implements NotasController{

	private NotasDao notasDao;
	
	@Override
	public List<Notas> obtenerListaNotas(String idMatricula, Integer idTipo) {
		notasDao = new NotasDaoImpl();
		return notasDao.obtenerListaNotas(idMatricula,idTipo);
	}

	@Override
	public void crearNotas(Notas notas) {
		notasDao = new NotasDaoImpl();
		notasDao.crearNotas(notas);
	}

	@Override
	public void actualizarNotas(Notas notas) {
		notasDao = new NotasDaoImpl();
		notasDao.actualizarNotas(notas);
	}

	@Override
	public Notas buscarPorId(Integer id) {
		notasDao = new NotasDaoImpl();
		return notasDao.buscarPorId(id);
	}

	@Override
	public List<Notas> buscarListadoTipoNotas(String idMatricula) {
		notasDao = new NotasDaoImpl();
		return notasDao.buscarListadoTipoNotas(idMatricula);
	}

	@Override
	public Notas verificarTipoNota(Integer idMatricula, Integer idTipoNota) {
		notasDao = new NotasDaoImpl();
		return notasDao.verificarTipoNota(idMatricula, idTipoNota);
	}

	@Override
	public List<Notas> listaNotasEstudiante(Integer idEstudiante) {
		notasDao = new NotasDaoImpl();
		return notasDao.listaNotasEstudiante(idEstudiante);
	}

}
