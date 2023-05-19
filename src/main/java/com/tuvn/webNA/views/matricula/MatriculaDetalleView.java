package com.tuvn.webNA.views.matricula;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tuvn.webNA.controllers.CarreraController;
import com.tuvn.webNA.controllers.EstudianteController;
import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.impl.CarreraControllerImpl;
import com.tuvn.webNA.controllers.impl.EstudianteControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.models.entities.Carrera;
import com.tuvn.webNA.models.entities.Estudiante;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;

@ManagedBean
@ViewScoped
public class MatriculaDetalleView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MatriculaController matriculaController;
	private CarreraController carreraController;
	private EstudianteController estudianteController;
	private MatriculaDetalleController matriculaDetalleController;
	
	private List<Carrera> carreras;
	private List<Estudiante> estudiantesFuente;
	private List<Estudiante> estudianteObjetivo;
	private List<Estudiante> listaTemp;


	private Matricula matricula;
	private Estudiante estudiante;
	private MatriculaDetalle matriculaDetalle;
	
	private String idMatricula;
	private Integer idCarrera;

	public MatriculaDetalleView() {
		
	}
	
	@PostConstruct
	public void init() {
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		carreraController = new CarreraControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		estudianteController = new EstudianteControllerImpl();
		estudianteObjetivo = new ArrayList<Estudiante>();
		listaTemp = new ArrayList<Estudiante>();
		
		idMatricula = getId();
		
		obtenerMatricula();
		obtenerCarreras();
		cargarEstudiantesMatriculados();		
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private void obtenerMatricula() {
		matricula = matriculaController.buscarPorId(idMatricula);
	}
	
	private void obtenerCarreras() {
		carreras = carreraController.obtenerListaCarrera();
	}
	
	public void seleccionarCarrera() {
		estudiantesFuente = estudianteController.obtenerEstudiantesCarrera(idCarrera);
	}
	
	public void agregarEstudiante() {
		
		matriculaDetalle = matriculaDetalleController.verificarMatricula(estudiante.getIdEstudiante(), matricula.getIdMatricula());

		if(matriculaDetalle.getIdMatriculaDetalle() == null) {
			estudianteObjetivo.add(estudiante);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, 
		     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Estudiante ya matriculado"));
		}
		
		estudiante = new Estudiante();
		matriculaDetalle = new  MatriculaDetalle();
		
	}
	
	public void removerEstudiante (Integer i) {
		estudiante = estudianteObjetivo.get(i);
		estudianteObjetivo.remove(estudiante);
		listaTemp.add(estudiante);
		estudiante = new Estudiante();
	}

	public void guardar() throws IOException{
		
		eliminarEstudianteMatricula();
		if(estudianteObjetivo.size() >= 5) {
			
			matriculaDetalle = new MatriculaDetalle();
			
			estudianteObjetivo.forEach(item -> {
				matriculaDetalle = matriculaDetalleController.verificarMatricula(item.getIdEstudiante(), matricula.getIdMatricula());
				
				if(matriculaDetalle.getIdMatriculaDetalle() == null) {
					matriculaDetalle.setIdEstudiante(item);
					matriculaDetalle.setIdMatricula(matricula);
					matriculaDetalleController.crearMatriculaDetalle(matriculaDetalle);					
				}else {
					System.out.println("No se pudo ingresar id = "+ item.getIdEstudiante());
				}
				
				
			});
			reload();
		}else {
			FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Se requiere minimo de 5 estudiantes por materia"));
		}
		
	}
	
	public void eliminarEstudianteMatricula() {
		if(listaTemp.size() > 0 && estudianteObjetivo.size() >= 5) {
			for (int i = 0; i < listaTemp.size(); i++) {
				Estudiante item = listaTemp.get(i);
				matriculaDetalle = matriculaDetalleController.verificarMatricula(item.getIdEstudiante(), matricula.getIdMatricula());
				if(matriculaDetalle.getIdMatriculaDetalle() != null) {
					matriculaDetalle.setMatrEstado(0);
					matriculaDetalleController.eliminarMatriculaDetalle(matriculaDetalle);
				}
			};
		}
	}
	
	private void cargarEstudiantesMatriculados() {
		List<MatriculaDetalle> estudiantesMatriculados;
		estudiantesMatriculados = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
		
		
		if(estudiantesMatriculados.size() > 0) {
			Integer size = estudiantesMatriculados.size();

			for (int i = 0; i < size; i++) {
				estudiante = estudiantesMatriculados.get(i).getIdEstudiante();
				estudianteObjetivo.add(estudiante);

			}
			
			estudiante = new Estudiante();
		}
	}
	
	public void reload() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=0&matricula="+matricula.getCodigo());

	}
	
	//----
	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public List<Estudiante> getEstudiantesFuente() {
		return estudiantesFuente;
	}

	public void setEstudiantesFuente(List<Estudiante> estudiantesFuente) {
		this.estudiantesFuente = estudiantesFuente;
	}

	public Integer getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	
	public List<Estudiante> getEstudianteObjetivo() {
		return estudianteObjetivo;
	}

	public void setEstudianteObjetivo(List<Estudiante> estudianteObjetivo) {
		this.estudianteObjetivo = estudianteObjetivo;
	}
	
	
}
