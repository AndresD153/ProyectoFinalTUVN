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

import com.tuvn.webNA.controllers.CarreraController;
import com.tuvn.webNA.controllers.impl.CarreraControllerImpl;
import com.tuvn.webNA.models.entities.Carrera;

@ManagedBean
@ViewScoped
public class CarreraView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CarreraController carreraController;
	
	private List<Carrera> carreras;
	
	private Carrera carrera;

	public CarreraView() {
		
	}
	
	@PostConstruct
	public void unit () {
		carreraController = new CarreraControllerImpl();
		
		carrera = new Carrera();
		
		listarCarreras();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=0");
	}
	
	public void listarCarreras() {
		carreras = carreraController.obtenerListaCarrera();
	}
	
	public void eliminarCarrera(Integer i) throws IOException {
		try {
			carrera = carreras.get(i);
			carrera.setEstado(0);
			carreraController.actualizarCarrera(carrera);
			limpiarObjeto();
	        reload();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha Eliminado", "No se ha Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	private void limpiarObjeto() {
		 carrera = new Carrera();
	}
	
	public void guardarCarrera() {
		try {
			if(carrera.getIdCarrera()!=null) {
				carreraController.actualizarCarrera(carrera);
				limpiarObjeto();
			}else {
				carreraController.crearCarrera(carrera);
				limpiarObjeto();
			}
			reload();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el dato", "No se guardo el dato");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void seleccionarCarrera(Integer i) {
		carrera = carreras.get(i);
	}
	
	//------------------------------------------------
	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	
	
	
}
