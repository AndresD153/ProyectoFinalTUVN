package com.tuvn.webNA.controllers;

import java.util.List;

import com.tuvn.webNA.models.entities.Asignatura;
//Definicion de los metodos de la intefaz AsignaturaController
public interface AsignaturaController {
	
	public List<Asignatura> obtenerListaAsignatura();
	
	public void crearAsignatura(Asignatura asignatura);
	
	public void actualizarAsignatura(Asignatura asignatura);
	
	public Asignatura busacrPorId(Integer id);
}
