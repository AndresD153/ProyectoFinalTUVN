package com.tuvn.webNA.views.asistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.CellEditEvent;

import com.tuvn.webNA.controllers.AsistenciaController;
import com.tuvn.webNA.controllers.FechaAsistenciaController;
import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.impl.AsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.FechaAsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.models.entities.Asistencia;
import com.tuvn.webNA.models.entities.FechaAsistencia;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;

@ManagedBean
@ViewScoped
public class EditarAsistenciaView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AsistenciaController asistenciaController;
	private FechaAsistenciaController fechaAsistenciaController;
	private MatriculaDetalleController matriculaDetalleController;
	private MatriculaController matriculaController;
	
	private List<Asistencia> asistencias;
	private List<MatriculaDetalle> estudiantes;
	
	private Asistencia asistencia;
	private FechaAsistencia fechaAsistencia;
	private Matricula matricula;
	
	private String idMatricula;
	private Integer idFecha;
	private Integer idMatriculaDetalle;

	public EditarAsistenciaView() {
		
	}
	
	@PostConstruct
	public void init () {
		asistenciaController = new AsistenciaControllerImpl();
		fechaAsistenciaController = new FechaAsistenciaControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		
		idMatricula = getId();
		idFecha = getIdFecha();
		fechaAsistencia = fechaAsistenciaController.buscarPorId(idFecha);
		
		listarFechaEstudiante();
		listarEstudiantes();
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private Integer getIdFecha() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		Integer getParam = Integer.parseInt(params.get("fecha"));
		return getParam;
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=2&matricula="+idMatricula+"&fecha="+idFecha);
	}
	
	public String convertirFecha(Date objetoFecha) {

		Calendar c = Calendar.getInstance();
		c.setTime(objetoFecha);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);

		String dateString = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

		return dateString;
	}
	
	private void listarFechaEstudiante() {
		asistencias = asistenciaController.obtenerListaAsistenciaEstudiante(idFecha, idMatricula);
	}
	
	public void editarCelda(CellEditEvent<Object> event) {		
		Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        Integer i = event.getRowIndex();
        
        asistencia = asistencias.get(i);
        
        asistencia.setAsistencia(Integer.parseInt(newValue.toString()));

        if (newValue != null && !newValue.equals(oldValue)) {
        	asistenciaController.actualizarAsistencia(asistencia);
            System.out.println(asistencia);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Asistencia Actualizada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}
	
	public String mostrarDetalle(Integer est) {
		if(est == 0) {
			return "Falta";
		}else if(est == 1) {
			return "Atraso";
		}else {
			return "Presente";
		}
	}
	
	public void listarEstudiantes() {
		matricula = matriculaController.buscarPorId(idMatricula);
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
	}
	
	public void crearAsistenciaEstudiante() throws IOException {
		Asistencia a = asistencias.stream().filter(item -> item.getIdMatriculaDetalle().getIdMatriculaDetalle().equals(idMatriculaDetalle))
				.limit(1)
				.findFirst().orElse(null);
		
		if(a!=null) {
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR,"Asistencia duplicada", "Nota ya asignada"));
		}else {
			FechaAsistencia fa = fechaAsistenciaController.buscarPorId(idFecha);
			MatriculaDetalle md = estudiantes.stream().filter(item -> item.getIdMatriculaDetalle().equals(idMatriculaDetalle))
					.limit(1)
					.findFirst().orElse(null);
			
			Asistencia asi = new Asistencia();
			asi.setIdMatriculaDetalle(md);
			asi.setAsistencia(2);
			asi.setIdFechaAsistencia(fa);
			
			asistenciaController.crearAsistencia(asi);
			
			reload();
		}
	}
	
	//--------------------------------------------------------------------------------------------
	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public FechaAsistencia getFechaAsistencia() {
		return fechaAsistencia;
	}

	public void setFechaAsistencia(FechaAsistencia fechaAsistencia) {
		this.fechaAsistencia = fechaAsistencia;
	}

	public List<MatriculaDetalle> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<MatriculaDetalle> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Integer getIdMatriculaDetalle() {
		return idMatriculaDetalle;
	}

	public void setIdMatriculaDetalle(Integer idMatriculaDetalle) {
		this.idMatriculaDetalle = idMatriculaDetalle;
	}
	
	
	

}
