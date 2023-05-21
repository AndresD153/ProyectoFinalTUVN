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
		//inicializar los objetos necesarios
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
		//Obtener el IdMatricula desde el URL
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private Integer getIdFecha() {
		//Obtener la Fecha desde el URL
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

		//Concatenar la respuestas y mostrar en el fomato correcto
		String dateString = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

		return dateString;
	}
	
	private void listarFechaEstudiante() {
		//Obtener la lista de estudiantes con el id de la fecha y el IdMatricula
		asistencias = asistenciaController.obtenerListaAsistenciaEstudiante(idFecha, idMatricula);
	}
	
	//Metodo para editar celdas cuyo parametro extrae un evento llamado CellEditEvent 
	public void editarCelda(CellEditEvent<Object> event) {		
		//Extraer el valor antiguo 
		Object oldValue = event.getOldValue();
		//Extraer el valor modificado
        Object newValue = event.getNewValue();
        //Obtener el Index de la lista
        Integer i = event.getRowIndex();
        //Obtener el objeto Asitencia apartir del Index
        asistencia = asistencias.get(i);
        //Actualizar el objeto asistencia con el nuevo valor 
        asistencia.setAsistencia(Integer.parseInt(newValue.toString()));
        
        //Validar que el nuevo valor no sea nulo o sea igual al anterior
        if (newValue != null && !newValue.equals(oldValue)) {
        	//Realizar la accion de actualizar la asitencia
        	asistenciaController.actualizarAsistencia(asistencia);

        	//Mandar un mensaje o notificaion de la asitencia
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Asistencia Actualizada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}
	
	public String mostrarDetalle(Integer est) {
		//Mostrar el detalle de la asistencia validado atravez de un numero
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
	
	//Metodo para Insertar una nueva asistencia en caso de no haberse matriculado previamente 
	public void crearAsistenciaEstudiante() throws IOException {
		//Filtrar la lista asitencias para mostrar unicamente el primer valor
		Asistencia a = asistencias.stream().filter(item -> item.getIdMatriculaDetalle().getIdMatriculaDetalle().equals(idMatriculaDetalle))
				.limit(1)
				.findFirst().orElse(null);
		
		//Validar si el objeto A "asitencia" es nullo es decir esta asitencia no a sido generada aun
		if(a!=null) {
			//Si es nullo mostrar una notificacion 
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Asistencia ya registrada", "Porque ya existe");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			//Buscar la fechaAsitencia 
			FechaAsistencia fa = fechaAsistenciaController.buscarPorId(idFecha);
			//Obtener la matricula detalle del estudiante matriculado recientemente
			MatriculaDetalle md = estudiantes.stream().filter(item -> item.getIdMatriculaDetalle().equals(idMatriculaDetalle))
					.limit(1)
					.findFirst().orElse(null);
			//Inicialisar un nuevo objeto asistencia
			Asistencia asi = new Asistencia();
			//Actualizar con los nuevos datos extraidos
			asi.setIdMatriculaDetalle(md);
			asi.setAsistencia(2);
			asi.setIdFechaAsistencia(fa);
			
			//Llamar a la accion crearAsitencia 
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
