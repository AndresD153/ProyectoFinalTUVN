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
		//Inicialisar los objetos necesarios
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
		//Funcion para recargar la pagina con parametros 
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=1&matricula="+idMatricula+"&tipoNota="+idTipoNota);
	}
	
	public void obtenerListaNotas() {
		notas = notasController.obtenerListaNotas(idMatricula,idTipoNota);
	}
	
	private String getIdMatricula() {
		//Obtener el ID de la Matricula atravez de la URL 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private Integer getIdTipoNota() {
		//Obtener el ID de TipoNota atravez de la URL
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("tipoNota");
		return Integer.parseInt(getParam);
	}
	
	public void editarCelda(CellEditEvent<Object> event) {	
		//Obtener el antiguo valor y el nuevo valor
		Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        //Obtener el Index de la celda seleccionada
        Integer i = event.getRowIndex();
        //Obtener el objeto nota mediante el Index
        nota = notas.get(i);
        
        nota.setNota(Double.parseDouble(newValue.toString()));
        //Llamar a la accion actualizaNotaa
        notasController.actualizarNotas(nota);
        
        System.out.println(nota);
        //Verificar si el valor nuevo no es nulo y el valor nuevo no es igual al antiguo
        if (newValue != null && !newValue.equals(oldValue)) {
        	//En caso de cumplir con la afirmacion propuesta enviar una alerta
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nota Actualizada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
	}
	
	
	public void listaEStudiantes() {
		matricula = matriculaController.buscarPorId(idMatricula);
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
	}
	
	public void crearNotaEstudiante() throws IOException {
		//Obtener a traves de la lista Notas el objeto simple Nota a partir del IdMatriculaDetalle 
		Notas n = notas.stream().filter(item -> item.getIdMatriculaDetalle().getIdMatriculaDetalle().equals(idMatriculaDetalle))
				.limit(1)
				.findFirst().orElse(null);
		//Verificar que el objeto nota no sea nullo
		if(n!=null) {
			//Si es nullo enviar una alerta
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nota duplicada", "Nota ya asignada"));
		}else {
			//Buscar el tipo nota requerido
			TipoNota tn = tipoNotaController.buscarPorId(idTipoNota);
			//A traves de la lista estudiantes buscar por ID la matriculaDetalle y guardarlo en un objeto simple MatriculaDetalle "md"
			MatriculaDetalle md = estudiantes.stream().filter(item -> item.getIdMatriculaDetalle().equals(idMatriculaDetalle))
					.limit(1)
					.findFirst().orElse(null);
			//Inicialisar notas y editarlas con parametros por defecto
			Notas not = new Notas();
			not.setIdMatriculaDetalle(md);
			not.setIdTipoNota(tn);
			not.setNota(0.0);
			//Llamar a la accion crear notas
			notasController.crearNotas(not);
			
			reload();
		}
	}
	
	
	//-------------------------------- Get y Set -----------------------------------	
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
