package com.tuvn.webNA.controllers;

import java.util.List;

import com.tuvn.webNA.models.entities.Estudiante;

public interface EstudianteController {

	public List<Estudiante> obtenerListaEstudiantes();
	
	public List<Estudiante> obtenerEstudiantesCarrera(Integer idCarrera);
	
	public boolean crearEstudiante(Estudiante estudiante);
	
	public void actualizarEstudianmte(Estudiante estudiante);
	
	public Estudiante buscarPorId(Integer id);
}
