package com.tuvn.webNA.views.admin;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tuvn.webNA.controllers.CarreraController;
import com.tuvn.webNA.controllers.EstudianteController;
import com.tuvn.webNA.controllers.impl.CarreraControllerImpl;
import com.tuvn.webNA.controllers.impl.EstudianteControllerImpl;
import com.tuvn.webNA.models.entities.Carrera;
import com.tuvn.webNA.models.entities.Estudiante;

@ManagedBean
@ViewScoped
public class EstudiantesView implements Serializable{

	private static final long serialVersionUID = 1L;

	private EstudianteController estudianteController;
	private CarreraController carreraController;
	
	private List<Estudiante> estudiantes;
	private List<Carrera> carreras;
	
	private Estudiante estudiante;
	
	private Integer idCarrera;
	private String fecha;
	
	public EstudiantesView() {
		
	}

	@PostConstruct
	public void init () {
		estudianteController = new EstudianteControllerImpl();
		carreraController = new CarreraControllerImpl();
		
		estudiante = new Estudiante();
		carreras = carreraController.obtenerListaCarrera();
		
		listarEstudiantes();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=1");
	}
	
	public void listarEstudiantes() {
		estudiantes =  estudianteController.obtenerListaEstudiantes();
	}
	
	public void limpiarObjeto() {
		estudiante = new Estudiante();
		fecha = "";
		idCarrera = 0;
	}
	
	public void eliminarEstudiante(Integer i) {
		try {
			
			estudiante = estudiantes.get(i);
			estudiante.setEstado(0);
			estudianteController.actualizarEstudianmte(estudiante);
			
			limpiarObjeto();
			reload();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha Eliminado", "No se ha Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void seleccionarEstudiante(Integer i) {
		estudiante = estudiantes.get(i);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaComoCadena = sdf.format(new Date());
		fecha = fechaComoCadena;
		
		idCarrera = estudiante.getIdCarrera().getIdCarrera();
	}
	
	public void guardarEstudiante() {
		try {
			Carrera car = carreraController.buscarPorId(idCarrera); 			
			estudiante.setIdCarrera(car);
			
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaDate = null;
			fechaDate = formato.parse(fecha);
			estudiante.setFechaNacimiento(fechaDate);
			
			if(estudiante.getIdEstudiante() != null) {
				estudianteController.actualizarEstudianmte(estudiante);
			}else {
				Boolean validar = estudianteController.crearEstudiante(estudiante);
				if(validar == false) {
					throw new Exception();
				}
			}
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el dato C.I Duplicada", "No se guardo el dato C.I Duplicada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	//-----------------------------------------------------------------------------------------------

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Integer getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}
	
	
	
	
}
