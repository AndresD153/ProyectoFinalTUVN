package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.models.dao.ProfesorDao;
import com.tuvn.webNA.models.dao.impl.ProfesorDaoImpl;
import com.tuvn.webNA.models.entities.Profesor;

public class ProfesorControllerImpl implements ProfesorController{
	
	private ProfesorDao profesorDao;

	@Override
	public List<Profesor> obtenerListaProfesor() {
		profesorDao = new ProfesorDaoImpl();
		return profesorDao.obtenerListaProfesor();
	}

	@Override
	public boolean crearProfesor(Profesor profesor) {
		profesorDao = new ProfesorDaoImpl();
		return profesorDao.crearProfesor(profesor);
			
	}

	@Override
	public void actualizarProfesor(Profesor profesor) {
		profesorDao = new ProfesorDaoImpl();
		profesorDao.actualizarProfesor(profesor);
	}

	@Override
	public Profesor buscarPorId(Integer id) {
		profesorDao = new ProfesorDaoImpl();
		return profesorDao.buscarPorId(id);
	}

	@Override
	public Profesor iniciarProfesor(String usuario, String password) {
		profesorDao = new ProfesorDaoImpl();
		return profesorDao.iniciarProfesor(usuario, password);
	}
	
}
