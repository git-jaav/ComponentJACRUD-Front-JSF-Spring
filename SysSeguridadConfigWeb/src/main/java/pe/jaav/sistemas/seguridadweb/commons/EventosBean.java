package pe.jaav.sistemas.seguridadweb.commons;

import org.primefaces.model.UploadedFile;

import java.io.InputStream;

//import pe.royalsystems.springerp.service.ws.curso.SaCurso;

public class EventosBean {

	// Evento seleccion Upload File

	public EventoSeleccionUploadFile eventoSeleccionUploadFile;
	public EventoSeleccionUploadFile getEventoSeleccionUploadFile() {
		return eventoSeleccionUploadFile;
	}

	public void setEventoSeleccionUploadFile(EventoSeleccionUploadFile eventoSeleccionUploadFile) {
		this.eventoSeleccionUploadFile = eventoSeleccionUploadFile;
	}



	/*******************************/
	/*************** INTERFACES **************/

	public interface EventoSeleccionUploadFile {
		public void retornoObjectInputStreamFile(InputStream objInputStream, String nombreFile, Integer flagExito);

		public void retornoObjectFileBytes(Object objSeleccion, String nombreFile, Integer flagExito);
		
		public void retornoObjectFileUpload(UploadedFile fileUpload, Integer flagExito);
	}




	// Evento de Reportes Generico
	private EventoGenericReporte eventoGenericReporte;

	public EventoGenericReporte getEventoGenericReporte() {
		return eventoGenericReporte;
	}

	public void setEventoGenericReporte(EventoGenericReporte eventoGenericReporte) {
		this.eventoGenericReporte = eventoGenericReporte;
	}

	// Evento seleccion de motivos de nota de credito


	private EventoGeneralRegistro eventoGeneralRegistro;
	
	public EventoGeneralRegistro getEventoGeneralRegistro() {
		return eventoGeneralRegistro;
	}

	public void setEventoGeneralRegistro(EventoGeneralRegistro eventoGeneralRegistro) {
		this.eventoGeneralRegistro = eventoGeneralRegistro;
	}

	/*******************************/


	public interface EventoGenericReporte {
		public void eventoAceptar(Integer indica, String mensaje);

		public void eventoValidacion(Integer indica, String mensaje);
	}
	
	public interface EventoGeneralRegistro {
		public void retornoAceptar(Integer indica, String mensaje);
		public void retornoCancelar(Integer indica, String mensaje);
		public void retornoOperacion(String indicaTipo,Object objeto, String mensaje);
		
	}
	
//	public interface EventoSeleccionWSSaCurso{
//		public void retornoWSSacurso(SaCurso curso, String mensaje);
//	}
}
