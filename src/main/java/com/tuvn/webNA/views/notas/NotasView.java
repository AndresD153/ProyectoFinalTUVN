package com.tuvn.webNA.views.notas;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.NotasController;
import com.tuvn.webNA.controllers.TipoNotaController;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.controllers.impl.NotasControllerImpl;
import com.tuvn.webNA.controllers.impl.TipoNotaControllerImpl;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;
import com.tuvn.webNA.models.entities.Notas;
import com.tuvn.webNA.models.entities.TipoNota;

@ManagedBean
@ViewScoped
public class NotasView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TipoNotaController tipoNotaController;
	private NotasController notasController;
	private MatriculaDetalleController matriculaDetalleController;
	private MatriculaController matriculaController;
	
	private List<TipoNota> tipoNotas;
	private List<Notas> notas;
	private List<MatriculaDetalle> matriculas;
	
	private TipoNota tipoNota;
	private Matricula matricula;
	private Notas nota;
	
	private Integer idTipoNota;
	private String idMatricula;

	public NotasView() {	
		
	}

	@PostConstruct
	public void init () {
		tipoNotaController = new TipoNotaControllerImpl();
		notasController = new NotasControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		
		idMatricula = getId();
		matricula = matriculaController.buscarPorId(idMatricula);
		
		obtenerTipoNotas();
		obtenerNotas();
		
	}
	
	public void obtenerTipoNotas() {
		tipoNotas = tipoNotaController.obtenerListaTipoNota();
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private void obtenerNotas() {
		notas = notasController.buscarListadoTipoNotas(idMatricula);
	}
	
	public void crearNotas() {		
		
		try {
			nota = notasController.verificarTipoNota(matricula.getIdMatricula(), idTipoNota);
			
			if(nota.getIdNotas() != null) {
				throw new Exception();
			}else {
				tipoNota = tipoNotaController.buscarPorId(idTipoNota);
				matriculas = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
				
				matriculas.forEach(item -> {
					nota = new Notas();
					nota.setIdTipoNota(tipoNota);
					nota.setNota(0.0);
					nota.setIdMatriculaDetalle(item);
					
					notasController.crearNotas(nota);
				});
				
				reload();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
				     new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nota Existente", "Estudiante ya matriculado"));
		}
		
		
	}
	

	public void reload() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=1&matricula="+matricula.getCodigo());
	}
	
	//----------

	public List<TipoNota> getTipoNotas() {
		return tipoNotas;
	}

	public void setTipoNotas(List<TipoNota> tipoNotas) {
		this.tipoNotas = tipoNotas;
	}

	public Integer getIdTipoNota() {
		return idTipoNota;
	}

	public void setIdTipoNota(Integer idTipoNota) {
		this.idTipoNota = idTipoNota;
	}

	public List<Notas> getNotas() {
		return notas;
	}

	public void setNotas(List<Notas> notas) {
		this.notas = notas;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	
	
	
}
