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
	
	@PostConstruct
	public void init() {
		profesorController = new ProfesorControllerImpl();
	}
	
	public void iniciarSesion() {	
		try {
			//Llenar el objeto profesor con la informacion requerida "usuario y password"
			profesor = profesorController.iniciarProfesor(username, password);
			
			//Validar que el objeto Profesor contenga datos 
			//si es nullo generara una excepcion 
			if(profesor.getIdProfesor()!=null) {
				
				//Si el Profesor contiene datos inisializa la sesion con los datos ingresados
				HttpSession session = SessionsUtils.getSession();
                session.setAttribute("username", profesor.getIdProfesor().toString());
                
                //Se envia una notificacion 
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO,"Info", "Session Iniciada"));
                
                //Valida el rol del usuario "Admin o Profesor"
                if(profesor.getRol()==1) {
                	redirectLogin("pages/index.xhtml");      
                	
                }else {
                	redirectLogin("admin/admin.xhtml");
                }
                
                
               
			}else {
				throw new Exception();
			}
			
		}catch (Exception e) {
			//Si existe excepcion mandar un mensaje de adbertencia
			FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_WARN,"Avertencia", "Usuario o Password incorrectos"));
		}
	}
	
	
	public void redirectLogin(String redirect) throws IOException {
		//Redirecciona a la pagina solicitada
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(redirect);

	}
	
	public void cerrarSesion() throws IOException {
		//Destruye la sesion creada
		HttpSession session = SessionsUtils.getSession();
		session.invalidate();
		
		//Redirecciona al login
		redirectLogin("../login.xhtml");
	}
	

	//-------------------------------- GEt y Set ----------------------
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
