package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.AsignaturaController;
import com.tuvn.webNA.models.dao.AsignaturaDao;
import com.tuvn.webNA.models.dao.impl.AsignaturaDaoImpl;
import com.tuvn.webNA.models.entities.Asignatura;

//Definicion de los metodos de la Implementacion de la clase AsignaturaController
public class AsignaturaControllerImpl implements AsignaturaController{
	
	private AsignaturaDao asignaturaDao;

	@Override
	public List<Asignatura> obtenerListaAsignatura() {
		asignaturaDao = new AsignaturaDaoImpl();
		return asignaturaDao.obtenerListaAsignatura();
	}

	@Override
	public void crearAsignatura(Asignatura asignatura) {
		asignaturaDao = new AsignaturaDaoImpl();
		asignaturaDao.crearAsignatura(asignatura);
	}

	@Override
	public void actualizarAsignatura(Asignatura asignatura) {
		asignaturaDao = new AsignaturaDaoImpl();
		asignaturaDao.actualizarAsignatura(asignatura);
	}

	@Override
	public Asignatura busacrPorId(Integer id) {
		asignaturaDao = new AsignaturaDaoImpl();
		return asignaturaDao.busacrPorId(id);
	}
	
}
