package com.tuvn.webNA.controllers;

import java.util.List;

import com.tuvn.webNA.models.entities.Profesor;

public interface ProfesorController {

	public List<Profesor> obtenerListaProfesor();
	
	public boolean crearProfesor(Profesor profesor);
	
	public void actualizarProfesor(Profesor profesor);
	
	public Profesor buscarPorId(Integer id);
	
	public Profesor iniciarProfesor(String usuario, String password);
}
