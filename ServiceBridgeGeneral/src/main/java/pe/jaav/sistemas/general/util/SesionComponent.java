package pe.jaav.sistemas.general.util;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
//@Scope(value="session")
public class SesionComponent {

	private String nombre = "Session Map";	
	private Map<String,String> mapTokenSesion;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Map<String, String> getMapTokenSesion() {
		return mapTokenSesion;
	}
	public void setMapTokenSesion(Map<String, String> mapTokenSesion) {
		this.mapTokenSesion = mapTokenSesion;
	}
	
	
}
