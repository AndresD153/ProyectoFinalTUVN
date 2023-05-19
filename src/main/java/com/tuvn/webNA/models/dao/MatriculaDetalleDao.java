package com.tuvn.webNA.models.dao;

import java.util.List;

import com.tuvn.webNA.models.entities.MatriculaDetalle;

public interface MatriculaDetalleDao {

	public List<MatriculaDetalle> obtenerListaMatriculaDetalle(Integer idMatricula);
	
	public void crearMatriculaDetalle(MatriculaDetalle matriculaDetalle);
	
	public void actualizarMatriculaDetalle(MatriculaDetalle matriculaDetalle);
	
	public MatriculaDetalle verificarMatricula(Integer id, Integer idMatricula);
	
	public MatriculaDetalle buscarPorId(Integer id);
	
	public void eliminarMatriculaDetalle(MatriculaDetalle mat);
	
}
