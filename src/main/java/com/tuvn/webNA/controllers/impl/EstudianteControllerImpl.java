package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.EstudianteController;
import com.tuvn.webNA.models.dao.EstudianteDao;
import com.tuvn.webNA.models.dao.impl.EstudianteDaoImpl;
import com.tuvn.webNA.models.entities.Estudiante;

public class EstudianteControllerImpl implements EstudianteController{
	
	private EstudianteDao estudianteDao;

	@Override
	public List<Estudiante> obtenerListaEstudiantes() {
		estudianteDao = new EstudianteDaoImpl();
		return estudianteDao.obtenerListaEstudiantes();
	}

	@Override
	public boolean crearEstudiante(Estudiante estudiante) {
		estudianteDao = new EstudianteDaoImpl();
		return estudianteDao.crearEstudiante(estudiante);
		
	}

	@Override
	public void actualizarEstudianmte(Estudiante estudiante) {
		estudianteDao = new EstudianteDaoImpl();
		estudianteDao.actualizarEstudianmte(estudiante);	
	}

	@Override
	public Estudiante buscarPorId(Integer id) {
		estudianteDao = new EstudianteDaoImpl();
		return estudianteDao.buscarPorId(id);
	}

	@Override
	public List<Estudiante> obtenerEstudiantesCarrera(Integer idCarrera) {
		estudianteDao = new EstudianteDaoImpl();
		return estudianteDao.obtenerEstudiantesCarrera(idCarrera);
	}

}
