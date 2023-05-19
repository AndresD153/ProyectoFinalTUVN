package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.CarreraController;
import com.tuvn.webNA.models.dao.CarreraDao;
import com.tuvn.webNA.models.dao.impl.CarreraDaoImpl;
import com.tuvn.webNA.models.entities.Carrera;

public class CarreraControllerImpl implements CarreraController{

	private CarreraDao carreraDao;
	
	@Override
	public List<Carrera> obtenerListaCarrera() {
		carreraDao = new CarreraDaoImpl();
		return carreraDao.obtenerListaCarrera();
	}

	@Override
	public void crearCarrera(Carrera carrera) {
		carreraDao = new CarreraDaoImpl();
		carreraDao.crearCarrera(carrera);
	}

	@Override
	public void actualizarCarrera(Carrera carrera) {
		carreraDao = new CarreraDaoImpl();
		carreraDao.actualizarCarrera(carrera);
	}

	@Override
	public Carrera buscarPorId(Integer id) {
		carreraDao = new CarreraDaoImpl();
		return carreraDao.buscarPorId(id);
	}
	
	
}
