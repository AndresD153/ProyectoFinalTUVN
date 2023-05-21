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
		//Inicialisar las listas necesarias para realizar la operacion
		List<Notas> lstNotas = notasController.listaNotasEstudiante(idMatriculaDetalle);
		List<Notas> lista = new ArrayList<Notas>();
		//Inicialisar un objeto simple notas
		Notas nota = new Notas();
		//Actualizar nota con un valor por defecto
		nota.setNota(0.0);
		//Verificar que la lista no este vacia
		if(lstNotas.size()==0) {
			//Realizar un bucle para agregar datos a la lista "lista"
			for (int i = 0; i < 11; i++) {
				nota.setIdNotas(i);
				lista.add(nota);
			}
			return lista;
		}
		
		int j = 1;
		int index = 0;
		//LLenar la variable s con el numero total de objetos dentro de "lstnotas" y restarlo -1
		int s = lstNotas.size()-1;
		//Buscar mediante el index el ultimo objeto de la lista "lstNotas"
		Notas oNota = lstNotas.get(s);
		//Obtener el ID del ultimo tipo de notas
		int last = oNota.getIdTipoNota().getIdTipoNota();
		
		//Declarar un bucle representando los 11 tipos de notas
		for (int i = 0; i < 11; i++) {
			//Verificar si el index del bucle es menor que el total de objetos dentro del array "lstNotas"
			if(i <= s) {
				//Obtener mediante el index un objeto dentro de "lstNotas"
				Notas n = lstNotas.get(index);
				//Guardar en la variable "j" el valor del id tipo nota actual -1
				j = (n.getIdTipoNota().getIdTipoNota()-1);
			
				if(j == i) {
					//Si j == i añadir un nuevo objeto Nota a "lista"  
					lista.add(n);
					//Sumar el index
					index +=1;
				}else {
					//Caso contrario rellenar nota por defecto "0.00"
					nota.setIdNotas(null);
					lista.add(nota);
				}
			}else {
				//Verificar que el ultimo Id de tipo nota sea igual a index+2
				if(last == (index+2)) {
					//Si cumple, agregar el objeto o nota al array "lista"
					lista.add(oNota);
					//Inicialisar index en 0
					index=0;
				}else {
					//Caso contrario llenar con una nota por defecto la lista "0.00"
					nota.setIdNotas(null);
					lista.add(nota);
				}
			}
		}
		//Retornar la lista final con las notas registradas y notas en 0.00
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
