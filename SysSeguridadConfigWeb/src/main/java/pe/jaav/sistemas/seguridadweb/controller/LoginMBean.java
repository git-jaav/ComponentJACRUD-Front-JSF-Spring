package pe.jaav.sistemas.seguridadweb.controller;


import org.primefaces.context.RequestContext;

import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.security.UserCredentials;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.util.Constant;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;
import pe.jaav.sistemas.seguridadweb.util.Utiles;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ManagedBean(name = "loginMBean")
@ViewScoped
public class LoginMBean implements Serializable{			
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;

	
	
	
	private String email;
	private String clave;
	private String claveAuxiliar;
	private String tipodocumento;
	
	private boolean captchaVisible = false;
	
	//private static String VARIABLE_CAPTCHA="realPerson";
	


	private String messageAlertFood = "";
	private String messageFinal = "";

	private String varTipoDocumento = null;
	private String varUsuarioDef = null;
	private String varTipoMsgNotif = null;
	
	//NO USADO
	//private org.primefaces.model.StreamedContent imageCaptcha;	
	private boolean logeado = false;
	private String flagLogin = "NO";	
	private boolean cargarCaptcha = true;
	
	private Integer idUsuarioTemp;
	private String tokenSesion;
	
	//PARAM SECURITY AUTH
	private String paramSecurityAuth = FacesUtil.getPropertyParametros("PARAM_ENABLED_SECURITY_AUTH");
	
 	
	@PostConstruct
	public void init(){
		claveAuxiliar = null;
		email=null;
		clave=null;			
		idUsuarioTemp = null;
		cargarParametrosSystem("",true);
		captchaVisible=false;
	}
	
	/**Evento de inicialización de los valores de la página del Login*/
	public void preRender(){
		System.out.println("INICIO PRE RENDER LOGIN ...");		
		if(cargarCaptcha){	
			claveAuxiliar = null;
			email=null;
			clave=null;						
			cargarCaptcha=false;
		}		
		
		//cargarParametrosURL();
		///DESTRUIR BEAN PRINCIPAL (para recargar las opciones de seguridad)
		//ManejadorSpringBean.destruirScope(applicationContext, "bandejaEducativoBeans");
	}  	    
	
	/**De recibir parámetros por la URL, se recepciona en este método*/
	public void cargarParametrosURL(){
		try{	
			if(varTipoDocumento!=null){
				tipodocumento = varTipoDocumento;
			}
			if(varUsuarioDef!=null){						
				email = varUsuarioDef;
				//GET CLAVE
			
			}
			messageAlertFood = "";
			if(varTipoMsgNotif!=null){
				if(varTipoMsgNotif.equals("A")){
					//NUEVA CUENTA
					messageAlertFood = FacesUtil.getMSJProperty("MSJ_INFO_CUENTACREADA_VERCORREO");
				}else if(varTipoMsgNotif.equals("B")){
					//CUENTA EXISTENTE
					messageAlertFood = FacesUtil.getMSJProperty("MSJ_INFO_CUENTAEXISTENTE_VERCORREO");					
				}			
			}
			
			
		}catch(Exception ex){
			
		}
	}
	/**Evento de previo a la autenticación. Esto incluye las validaciones neecsarias.*/
	public void btnPreLogin(){
		try{				
			logeado = validacionLogin(this.getEmail(), this.getClave());
			if(logeado){
				RequestContext.getCurrentInstance().execute("doLogin();");
				messageAlertFood = null;
				claveAuxiliar = this.getClave();
			}									
		}catch(Exception ex){
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("estaLogeado", false);
			FacesUtil.adicionarMensajeError("Error Inesperado");
			ex.printStackTrace();
		}
	}
	/**Método que indica si el usuario está logueado correctamete (Invocado desde JS)*/
	public boolean estaLogeado() {
		return logeado;
	}

	/**Método correspondiente a la concretización del Login. Llamar al Servlet de Seguridad con el Spring Security*/	
	public String doLogin() throws IOException, ServletException {					
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		String paramSusces = Utiles.NO_db;
		if(logeado){
		//if(true){
			 paramSusces = Utiles.SI_db;	
		}		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check?j_username=" + this.getEmail()
						+ "&j_password=" + claveAuxiliar
						+ "&j_loginsuccessful=" + paramSusces
						+ "&j_loginidusuario=" + idUsuarioTemp
						+ "&j_tokenSesion=" + tokenSesion						
						);
		try{
		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();			
		}catch(Exception ex){
			ex.printStackTrace();			
		}
		
		this.setEmail(null);
		return null;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	
	/****AGREGADOS****/
	
	private SysUsuario usuarioSinLogin = null;
	Map<String,String> mapParametrosSys = new HashMap<String,String>();
	
	/**evento al cambio del USARIO*/
	public void eventoCambioUsuario() {		
		try{		
			/**Por implementar*/
			messageAlertFood="";			
			//////////////			
			/*
			usuarioSinLogin=null;
			if (email!= null && !"".equals(email)) {
				Usuario objFiltro = new Usuario();
				objFiltro.setUsuario(email);
				usuarioSinLogin =  usuarioService.obtenerPorId(objFiltro);	
			}														
			eventoValidarCaducidad();*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	/**validación de la caducidad de la cuenta de USUARIOS EXTERNOS*/
	public void eventoValidarCaducidad() {		
		try{		
			/**Por implementar*/
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**RECUPERACIóN DE CLAVE*/
	public void btnRecuperarClave() {		
		if (email != null && !"".equals(email)) {
			if(usuarioSinLogin!=null){
				//if(usuarioSinLogin.getPersona()!=null){				
//				if(false){//OBS::
//					//si es EXTERNO envío para el correo del USUARIO
//					/**#A.1 .- obtenemos la persona del usuario y el correo asociado con esta persona*/			
//				}else{				
//				}
			}else{
				FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_noUserValido"));				
			}
		} else {
			FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_noUserIngresado"));
		}
	}
	
	
	
	/**Generar la nueva clave (IMPLE ALGORITMO) y obtenerla*/
	/*public String getClaveNuevaGenerada(String indica){			
		String clave =encriptaKeyService.getKeyGenerado_Cifrado(false);;
		///
		return clave;
	}*/
	
	/** Set parámetros generales del SISTEMA a la SESIÓN ACTUAL*/
	public void setParametrosSystem(boolean indicaCarga){
		if(indicaCarga && mapParametrosSys!=null){
			EntidadSession.getInstance().setMapPropParametros(mapParametrosSys);
		}
	}
	/** Cargar parámetros generales del SISTEMA*/
	public void cargarParametrosSystem(String tipo, boolean indicaCarga){
		if(indicaCarga){
			/***** OBS:  TRAER PARAMETROS DE LA BASE DE DATOS ******/
			mapParametrosSys = new HashMap<String,String>();
			if(true){				
				/******/				
			}			
		}
	}

	public String getFlagLogin() {
		return flagLogin;
	}

	public void setFlagLogin(String flagLogin) {
		this.flagLogin = flagLogin;
	}
	/**ADD  VALIDACIONES DEL PRE LOGIN*/
	public boolean validacionLogin(String users,String clave){		
		SysUsuario login = validarPreAcceso(users,clave);
		boolean msgSoloAdvertencia = true;
		if ( login!= null) {			
			if(validarCaptcha()){
				//LOGICA ADICIONAL
				logeado = true;
				if(logeado){
					idUsuarioTemp = login.getUsuaId(); 
					tokenSesion = login.getTokenSecurity()!=null?login.getTokenSecurity():"";
					messageFinal = FacesUtil.getMSJProperty("MSJ_INFO_loginCorrecto") +" " +login.getUsuaNombre();									
					//return logeado;
				}
			}else{
				logeado = false;		
				msgSoloAdvertencia = false;
				messageFinal = FacesUtil.getMSJProperty("MSJ_ERROR_login_LDAP_CAPTCHA_noCodigo");					
			}	
		} else {
			logeado = false;	
			msgSoloAdvertencia = false;			
			messageFinal = FacesUtil.getMSJProperty("MSJ_WARN_loginIncorrecto");
		}	
		if(logeado){
			FacesUtil.adicionarMensajeInfo(messageFinal);
		}else{
			if(msgSoloAdvertencia){
				FacesUtil.adicionarMensajeWarning(messageFinal);
			}else{
				FacesUtil.adicionarMensajeError(messageFinal);
			}
		}
		return logeado;
	}
	

	
	/** Obtener el USUARIO DE ACCESO*/
	public SysUsuario validarPreAcceso(String usuario, String clave) {		
		Optional<SysUsuario> optUser = Optional.empty();
		try{
			if(clave==null){
				clave ="";
			}	
			if(Constant.SI_db.equals(paramSecurityAuth)){
				//Seguridad del API REST AUTH
				UserCredentials userCred = new UserCredentials();
				userCred.setUsername(usuario);
				userCred.setPassword(clave);			
				optUser = usuarioService.obtenerLoginAuth(userCred);
			}else{
				//login DB normal
				optUser = usuarioService.obtenerLogin(usuario,clave);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		return optUser.isPresent() ? optUser.get() : null;		
	}
	/**Método que indica si el usuario está logueado correctamete (Invocado desde JS)*/
	public boolean setIntentosLoginUsados(SysUsuario userLogin, boolean loginSucces) {		
		boolean result =false;
		if(userLogin!=null){
			if(!loginSucces){

			}	
		}
		return result;
	}

	
	/**Método que indica si el usuario está logueado correctamete (Invocado desde JS)*/
	public boolean validarIntentosPermitidos(SysUsuario login) {
		boolean result =true;
		String parIntPermitidos =  mapParametrosSys.get(FacesUtil.getPropertyParametros("PAR_LONGIN_INTENTOSPERM"));
		if(parIntPermitidos!=null){
			//if(Utiles.BLOQUEADO_db.equals((""+login.getSqllogin()).trim())){
			//	result =false;
		//	}
		}
		return result;
	}
	/**método para validar vía WEB SERVICE o otro recurso EXTERNO el usuario con el usuario del Active Directory del dominio*/
	public boolean validarUsuarioInternoActiveDirectory(SysUsuario objValidar,String claveIngresada) {
		boolean result = true;		
		if(Constant.ACTIVO_db.equals(objValidar.getUsuaEstado())){
			//SERVICIO PARA UTILIZAR ACTIVE DIRECTORY

		}else{
			result = false;			
			messageFinal = FacesUtil.getMSJProperty("MSJ_WARN_login_UsuarioNoActivo");
		}				
		return result;
	}

	/****************************************/
	
	
	public String getMessageAlertFood() {
		return messageAlertFood;
	}

	public void setMessageAlertFood(String messageAlertFood) {
		this.messageAlertFood = messageAlertFood;
	}
	
	
	
	/**********CAPTCHA***************/
	public boolean validarCaptcha() {
		boolean result =  false;
		if(captchaVisible){			
			/**OTRA OPCION*/
			/*if (captcha.isValidCaptcha()) {				
				return true;
			}*/			
		}else{
			result =  true;
		}
		return result;
	}

	
	public boolean isCaptchaVisible() {
		return captchaVisible;
	}

	public void setCaptchaVisible(boolean captchaVisible) {
		this.captchaVisible = captchaVisible;
	}
	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}



	public String getVarTipoDocumento() {
		return varTipoDocumento;
	}

	public void setVarTipoDocumento(String varTipoDocumento) {
		this.varTipoDocumento = varTipoDocumento;
	}

	public String getVarUsuarioDef() {
		return varUsuarioDef;
	}

	public void setVarUsuarioDef(String varUsuarioDef) {
		this.varUsuarioDef = varUsuarioDef;
	}

	public String getVarTipoMsgNotif() {
		return varTipoMsgNotif;
	}

	public void setVarTipoMsgNotif(String varTipoMsgNotif) {
		this.varTipoMsgNotif = varTipoMsgNotif;
	}


	public String getClaveAuxiliar() {
		return claveAuxiliar;
	}

	public void setClaveAuxiliar(String claveAuxiliar) {
		this.claveAuxiliar = claveAuxiliar;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}


}
