package com.tuvn.webNA.views.users;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.controllers.impl.ProfesorControllerImpl;
import com.tuvn.webNA.models.entities.Profesor;
import com.tuvn.webNA.utils.SessionsUtils;

//--Para que sea visible
@ManagedBean
@ViewScoped

public class LoginView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ProfesorController profesorController;
	
	private String username;
	private String password;

	private Profesor profesor;
	
	public LoginView() {
		
	}
	
	//--Iniciador / para no digitar codigo e el constructor
	@PostConstruct
	public void init() {
		profesorController = new ProfesorControllerImpl();
	}
	
	public void iniciarSesion() {	
		try {
			profesor = profesorController.iniciarProfesor(username, password);
			
			if(profesor.getIdProfesor()!=null) {
				HttpSession session = SessionsUtils.getSession();
                session.setAttribute("username", profesor.getIdProfesor().toString());
                
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO,"Info", "Session Iniciada"));
                
                if(profesor.getRol()==1) {
                	redirectLogin("pages/index.xhtml");      
                	
                }else {
                	redirectLogin("admin/admin.xhtml");
                }
                
                
               
			}else {
				throw new Exception();
			}
			
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN,"Avertencia", "Usuario o Password incorrectos"));
		}
	}
	
	
	public void redirectLogin(String redirect) throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(redirect);

	}
	
	public void cerrarSesion() throws IOException {
		HttpSession session = SessionsUtils.getSession();
		session.invalidate();
		
		redirectLogin("../login.xhtml");
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
