package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.models.dao.MatriculaDao;
import com.tuvn.webNA.models.dao.impl.MatriculaDaoImpl;
import com.tuvn.webNA.models.entities.Matricula;

public class MatriculaControllerImpl implements MatriculaController{

	private MatriculaDao matriculaDao;
	
	@Override
	public List<Matricula> obtenerListaMatricula(Integer id) {
		matriculaDao = new MatriculaDaoImpl();
		return matriculaDao.obtenerListaMatricula(id);
	}

	@Override
	public void crearMatricula(Matricula matricula) {
		matriculaDao = new MatriculaDaoImpl();
		matriculaDao.crearMatricula(matricula);
	}

	@Override
	public void actualizarMatricula(Matricula matricula) {
		matriculaDao = new MatriculaDaoImpl();
		matriculaDao.actualizarMatricula(matricula);
	}

	@Override
	public Matricula buscarPorId(String id) {
		matriculaDao = new MatriculaDaoImpl();
		return matriculaDao.buscarPorId(id);
	}

}
