package com.tuvn.webNA.views.asistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
public class AsistenciaView implements Serializable{

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
	//private MatriculaDetalle matriculaDetalle;
	private String idMatricula;

	public AsistenciaView() {
		
	}

	@PostConstruct
	public void init() {
		asistenciaController = new AsistenciaControllerImpl();
		fechaAsistenciaController = new FechaAsistenciaControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		
		
		idMatricula = getId();
		matricula = matriculaController.buscarPorId(idMatricula);
		
		listarAsistenciasDia();
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	public String convertirFechaCompleta(Date objetoFecha) {

		ArrayList<String> dia = new ArrayList<>();
		
		dia.add("Domingo");
		dia.add("Lunes");
		dia.add("Martes");
		dia.add("Miercoles");
		dia.add("Jueves");
		dia.add("Viernes");
		dia.add("Sabado");
		
		
		Calendar c = Calendar.getInstance();
		c.setTime(objetoFecha);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int d = (c.get(Calendar.DAY_OF_WEEK))-1;

		String dateString = dia.get(d)+"  | "+String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

		return dateString;
	}

	private String convertirFecha(Date objetoFecha) {

		Calendar c = Calendar.getInstance();
		c.setTime(objetoFecha);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);

		String dateString = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

		return dateString;
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=2&matricula="+matricula.getCodigo());

	}
	
	public void listarAsistenciasDia() {
		asistencias = asistenciaController.obtenerListaAsistenciasDia(idMatricula);
	}
	
	public void generarAsistencia() throws IOException {
		fechaAsistencia = fechaAsistenciaController.validarFecha(new Date());
		
		String fActual = convertirFecha(new Date());
		String fAntigua = convertirFecha(fechaAsistencia.getFecha());
		
		asistencia = asistenciaController.buscarPrimeraAsistencia(new Date(), idMatricula);
		
		if(fechaAsistencia.getIdFechaAsistencia()==null) {
			
			fechaAsistencia =  fechaAsistenciaController.crearFechaAsistencia(fechaAsistencia);
			insertarAsistencia(fechaAsistencia);
			
		}else if(fActual.equals(fAntigua) && asistencia.getAsistencia()!=null){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha generado la asisitecia", "Porque ya existe");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else {
			insertarAsistencia(fechaAsistencia);
		}
				
		
	}
	
	private void insertarAsistencia(FechaAsistencia fecha) throws IOException {
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
		asistencia = new Asistencia();
		asistencia.setIdFechaAsistencia(fecha);
		
		for (Iterator<MatriculaDetalle> iterator = estudiantes.iterator(); iterator.hasNext();) {
			MatriculaDetalle md = (MatriculaDetalle) iterator.next();
			
			asistencia.setIdMatriculaDetalle(md);
			asistencia.setAsistencia(2);
			
			asistenciaController.crearAsistencia(asistencia);
		}

		reload();
	}
	
	//-------
	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	
	
}
