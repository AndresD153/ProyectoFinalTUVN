package com.tuvn.webNA.controllers;

import java.util.List;

import com.tuvn.webNA.models.entities.Notas;

public interface NotasController {

	public List<Notas> obtenerListaNotas(String idMatricula, Integer idTipo);
	
	public void crearNotas(Notas notas);
	
	public void actualizarNotas(Notas notas);
	
	public Notas buscarPorId(Integer id);
	
	public List<Notas> buscarListadoTipoNotas(String idMatricula);
	
	public Notas verificarTipoNota(Integer idMatricula, Integer idTipoNota);

	public List<Notas> listaNotasEstudiante(Integer idEstudiante);
}
