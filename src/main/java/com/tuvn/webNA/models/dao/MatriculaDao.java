package com.tuvn.webNA.models.dao;

import java.util.List;

import com.tuvn.webNA.models.entities.Matricula;

public interface MatriculaDao {

	public List<Matricula> obtenerListaMatricula(Integer id);
	
	public void crearMatricula(Matricula matricula);
	
	public void actualizarMatricula(Matricula matricula);
	
	public Matricula buscarPorId(String id);
	
}
