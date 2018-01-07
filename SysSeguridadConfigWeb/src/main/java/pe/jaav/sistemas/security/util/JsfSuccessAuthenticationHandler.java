package pe.jaav.sistemas.security.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import pe.jaav.sistemas.seguridadweb.util.FacesUtil;


public class JsfSuccessAuthenticationHandler implements AuthenticationSuccessHandler {
	
	private String paginaInicio = "";
	
	public void setPaginaInicio(String paginaInicio) {
		this.paginaInicio = paginaInicio;
	}
	
	public String getPaginaInicio() {
		return paginaInicio;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(paginaInicio != null && !paginaInicio.trim().equals("")){
			FacesUtil.redirect(paginaInicio);
		}
	}

}
