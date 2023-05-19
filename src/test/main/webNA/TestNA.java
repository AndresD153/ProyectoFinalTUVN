package webNA;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.tuvn.webNA.controllers.AsignaturaController;
import com.tuvn.webNA.controllers.AsistenciaController;
import com.tuvn.webNA.controllers.CarreraController;
import com.tuvn.webNA.controllers.EstudianteController;
import com.tuvn.webNA.controllers.FechaAsistenciaController;
import com.tuvn.webNA.controllers.MatriculaController;
import com.tuvn.webNA.controllers.MatriculaDetalleController;
import com.tuvn.webNA.controllers.NotasController;
import com.tuvn.webNA.controllers.ProfesorController;
import com.tuvn.webNA.controllers.TipoNotaController;
import com.tuvn.webNA.controllers.impl.AsignaturaControllerImpl;
import com.tuvn.webNA.controllers.impl.AsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.CarreraControllerImpl;
import com.tuvn.webNA.controllers.impl.EstudianteControllerImpl;
import com.tuvn.webNA.controllers.impl.FechaAsistenciaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaControllerImpl;
import com.tuvn.webNA.controllers.impl.MatriculaDetalleControllerImpl;
import com.tuvn.webNA.controllers.impl.NotasControllerImpl;
import com.tuvn.webNA.controllers.impl.ProfesorControllerImpl;
import com.tuvn.webNA.controllers.impl.TipoNotaControllerImpl;
import com.tuvn.webNA.models.entities.Asignatura;
import com.tuvn.webNA.models.entities.Asistencia;
import com.tuvn.webNA.models.entities.Carrera;
import com.tuvn.webNA.models.entities.Estudiante;
import com.tuvn.webNA.models.entities.FechaAsistencia;
import com.tuvn.webNA.models.entities.Matricula;
import com.tuvn.webNA.models.entities.MatriculaDetalle;
import com.tuvn.webNA.models.entities.Notas;
import com.tuvn.webNA.models.entities.Profesor;
import com.tuvn.webNA.models.entities.TipoNota;

public class TestNA {
	
	private EstudianteController estudiantecontroller;
	private AsignaturaController asignaturaController;
	private CarreraController carreraController;
	private ProfesorController profesorController;
	private TipoNotaController tipoNotaController;
	private FechaAsistenciaController fechaAsistenciaController;
	private AsistenciaController asistenciaController;
	private MatriculaController matriculaController;
	private MatriculaDetalleController matriculaDetalleController;
	private NotasController notasController;
	
	private Asignatura asignatura;
	private Estudiante estudiante;
	private Carrera carrera;
	private Profesor profesor;
	private TipoNota tipoNota;
	private FechaAsistencia fechaAsistencia;
	private Asistencia asistencia;
	private MatriculaDetalle matriculaDetalle;
	private Matricula matricula;
	private Notas notas;

	@Test
	public void test() {
		this.crearCarrera();
		this.crearEstudiante();
		this.crearProfesor();
		this.crearAsignatura();
		this.crearMatricula();
		this.crearTipoNota();
		this.crearFechaAsistencia();
		this.crearMatriculaDetalle();
		this.crearAsistencia();
		this.crearNotas();
	}
	
	private void crearCarrera() {
		carreraController = new CarreraControllerImpl();
		
		carrera = new Carrera();
		
		carrera.setNombre("Desarollo");
		carrera.setEstado(1);
		
		carreraController.crearCarrera(carrera);
		System.out.println("Carrera creada");
	}
	
	private void crearEstudiante() {
		estudiantecontroller = new EstudianteControllerImpl();
		
		estudiante = new Estudiante();
		Calendar cal=Calendar.getInstance();
		cal.set(2001, 5, 13);
		Date fNac=cal.getTime();
		
		estudiante.setNombre("Andres");
		estudiante.setApellido("David");
		estudiante.setCiEstudiante("1753638887");
		estudiante.setEmail("andres@gmail.com");
		estudiante.setFechaNacimiento(fNac);
		estudiante.setIdCarrera(carrera);
		
		estudiantecontroller.crearEstudiante(estudiante);
		System.out.println("estuidante creado");
	}
	
	private void crearAsignatura() {
		asignaturaController = new AsignaturaControllerImpl();
		
		asignatura = new Asignatura();
		
		asignatura.setNombre("Redes");
		asignatura.setDetalle("Materia de redes");
		
		asignaturaController.crearAsignatura(asignatura);
		System.out.println("Asignatura creada");
		
		
	}

	private void crearProfesor() {
		profesorController = new ProfesorControllerImpl();
		
		profesor = new Profesor();
				
		profesor.setNombre("Pedro");
		profesor.setApellido("Miguel");
		profesor.setCedula("1478523691");
		profesor.setEdad(41);
		profesor.setEmail("pedroMiguel@gmail.com");
		profesor.setProfecion("Ingeniero en Sistemas");
		profesor.setUsuario("pedro");
		profesor.setPassword("pedro");
		
		profesorController.crearProfesor(profesor);
		System.out.println("Profesor creado");
	}

	private void crearTipoNota() {
		tipoNotaController = new TipoNotaControllerImpl();
		
		tipoNota = new TipoNota();
		
		tipoNota.setTipoNombre("Evaluacion");
		
		tipoNotaController.crearTipoNota(tipoNota);
		System.out.println("Tipo de Nota Creada");
	}
	
	private void crearFechaAsistencia() {
		fechaAsistenciaController = new FechaAsistenciaControllerImpl();
		
		fechaAsistencia = new FechaAsistencia();
		
		fechaAsistenciaController.crearFechaAsistencia(fechaAsistencia);
		System.out.println("Fecha de asistencia creada");
	}

	private void crearAsistencia() {
		asistenciaController = new AsistenciaControllerImpl();
		
		asistencia = new Asistencia();
		
		asistencia.setIdFechaAsistencia(fechaAsistencia);
		asistencia.setIdMatriculaDetalle(matriculaDetalle);
		asistencia.setAsistencia(1);
		
		asistenciaController.crearAsistencia(asistencia);
		System.out.println("Asistencia creada");
	}

	private void crearMatricula() {
		matriculaController = new MatriculaControllerImpl();
		
		matricula = new Matricula();
		
		matricula.setIdProfesor(profesor);
		matricula.setIdAsignatura(asignatura);
		matricula.setCodigo("22231753");
		
		matriculaController.crearMatricula(matricula);
		System.out.println("Matricula creada");
	}
	
	private void crearMatriculaDetalle() {
		matriculaDetalleController = new MatriculaDetalleControllerImpl();
		
		matriculaDetalle = new MatriculaDetalle();
		
		matriculaDetalle.setIdEstudiante(estudiante);
		matriculaDetalle.setIdMatricula(matricula);
		
		matriculaDetalleController.crearMatriculaDetalle(matriculaDetalle);
		System.out.println("Matricula Detalle creada");
	}
	
	private void crearNotas() {
		notasController = new NotasControllerImpl();
		
		notas = new Notas();
		
		notas.setIdMatriculaDetalle(matriculaDetalle);
		notas.setIdTipoNota(tipoNota);
		notas.setNota(10.2);
		
		notasController.crearNotas(notas);
		System.out.println("Nota creada correctamente");
		
	}

}
