package com.tuvn.webNA.controllers;

import java.util.List;

import com.tuvn.webNA.models.entities.Carrera;

public interface CarreraController {
	
	public List<Carrera> obtenerListaCarrera();
	
	public void crearCarrera(Carrera carrera);
	
	public void actualizarCarrera(Carrera carrera);
	
	public Carrera buscarPorId(Integer id);
}