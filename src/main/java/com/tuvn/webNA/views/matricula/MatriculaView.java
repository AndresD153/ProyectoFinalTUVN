package com.tuvn.webNA.views.matricula;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tuvn.webNA.controllers.AsignaturaController;
import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.controllers.impl.AsignaturaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.ProfesorControllerImpl;
import com.tuvn.webNA.models.entities.Asignatura;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.Profesor;
import com.tuvn.webNA.utils.SessionsUtils;

@ManagedBean
@ViewScoped
public class MatriculaView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MatriculaController matriculaController;
	private AsignaturaController asignaturaController;
	private ProfesorController profesorController;

	private List<Matricula> matriculas;
	private List<Asignatura> asignaturas;

	private Profesor profesor;
	private Asignatura asignatura;
	private Matricula matricula;
	
	private Integer idProfesor;
	private Integer idAsignatura;
	
	

	public MatriculaView() {
		
	}

	@PostConstruct
	public void init() {
		matriculaController = new MatriculaControllerImpl();
		asignaturaController = new AsignaturaControllerImpl();
		profesorController = new ProfesorControllerImpl();
		
		obtenerMatriculas();
		obtenerAsignaturas();
	}
	
	private void obtenerIdProfesor() {
		HttpSession session = SessionsUtils.getSession();
		String id = session.getAttribute("username").toString();
		idProfesor = Integer.parseInt(id);
	}
	
	private void obtenerMatriculas() {
		obtenerIdProfesor();
		matriculas = matriculaController.obtenerListaMatricula(idProfesor);
	}

	private void obtenerAsignaturas() {
		asignaturas = asignaturaController.obtenerListaAsignatura();
	}
	
	private void buscarProfesor() {
		profesor = profesorController.buscarPorId(idProfesor);
	}
	
	private void buscarAsignatura() {
		asignatura = asignaturaController.busacrPorId(idAsignatura);
	}
	
	public void crearMatricula() throws IOException {
		if(idAsignatura > 0) {
			matricula = new Matricula();
			
			buscarAsignatura();
			buscarProfesor();
			
			String codigo = generateCod();
			matricula.setCodigo(codigo);
			matricula.setIdAsignatura(asignatura);
			matricula.setIdProfesor(profesor);
			
			matriculaController.crearMatricula(matricula);
			
			reload();
		}
	}
	
	private String generateCod() {
		
		Calendar date = Calendar.getInstance();
		int y1 = date.get(Calendar.YEAR);
		
		date.add(Calendar.MONTH, 6);
		int y2 = date.get(Calendar.YEAR);
		
		String d1 = String.valueOf(y1);
		String c1 = d1.substring(2);
		
		String d2 = String.valueOf(y2);
		String c2 = d2.substring(2);
		
		Double random = (Math.random()*10000 + 1);
		String sr = String.valueOf(Math.round(random));
		String  code = String.valueOf(Integer.parseInt(sr));
				
		return c1+c2+code;
	}
	
	public void reload() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

	}
	//----------------------------------------
	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public Integer getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Integer idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
	
	
	
}
