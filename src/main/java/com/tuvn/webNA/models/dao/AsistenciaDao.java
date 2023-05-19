package com.tuvn.webNA.models.dao;

import java.util.Date;
import java.util.List;

import com.tuvn.webNA.models.entities.Asistencia;

public interface AsistenciaDao {

	public List<Asistencia> obtenerListaAsistencia();
	
	public void crearAsistencia(Asistencia asistencia);
	
	public void actualizarAsistencia(Asistencia asistencia);
	
	public Asistencia buscarPorId(Integer id);
	
	public Asistencia buscarPrimeraAsistencia(Date fecha, String idMatricula);
	
	public List<Asistencia> obtenerListaAsistenciasDia(String idMatricula);
	
	public List<Asistencia> obtenerListaAsistenciaEstudiante(Integer fecha,String idMatricula);
	
	public List<Object> obtenerReporteAsistencia(String idMatricula);
}
