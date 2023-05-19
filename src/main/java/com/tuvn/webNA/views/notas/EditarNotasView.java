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

import org.primefaces.event.CellEditEvent;

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
public class EditarNotasView implements Serializable{

	private static final long serialVersionUID = 1L;

	private NotasController notasController;
	private MatriculaDetalleController matriculaDetalleController;
	private MatriculaController matriculaController;
	private TipoNotaController tipoNotaController;
	
	private List<Notas> notas;
	private List<MatriculaDetalle> estudiantes;
	
	private Notas nota;
	private Matricula matricula;
	
	private String idMatricula;
	private Integer idTipoNota;
	private Integer idMatriculaDetalle;

	public EditarNotasView() {
	
	}
	
	@PostConstruct
	public void init() {
		notasController = new NotasControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		tipoNotaController = new TipoNotaControllerImpl();
		
		idMatricula = getIdMatricula();
		idTipoNota = getIdTipoNota();
		
		obtenerListaNotas();
		listaEStudiantes();
	}
	
	public void reload() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=1&matricula="+idMatricula+"&tipoNota="+idTipoNota);
	}
	
	public void obtenerListaNotas() {
		notas = notasController.obtenerListaNotas(idMatricula,idTipoNota);
	}
	
	private String getIdMatricula() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private Integer getIdTipoNota() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("tipoNota");
		return Integer.parseInt(getParam);
	}
	
	public void editarCelda(CellEditEvent<Object> event) {		
		Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        Integer i = event.getRowIndex();
        
        nota = notas.get(i);
        
        nota.setNota(Double.parseDouble(newValue.toString()));
        
        notasController.actualizarNotas(nota);
        
        System.out.println(nota);

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nota Actualizada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}
	
	
	public void listaEStudiantes() {
		matricula = matriculaController.buscarPorId(idMatricula);
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
	}
	
	public void crearNotaEstudiante() throws IOException {
		Notas n = notas.stream().filter(item -> item.getIdMatriculaDetalle().getIdMatriculaDetalle().equals(idMatriculaDetalle))
				.limit(1)
				.findFirst().orElse(null);
		
		if(n!=null) {
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nota duplicada", "Nota ya asignada"));
		}else {
			TipoNota tn = tipoNotaController.buscarPorId(idTipoNota);
			MatriculaDetalle md = estudiantes.stream().filter(item -> item.getIdMatriculaDetalle().equals(idMatriculaDetalle))
					.limit(1)
					.findFirst().orElse(null);
			
			Notas not = new Notas();
			not.setIdMatriculaDetalle(md);
			not.setIdTipoNota(tn);
			not.setNota(0.0);
			
			notasController.crearNotas(not);
			
			reload();
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------	
	//-------------------------------------------
	public List<Notas> getNotas() {
		return notas;
	}

	public void setNotas(List<Notas> notas) {
		this.notas = notas;
	}

	public List<MatriculaDetalle> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<MatriculaDetalle> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Integer getIdMatriculaDetalle() {
		return idMatriculaDetalle;
	}

	public void setIdMatriculaDetalle(Integer idMatriculaDetalle) {
		this.idMatriculaDetalle = idMatriculaDetalle;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	
	
	
}
