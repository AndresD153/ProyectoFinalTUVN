package com.tuvn.webNA.views.asistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.tuvn.webNA.controllers.AsistenciaController;
import com.tuvn.webNA.controllers.FechaAsistenciaController;
import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.impl.AsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.FechaAsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.models.entities.Asistencia;
import com.tuvn.webNA.models.entities.FechaAsistencia;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;

@ManagedBean
@ViewScoped
public class AsistenciaView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AsistenciaController asistenciaController;
	private FechaAsistenciaController fechaAsistenciaController;
	private MatriculaDetalleController matriculaDetalleController;
	private MatriculaController matriculaController;
	
	private List<Asistencia> asistencias;
	private List<MatriculaDetalle> estudiantes;
		
	private Asistencia asistencia;
	private FechaAsistencia fechaAsistencia;
	private Matricula matricula;
	//private MatriculaDetalle matriculaDetalle;
	private String idMatricula;

	public AsistenciaView() {
		
	}

	@PostConstruct
	public void init() {
		
		//Inizialisar kas clases a utilizar
		asistenciaController = new AsistenciaControllerImpl();
		fechaAsistenciaController = new FechaAsistenciaControllerImpl();
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		matriculaController = new MatriculaControllerImpl();
		
		
		idMatricula = getId();
		matricula = matriculaController.buscarPorId(idMatricula);
		
		listarAsistenciasDia();
	}
	
	private String getId() {
		//Funcion para obtener el Id de la matricula
		//Leer el contexto 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		//Mapear los parammetros extraidos de la web 
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap(); 
		//Asignar a la variable getParam el parametro de la URL
		String getParam = params.get("matricula");
		
		//Retorna una cadena de caracteres con el ID de matricula
		return getParam;
	}
	
	public String convertirFechaCompleta(Date objetoFecha) {
		//Inizialisar un arrayList con los dias de la semana
		ArrayList<String> dia = new ArrayList<>();
		//Agregar los dias de la semana al array Dia
		dia.add("Domingo");
		dia.add("Lunes");
		dia.add("Martes");
		dia.add("Miercoles");
		dia.add("Jueves");
		dia.add("Viernes");
		dia.add("Sabado");
		
		//Definir objeto Calendario con una fecha enviada previamente
		Calendar c = Calendar.getInstance();
		c.setTime(objetoFecha);
		
		//Obtener el año el mes y el dia a partir del objeto Calendario
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int d = (c.get(Calendar.DAY_OF_WEEK))-1;
		
		//Concatenar la respuestas y mostrar en el fomato correcto
		String dateString = dia.get(d)+"  | "+String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);

		return dateString;
	}

	private String convertirFecha(Date objetoFecha) {

		Calendar c = Calendar.getInstance();
		c.setTime(objetoFecha);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
	
		//Concatenar la fecha en el formato Ecuatoriano y retornarlo
		String dateString = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);

		return dateString;
	}
	
	public void reload() throws IOException {
		//Funcion para recarlar la pagina
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()+"?i=2&matricula="+matricula.getCodigo());

	}
	
	public void listarAsistenciasDia() {
		asistencias = asistenciaController.obtenerListaAsistenciasDia(idMatricula);
	}
	
	public void generarAsistencia() throws IOException {
		//Llamar al objeto fecha asitencia a travez de la fecha actual
		fechaAsistencia = fechaAsistenciaController.validarFecha(new Date());
		
		//Obtener la fecha actual y la fecha antigua con el formato Ecuatoriano
		String fActual = convertirFecha(new Date());
		String fAntigua = convertirFecha(fechaAsistencia.getFecha());
		
		//Llamar al objeto asistencia con los datos fechaActual y IdMatricula 
		asistencia = asistenciaController.buscarPrimeraAsistencia(new Date(), idMatricula);
		
		//Validar que la fechaAsitencia exista.
		if(fechaAsistencia.getIdFechaAsistencia()==null) {
			
			//Si no existe se debera crear una nueva fecha "aplica unicamente para la primera asistencia del dia"
			fechaAsistencia =  fechaAsistenciaController.crearFechaAsistencia(fechaAsistencia);
			//Insertar la sistencia 
			insertarAsistencia(fechaAsistencia);
		
			
		}else if(fActual.equals(fAntigua) && asistencia.getAsistencia()!=null){
			//Si la fecha ya fue creada saltara un mensaje como advertencia
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se ha generado la asisitecia", "Porque ya existe");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		}else {
			insertarAsistencia(fechaAsistencia);
		}
				
		
	}
	
	private void insertarAsistencia(FechaAsistencia fecha) throws IOException {
		//Obtener la lista de estudiantes matriculados en la materia
		estudiantes = matriculaDetalleController.obtenerListaMatriculaDetalle(matricula.getIdMatricula());
		//Inizialisar una nueva asitencia
		asistencia = new Asistencia();
		//Llena el atributo fecha con la fecha enviada
		asistencia.setIdFechaAsistencia(fecha);
		
		//Realiza un bucle para la insercion de las asitencias con el numero total de estudiantes matriculados
		for (Iterator<MatriculaDetalle> iterator = estudiantes.iterator(); iterator.hasNext();) {
			//Obtener el objeto MatriculaDetalle desde la lista de estudiantes
			MatriculaDetalle md = (MatriculaDetalle) iterator.next();
			//Actualizar el objeto asitencia con la matricula detalle 
			asistencia.setIdMatriculaDetalle(md);
			//Actualizar el objeto asitencia con el valor pordefecto que va a ser el "2" simboliza "presente"
			asistencia.setAsistencia(2);
			//Guardar los cambioos
			asistenciaController.crearAsistencia(asistencia);
		}
		
		//Una vez realizada la insertcion refrescar la pagina
		reload();
	}
	
	//----------------------------------------------------------------
	public List<Asistencia> getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	
	
}
