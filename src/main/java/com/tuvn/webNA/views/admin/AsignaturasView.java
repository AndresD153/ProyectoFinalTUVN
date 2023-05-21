package com.tuvn.webNA.views.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tuvn.webNA.controllers.AsignaturaController;
import com.tuvn.webNA.controllers.impl.AsignaturaControllerImpl;
import com.tuvn.webNA.models.entities.Asignatura;

@ManagedBean
@ViewScoped
public class AsignaturasView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Definicion de los objetos a utilizar dentro de la clase tales como listas, objetos simples y tipos de variables
	private AsignaturaController asignaturaController;
	
	private List<Asignatura> asignaturas;
	
	private Asignatura asignatura;

	public AsignaturasView() {
		
	}
	
	//--Metodo iniciado por defecto una vez la clase a sido inicializada 
	@PostConstruct
	public void unit () {
		//Inicializar la implementacion de la interfaz de asignaturaController 
		asignaturaController = new AsignaturaControllerImpl();
		
		//Iniziar el objeto asignatura 
		asignatura = new Asignatura();
		
		//LLamar al Metodo listaAsignaturas para llenar la lista asignaturas
		listaAsignaturas();
	}
	
	public void reload() throws IOException {
		//Funcion para recargar la pagina actual
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=2");
	}
	
	public void limpiarObjeto() {
		//Funcion para limpiar el objeto asignatura
		asignatura = new Asignatura();
	}
	
	public void listaAsignaturas() {
		asignaturas = asignaturaController.obtenerListaAsignatura();
	}
	
	//Metodo para eliminar asignatura deseadas
	public void eliminarAsignatura(Integer i) {
		try {
			//Definir los atributos que se van a cambiar para actualizar el registro
			//Los datos no se eliminan de la BD solo no se pueden visualizar
			asignatura = asignaturas.get(i);
			asignatura.setEstado(0);
			
			//Lllamar a la funcion actualiza 
			asignaturaController.actualizarAsignatura(asignatura);
			
			//Realizar una validaciones
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			//En caso de error mostrar el siguiente mensaje
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha Eliminado", "No se ha Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void seleccionarAsignatura(Integer i) {
		//Función para seleccionar una Asignatura en especifico y asignarla al objeto asignatura
		asignatura = asignaturas.get(i);
	}
	
	public void guardarAsignatura() throws IOException {
		try {
			//Funcion para guardar asignatura ya sea crear una nueva o actualizar la informacion existente
			if(asignatura.getIdAsignatura() != null) {
				asignaturaController.actualizarAsignatura(asignatura);
			}else {
				asignaturaController.crearAsignatura(asignatura);			
			}
			
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo", "No se guardo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}

	//-------------------------Metodos GET y SET -------------------------------------------
	//-----------Para visualizar y editar la infromacion-------------------------------------
	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}	
	
	
}
