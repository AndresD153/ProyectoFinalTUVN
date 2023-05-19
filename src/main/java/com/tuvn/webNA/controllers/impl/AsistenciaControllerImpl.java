package com.tuvn.webNA.controllers.impl;

import java.util.Date;
import java.util.List;

import com.tuvn.webNA.controllers.AsistenciaController;
import com.tuvn.webNA.models.dao.AsistenciaDao;
import com.tuvn.webNA.models.dao.impl.AsistenciaDaoImpl;
import com.tuvn.webNA.models.entities.Asistencia;

public class AsistenciaControllerImpl implements AsistenciaController{

	private AsistenciaDao asistenciaDao;
	@Override
	public List<Asistencia> obtenerListaAsistencia() {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.obtenerListaAsistencia();
	}

	@Override
	public void crearAsistencia(Asistencia asistencia) {
		asistenciaDao = new AsistenciaDaoImpl();
		asistenciaDao.crearAsistencia(asistencia);
	}

	@Override
	public void actualizarAsistencia(Asistencia asistencia) {
		asistenciaDao = new AsistenciaDaoImpl();
		asistenciaDao.actualizarAsistencia(asistencia);
	}

	@Override
	public Asistencia buscarPorId(Integer id) {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.buscarPorId(id);
	}

	@Override
	public List<Asistencia> obtenerListaAsistenciasDia(String idMatricula) {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.obtenerListaAsistenciasDia(idMatricula);
	}

	@Override
	public Asistencia buscarPrimeraAsistencia(Date fecha, String idMatricula) {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.buscarPrimeraAsistencia(fecha,idMatricula);
	}

	@Override
	public List<Asistencia> obtenerListaAsistenciaEstudiante(Integer fecha, String idMatricula) {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.obtenerListaAsistenciaEstudiante(fecha,idMatricula);
	}

	@Override
	public List<Object> obtenerReporteAsistencia(String idMatricula) {
		asistenciaDao = new AsistenciaDaoImpl();
		return asistenciaDao.obtenerReporteAsistencia(idMatricula);
	}

}
