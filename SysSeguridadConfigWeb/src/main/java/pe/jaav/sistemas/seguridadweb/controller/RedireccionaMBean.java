package pe.jaav.sistemas.seguridadweb.controller;


import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;


@ManagedBean(name = "redireccionaMBean")
@ViewScoped
public class RedireccionaMBean {
		
    private static final String URL_PORTALGENERAL= FacesUtil.getPropertyParametros("PAR_PAGININA_DEFAULT"); 	
	
	private boolean cambioClave = false;
	private  String urlActual;
	private String lenguaje;
	
	
	//@PostConstruct
    public void inicializar() { 
        /*** LOGICA PARA REDIRECCIONAR A PAGINA PRINCIPAL ****/
        cambioClave=false;        
        String  paginaSgt = URL_PORTALGENERAL;        
        urlActual = paginaSgt; 
        setParametrosDefault();
        FacesUtil.redirect(paginaSgt);
    }

    public void setParametrosDefault(){
    	try{
        	FacesUtil.propiedades = ResourceBundle.getBundle("mensajes",FacesUtil.getContextLocale());
        	
        	EntidadSession.getInstance().setSysUrl(URL_PORTALGENERAL);
        	EntidadSession.getInstance().setUrlPaginaInicio(URL_PORTALGENERAL);
        	
        	EntidadSession.getInstance().setConceptoActualDesc(FacesUtil.getPropertyParametros("PAR_SEG_CONCEPTODESC_DEF"));
        	EntidadSession.getInstance().setConceptoActual(FacesUtil.getPropertyParametros("PAR_SEG_CONCEPTO_DEF"));
        	EntidadSession.getInstance().setGrupoActual(FacesUtil.getPropertyParametros("PAR_SEG_GRUPO_DEF"));
        	EntidadSession.getInstance().setAplicacionCodigoActual(FacesUtil.getPropertyParametros("PAR_SEG_APPCODIGO_DEF"));

//        	EntidadSession.getInstance().setSysConceptoDesc(FacesUtil.getPropertyParametros("PAR_SEG_CONCEPTODESC_DEF"));
//        	EntidadSession.getInstance().setSysConcepto(FacesUtil.getPropertyParametros("PAR_SEG_CONCEPTO_DEF"));
//        	EntidadSession.getInstance().setSysGrupo(FacesUtil.getPropertyParametros("PAR_SEG_GRUPO_DEF"));
//        	EntidadSession.getInstance().setSysAplicacionCodigo(FacesUtil.getPropertyParametros("PAR_SEG_APPCODIGO_DEF"));
//    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
	public String ingresar() {
		return urlActual;
	}
	public boolean isCambioClave() {
		return cambioClave;
	}

	public void setCambioClave(boolean cambioClave) {
		this.cambioClave = cambioClave;
	}

	public String getLenguaje() {
		lenguaje =FacesUtil.getSessionLocale().getLanguage(); 
		return lenguaje;
	}

	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
}


