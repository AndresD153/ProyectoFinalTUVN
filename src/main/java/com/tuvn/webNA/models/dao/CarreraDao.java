package com.tuvn.webNA.models.dao;

import java.util.List;

import com.tuvn.webNA.models.entities.Carrera;

public interface CarreraDao {

	public List<Carrera> obtenerListaCarrera();
	
	public void crearCarrera(Carrera carrera);
	
	public void actualizarCarrera(Carrera carrera);
	
	public Carrera buscarPorId(Integer id);
}
