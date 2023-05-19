package com.tuvn.webNA.controllers.impl;

import java.util.List;

import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.models.dao.MatriculaDetalleDao;
import com.tuvn.webNA.models.dao.impl.MatriculaDetalleDaoImpl;
import com.tuvn.webNA.models.entities.MatriculaDetalle;

public class MatriculaDetalleControllerImpl implements MatriculaDetalleController{

	private MatriculaDetalleDao matriculaDetalleDao;
	
	@Override
	public List<MatriculaDetalle> obtenerListaMatriculaDetalle(Integer idMatricula) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		return matriculaDetalleDao.obtenerListaMatriculaDetalle(idMatricula);
	}

	@Override
	public void crearMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		matriculaDetalleDao.crearMatriculaDetalle(matriculaDetalle);
	}

	@Override
	public void actualizarMatriculaDetalle(MatriculaDetalle matriculaDetalle) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		matriculaDetalleDao.actualizarMatriculaDetalle(matriculaDetalle);
	}

	@Override
	public MatriculaDetalle buscarPorId(Integer id) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		return matriculaDetalleDao.buscarPorId(null);
	}

	@Override
	public MatriculaDetalle verificarMatricula(Integer id, Integer idMatricula) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		return matriculaDetalleDao.verificarMatricula(id, idMatricula);
	}

	@Override
	public void eliminarMatriculaDetalle(MatriculaDetalle mat) {
		matriculaDetalleDao = new MatriculaDetalleDaoImpl();
		matriculaDetalleDao.eliminarMatriculaDetalle(mat);
	}

}
