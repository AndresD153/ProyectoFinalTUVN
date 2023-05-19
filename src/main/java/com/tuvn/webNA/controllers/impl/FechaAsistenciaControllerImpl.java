package com.tuvn.webNA.controllers.impl;

import java.util.Date;
import java.util.List;

import com.tuvn.webNA.controllers.FechaAsistenciaController;
import com.tuvn.webNA.models.dao.FechaAsistenciaDao;
import com.tuvn.webNA.models.dao.impl.FechaAsistenciaDaoImpl;
import com.tuvn.webNA.models.entities.FechaAsistencia;

public class FechaAsistenciaControllerImpl implements FechaAsistenciaController{

	private FechaAsistenciaDao fechaAsistenciaDao;
	
	@Override
	public List<FechaAsistencia> obtenerListaFechaAsistencia() {
		fechaAsistenciaDao = new FechaAsistenciaDaoImpl();
		return fechaAsistenciaDao.obtenerListaFechaAsistencia();
	}

	@Override
	public FechaAsistencia crearFechaAsistencia(FechaAsistencia fechaAsistencia) {
		fechaAsistenciaDao = new FechaAsistenciaDaoImpl();
		return fechaAsistenciaDao.crearFechaAsistencia(fechaAsistencia);
	}

	@Override
	public void actualizarFechaAsistencia(FechaAsistencia fechaAsistencia) {
		fechaAsistenciaDao = new FechaAsistenciaDaoImpl();
		fechaAsistenciaDao.actualizarFechaAsistencia(fechaAsistencia);
	}

	@Override
	public FechaAsistencia buscarPorId(Integer id) {
		fechaAsistenciaDao = new FechaAsistenciaDaoImpl();
		return fechaAsistenciaDao.buscarPorId(id);
	}

	@Override
	public FechaAsistencia validarFecha(Date fecha) {
		fechaAsistenciaDao = new FechaAsistenciaDaoImpl();
		return fechaAsistenciaDao.validarFecha(fecha);
	}
	
}