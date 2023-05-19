package com.tuvn.webNA.views.notas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.NotasController;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.controllers.impl.NotasControllerImpl;
//import com.ituvn.webNA.models.entities.Estudiante;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;
import com.tuvn.webNA.models.entities.Notas;

@ManagedBean
@ViewScoped
public class ReporteNotasView implements Serializable{

	private static final long serialVersionUID = 1L;

	private NotasController notasController;
	private MatriculaController matriculaController;
	private MatriculaDetalleController matriculaDetalleController;
	
	private Matricula matricula;
	
	private List<MatriculaDetalle> estudiantes;
	
	private String idMatricula;
	
	
	public ReporteNotasView() {
		
	}

	@PostConstruct
	public void init () {
		notasController = new NotasControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		
		idMatricula = getId();
				
		crearListaNotas();
	}
	
	private String getId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		String getParam = params.get("matricula");
		return getParam;
	}
	
	private void crearListaNotas() {
		matricula = matriculaController.buscarPorId(idMatricula);
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
		
	}
	
	public List<Notas> listaNotas(Integer idMatriculaDetalle){
		List<Notas> lstNotas = notasController.listaNotasEstudiante(idMatriculaDetalle);
		List<Notas> lista = new ArrayList<Notas>();
		
		Notas nota = new Notas();
		nota.setNota(0.0);
		
		if(lstNotas.size()==0) {
			for (int i = 0; i < 11; i++) {
				nota.setIdNotas(i);
				lista.add(nota);
			}
			return lista;
		}
		
		int j = 1;
		int index = 0;
		int s = lstNotas.size()-1;
		
		Notas oNota = lstNotas.get(s);
		
		int last = oNota.getIdTipoNota().getIdTipoNota();
		
		for (int i = 0; i < 11; i++) {
			if(i <= s) {
				Notas n = lstNotas.get(index);
				j = (n.getIdTipoNota().getIdTipoNota()-1);
				if(j == i) {
					lista.add(n);
					index +=1;
				}else {
					nota.setIdNotas(null);
					lista.add(nota);
				}
			}else {
				if(last == (index+2)) {
					lista.add(oNota);
					index=0;
				}else {
					nota.setIdNotas(null);
					lista.add(nota);
				}
			}
		}
		return lista;
	}

	//----------------------------------------------------------------------------------
	public List<MatriculaDetalle> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<MatriculaDetalle> estudiantes) {
		this.estudiantes = estudiantes;
	}
	
	
	
	
}
