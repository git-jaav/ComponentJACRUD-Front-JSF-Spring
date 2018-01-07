package pe.jaav.sistemas.seguridadweb.server;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public  class EntidadSession implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//ADICIONAL PARA INTERNACIONALIZACION	
	private Locale localeConfigActual;
	
	private Integer tipoUsuario;	
	private String ipCliente;
	private String sysUrl;
	private String sysVersion;
	private String flagObligarcambioClave;
	private String macCliente;
	private String pcNameCliente;

	public Map<String,String>  mapPropSeguridad= new HashMap<String ,String>();
	public Map<String,String>  mapPropParametros= new HashMap<String ,String>();		
	
	///PARA PARAMETROS CORREO
	private String correoUsuario;
	private String correoClave;
	private String correoHost;
	private String correoPort;
	private String correoSocketPort;
		
	
	////PARA SET CONCEPTO ACTUAL
	private String conceptoActualDesc;
	private String aplicacionCodigoActual;
	private String grupoActual;
	private String conceptoActual;
	private String conceptoPadreActual;	
	private String hostActual;
	private Date fechaSessionActual;	
	private Integer persona;    
	private String usuario;
	
	private String nombreUsuario;
	private String aplicacionCodigo;
		
	private String urlPaginaInicio;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getPersona() {
		return persona;
	}

	public void setPersona(Integer persona) {
		this.persona = persona;
	}


	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public static void setInstance(EntidadSession instance) {
		Instance = instance;
	}

	
	
	
	public String getGrupoActual() {
		return grupoActual;
	}

	public void setGrupoActual(String grupoActual) {
		this.grupoActual = grupoActual;
	}

	public String getConceptoActual() {
		return conceptoActual;
	}

	public void setConceptoActual(String conceptoActual) {
		this.conceptoActual = conceptoActual;
	}

	public String getConceptoPadreActual() {
		return conceptoPadreActual;
	}

	public void setConceptoPadreActual(String conceptoPadreActual) {
		this.conceptoPadreActual = conceptoPadreActual;
	}




	public String getAplicacionCodigoActual() {
		return aplicacionCodigoActual;
	}

	public void setAplicacionCodigoActual(String aplicacionCodigoActual) {
		this.aplicacionCodigoActual = aplicacionCodigoActual;
	}




	public String getHostActual() {
		return hostActual;
	}

	public void setHostActual(String hostActual) {
		this.hostActual = hostActual;
	}




	public Date getFechaSessionActual() {
		return fechaSessionActual;
	}

	public void setFechaSessionActual(Date fechaSessionActual) {
		this.fechaSessionActual = fechaSessionActual;
	}

	
    private static  EntidadSession Instance;

	public static EntidadSession getInstance() {
		try{			
			if(FacesContext.getCurrentInstance()!=null){							
				HttpSession session = (FacesContext.getCurrentInstance().getExternalContext().getSession(false)!=null?
						(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false):null );
				if(session!=null){
					Instance =(EntidadSession) session.getAttribute("EntidadSession");
				}else{
					Instance=null;
				}
				//return  entityGlobal;
				return Instance;		
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public static EntidadSession closeInstance() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("EntityGlobal");
		//return  entityGlobal;
		Instance= null;
		return Instance;
	}



	public String getIpCliente() {
		return ipCliente;
	}


	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getMacCliente() {
		return macCliente;
	}

	public void setMacCliente(String macCliente) {
		this.macCliente = macCliente;
	}

	public String getPcNameCliente() {
		return pcNameCliente;
	}

	public void setPcNameCliente(String pcNameCliente) {
		this.pcNameCliente = pcNameCliente;
	}

	public Map<String, String> getMapPropSeguridad() {
		return mapPropSeguridad;
	}

	public void setMapPropSeguridad(Map<String, String> mapPropSeguridad) {
		this.mapPropSeguridad = mapPropSeguridad;
	}

	public Map<String, String> getMapPropParametros() {
		return mapPropParametros;
	}

	public void setMapPropParametros(Map<String, String> mapPropParametros) {
		this.mapPropParametros = mapPropParametros;
	}

	
	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getCorreoClave() {
		return correoClave;
	}

	public void setCorreoClave(String correoClave) {
		this.correoClave = correoClave;
	}

	public String getCorreoHost() {
		return correoHost;
	}

	public void setCorreoHost(String correoHost) {
		this.correoHost = correoHost;
	}

	public String getCorreoPort() {
		return correoPort;
	}

	public void setCorreoPort(String correoPort) {
		this.correoPort = correoPort;
	}

	public String getCorreoSocketPort() {
		return correoSocketPort;
	}

	public void setCorreoSocketPort(String correoSocketPort) {
		this.correoSocketPort = correoSocketPort;
	}

	public String getConceptoActualDesc() {
		return conceptoActualDesc;
	}

	public void setConceptoActualDesc(String conceptoActualDesc) {
		this.conceptoActualDesc = conceptoActualDesc;
	}


	public String getFlagObligarcambioClave() {
		return flagObligarcambioClave;
	}

	public void setFlagObligarcambioClave(String flagObligarcambioClave) {
		this.flagObligarcambioClave = flagObligarcambioClave;
	}

	public Locale getLocaleConfigActual() {
		return localeConfigActual;
	}

	public void setLocaleConfigActual(Locale localeConfigActual) {
		this.localeConfigActual = localeConfigActual;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getAplicacionCodigo() {
		return aplicacionCodigo;
	}

	public void setAplicacionCodigo(String aplicacionCodigo) {
		this.aplicacionCodigo = aplicacionCodigo;
	}

	public String getUrlPaginaInicio() {
		return urlPaginaInicio;
	}

	public void setUrlPaginaInicio(String urlPaginaInicio) {
		this.urlPaginaInicio = urlPaginaInicio;
	}


}