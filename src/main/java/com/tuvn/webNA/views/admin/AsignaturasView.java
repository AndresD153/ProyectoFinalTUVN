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
	
	private AsignaturaController asignaturaController;
	
	private List<Asignatura> asignaturas;
	
	private Asignatura asignatura;

	public AsignaturasView() {
		
	}

	@PostConstruct
	public void unit () {
		asignaturaController = new AsignaturaControllerImpl();
		
		asignatura = new Asignatura();
		
		listaAsignaturas();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=2");
	}
	
	public void limpiarObjeto() {
		asignatura = new Asignatura();
	}
	
	public void listaAsignaturas() {
		asignaturas = asignaturaController.obtenerListaAsignatura();
	}
	
	public void eliminarAsignatura(Integer i) {
		try {
			asignatura = asignaturas.get(i);
			asignatura.setEstado(0);
			
			asignaturaController.actualizarAsignatura(asignatura);
			
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha Eliminado", "No se ha Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void seleccionarAsignatura(Integer i) {
		asignatura = asignaturas.get(i);
	}
	
	public void guardarAsignatura() throws IOException {
		try {
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

	//-----------------------------------------------------------------------------------
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
