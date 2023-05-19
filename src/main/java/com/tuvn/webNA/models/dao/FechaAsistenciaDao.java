package com.tuvn.webNA.models.dao;

import java.util.Date;
import java.util.List;

import com.tuvn.webNA.models.entities.FechaAsistencia;

public interface FechaAsistenciaDao {

	public List<FechaAsistencia> obtenerListaFechaAsistencia();
	
	public FechaAsistencia crearFechaAsistencia(FechaAsistencia fechaAsistencia);
	
	public void actualizarFechaAsistencia(FechaAsistencia fechaAsistencia);
	
	public FechaAsistencia buscarPorId(Integer id);
	
	public FechaAsistencia validarFecha(Date fecha);
	
}
