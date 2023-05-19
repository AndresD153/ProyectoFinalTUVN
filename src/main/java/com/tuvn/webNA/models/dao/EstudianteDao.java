package com.tuvn.webNA.models.dao;

import java.util.List;

import com.tuvn.webNA.models.entities.Estudiante;

public interface EstudianteDao {
	
	public List<Estudiante> obtenerListaEstudiantes();
	
	public List<Estudiante> obtenerEstudiantesCarrera(Integer idCarrera);
	
	public void crearEstudiante(Estudiante estudiante);
	
	public void actualizarEstudianmte(Estudiante estudiante);
	
	public Estudiante buscarPorId(Integer id);
	
}
