package com.tuvn.webNA.views.users;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.controllers.impl.ProfesorControllerImpl;
import com.tuvn.webNA.models.entities.Profesor;
import com.tuvn.webNA.utils.SessionsUtils;

@ManagedBean
@ViewScoped
public class PerfilView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ProfesorController profesorController;
	
	private Profesor profesor;
	
	private Integer idProfesor;
	

	public PerfilView() {
		
	}
	
	@PostConstruct
	public void init () {
		profesorController = new ProfesorControllerImpl();
		
		obtenerIdProfesor();
		
		profesor = profesorController.buscarPorId(idProfesor);
	}
	
	private void obtenerIdProfesor() {
		HttpSession session = SessionsUtils.getSession();
		String id = session.getAttribute("username").toString();
		idProfesor = Integer.parseInt(id);
	}
	
	public void actualizarPerfil() {
		profesorController.actualizarProfesor(profesor);
		
		FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO,"Info", "Información Actualizada"));
	}
	
	//------------

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	
}
