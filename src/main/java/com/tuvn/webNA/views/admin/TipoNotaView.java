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

import com.tuvn.webNA.controllers.TipoNotaController;
import com.tuvn.webNA.controllers.impl.TipoNotaControllerImpl;
import com.tuvn.webNA.models.entities.TipoNota;

@ManagedBean
@ViewScoped
public class TipoNotaView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TipoNotaController tipoNotaController;
	
	private List<TipoNota> tipoNotas;
	
	private TipoNota tipoNota;

	public TipoNotaView() {
		
	}

	@PostConstruct
	public void init () {
		tipoNotaController = new TipoNotaControllerImpl();
		
		tipoNota = new TipoNota();
		
		listarTipoNotas();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=4");
	}
	
	public void limpiarObjeto() {
		tipoNota = new TipoNota();
	}

	public void listarTipoNotas() {
		tipoNotas = tipoNotaController.obtenerListaTipoNota();
	}
	
	public void seleccionarTipoNota(Integer i) {
		tipoNota = tipoNotas.get(i);
	}
	
	public void eliminarTipoNota(Integer i) {
		try {
			
			tipoNota = tipoNotas.get(i);
			tipoNota.setTipoEstado(0);
			
			tipoNotaController.actualizarTipoNota(tipoNota);
			
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha Eliminado", "No se ha Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void guardarTipoNota() {
		try {
			if(tipoNota.getIdTipoNota() != null ) {
				tipoNotaController.actualizarTipoNota(tipoNota);
			}else {
				tipoNotaController.crearTipoNota(tipoNota);
			}
			
			limpiarObjeto();
			reload();
			
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se guardo el dato", "No se guardo el dato");
            FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	//-------------------------------------------------------------------------------------------
	public List<TipoNota> getTipoNotas() {
		return tipoNotas;
	}

	public void setTipoNotas(List<TipoNota> tipoNotas) {
		this.tipoNotas = tipoNotas;
	}

	public TipoNota getTipoNota() {
		return tipoNota;
	}

	public void setTipoNota(TipoNota tipoNota) {
		this.tipoNota = tipoNota;
	}
}
