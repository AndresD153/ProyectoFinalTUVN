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

import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.controllers.impl.ProfesorControllerImpl;
import com.tuvn.webNA.models.entities.Profesor;

@ManagedBean
@ViewScoped
public class ProfesoresView implements Serializable{

	private static final long serialVersionUID = 1L;

	private ProfesorController profesorController;
	
	private List<Profesor> profesores;
	
	private Profesor profesor;
	
	public ProfesoresView() {
		
	}
	
	@PostConstruct
	public void init () {
		profesorController = new ProfesorControllerImpl();
		
		profesor = new Profesor();
		
		listarProfesores();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=3");
	}

	public void limpiarObjeto() {
		profesor = new Profesor();
	}
	
	public void listarProfesores() {
		profesores = profesorController.obtenerListaProfesor();
	}
	
	public void eliminarProfesor(Integer i) {
		try {
			profesor = profesores.get(i);
			profesor.setEstado(0);
			profesorController.actualizarProfesor(profesor);
			
			limpiarObjeto();
			reload();
						
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se elimino", "No se elimino");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void seleccionarProfesor(Integer i) {
		profesor = profesores.get(i);
	}
	
	public void guardarProfesor() {
		try {
			if(profesor.getIdProfesor() != null ) {				
				profesorController.actualizarProfesor(profesor);
				limpiarObjeto();
			}else {
				Boolean validate = profesorController.crearProfesor(profesor);
				if(validate == false) {					
					throw new Exception();
				}
			}
			
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el dato", "No se guardo el dato");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	//--------------------------------------------------------------------------------------------------------
	public List<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
}
