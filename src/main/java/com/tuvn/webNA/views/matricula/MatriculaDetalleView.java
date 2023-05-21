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
	//Declarar el objeto estudianteFuente "Objeto del cual salen los datos de origen "
	private List<Estudiante> estudiantesFuente;
	//Declarar el objeto estudianteObjetivo "objeto en el cual se crearan datos de manera temporal y es modificable"
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
		//Inicialisar los objetos necesarios
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
		//Funcion para obtener el Id de la matricula
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

		//Validar que el objeto MatriculaDetalle contenga no datos "es decir que el estudiante no este matriculado"
		if(matriculaDetalle.getIdMatriculaDetalle() == null) {
			//Agregar el estudiante a la matricula
			estudianteObjetivo.add(estudiante);
		}else {
			//Caso contrario enviar una alerta
			FacesContext.getCurrentInstance().addMessage(null, 
		     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Estudiante ya matriculado"));
		}
		
		//Al finalizar la operacion inicialisar los objetos
		estudiante = new Estudiante();
		matriculaDetalle = new  MatriculaDetalle();
		
	}
	
	public void removerEstudiante (Integer i) {
		//Remover los estudianmtes de la lista estudianteObjetivo atravez del Index
		estudiante = estudianteObjetivo.get(i);
		estudianteObjetivo.remove(estudiante);
		listaTemp.add(estudiante);
		estudiante = new Estudiante();
	}

	public void guardar() throws IOException{
		
		eliminarEstudianteMatricula();
		//Validar que la listaObjetivo contenga 5 estudiantes para continuar 
		if(estudianteObjetivo.size() >= 5) {
			
			matriculaDetalle = new MatriculaDetalle();
			
			//Inicialisar un bucle con el numero de elementos disponibles en el array listaObjetivo			
			estudianteObjetivo.forEach(item -> {
				matriculaDetalle = matriculaDetalleController.verificarMatricula(item.getIdEstudiante(), matricula.getIdMatricula());
				
				//Validar que la matriculaDetalle aun no exista
				if(matriculaDetalle.getIdMatriculaDetalle() == null) {
					//Actualizar el objeto matriculaDetalle con la infromacion requerida
					matriculaDetalle.setIdEstudiante(item);
					matriculaDetalle.setIdMatricula(matricula);
					//Llamar a la accion crear
					matriculaDetalleController.crearMatriculaDetalle(matriculaDetalle);					
				}else {
					//Si existe se mandara una alerta
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
		//Funcion para eliminar a los estudiantes matriculados solo se cambia el estado y no se visualiza
		if(listaTemp.size() > 0 && estudianteObjetivo.size() >= 5) {
			//Eliminar a los estudiantes empleando un bucle
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
		//Llamar a los estudiantes matriculados
		estudiantesMatriculados = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
		//Validar que la lista de estudianmtes matriculados sea mayor que 0
		if(estudiantesMatriculados.size() > 0) {
			//Obtener el tamaño de la lista 0
			Integer size = estudiantesMatriculados.size();
			//Realizar un bucle para llenar la ListaObjetivo con los estudiantes matriculados
			for (int i = 0; i < size; i++) {
				estudiante = estudiantesMatriculados.get(i).getIdEstudiante();
				estudianteObjetivo.add(estudiante);

			}
			//Al finalizar inicialisar el objeto estudiante
			estudiante = new Estudiante();
		}
	}
	
	public void reload() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=0&matricula="+matricula.getCodigo());

	}
	
	//-----------------------------------------------------------------------
	//------------------Metodos GET y SET -----------------------------------
	//-----------------------------------------------------------------------
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
