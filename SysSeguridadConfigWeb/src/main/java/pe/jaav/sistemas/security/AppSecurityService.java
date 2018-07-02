package pe.jaav.sistemas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pe.jaav.common.util.UtilesCommons;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.server.InicializaSession;
import pe.jaav.sistemas.seguridadweb.util.Constant;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;
import pe.jaav.sistemas.seguridadweb.util.Utiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author JAAV
 *
 */
public class AppSecurityService implements UserDetailsService {

	/***********/
	//private static String MISMA_PAGINA = "/login.xhtml?faces-redirect=true";
	private boolean logeado = false;
	/***********/
	
	@Autowired
	private UsuarioService usuarioService;	
	
	Map<String, String> mapParametrosSys = new HashMap<String, String>();

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		String users = (String) req.getParameter("j_username");
		String clave = (String) req.getParameter("j_password");
		String success = (String) req.getParameter("j_loginsuccessful"); // PAR�?METRO SET POR LA VALIDACIÓN		
		String idUsuario =  (String) req.getParameter("j_loginidusuario"); 
		String tokenSesion =  (String) req.getParameter("j_tokenSesion");
		
		logeado = Utiles.SI_db.equals(success) ? true : false;


		UsuarioSistema user = null;
		//String paginaSgt = MISMA_PAGINA;

		if (logeado) {// SOLO SI LA VALIDACIÓN ES CORRECTA			
			SysUsuario objAcceso = new SysUsuario();
			objAcceso.setUsuaUsuario(users);			
			SysUsuario login =getUsuarioGeneral(idUsuario,users,clave,Constant.DB_CODE,tokenSesion);							
			GenerarAcceso(login,tokenSesion);								
			/************************************/
			/** SI LAS VALIDACIONES SON CORRECTAS: forzar al SPRING SECURITY */								
			login.setUsuaClave(clave);			
			user = new UsuarioSistema(login, getRoles(login));
		}
		return user;
	}

	/** Establecer el Token para la sesion (si existiera)
	 * @param token
	 * @return
	 */
	public boolean setSesionComponent(String token){
		boolean result = true;
		if(UtilesCommons.noEsVacio(token)){
			EntidadSession.getInstance().setTokenSecurity(token);
		}
		return result;
	}
	
	private Collection<? extends GrantedAuthority> getRoles(SysUsuario login) {

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ADMINISTRADOR"));
		/*
		 * for(RolLogin rol : login.getRoles()){ authorities.add(new
		 * SimpleGrantedAuthority(rol.getNombre().toUpperCase())); }
		 */
		return authorities;
	}

	
	/** Get usuario para la Sesion
	 * @param id
	 * @param usuario
	 * @param clave
	 * @param indica
	 * @return
	 */
	public SysUsuario getUsuarioGeneral(String id,String usuario, String clave,String indica, String tokenSesion) {	
		try{						
			if(Constant.DB_CODE.equals(indica)){
				Integer idUser  = Integer.parseInt(id);
				Optional<SysUsuario> obtUser = usuarioService.obtenerPorID(idUser,
						tokenSesion);
				return obtUser.isPresent() ? obtUser.get() : null;				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/** Generar Accesos */
	public void GenerarAcceso(SysUsuario objAcceso,String tokenSesion) {
		try{		
			/**PARA EL LOGIN DEL SISTEMA */
			Integer numPersona = 0;
			EntidadSession global = new EntidadSession();			
			global.setUsuario(objAcceso.getUsuaUsuario());//USUARIO SYS		
			
			/* DATOS GENERALES*/
			global.setNombreUsuario(objAcceso.getUsuaNombre());
			global.setAplicacionCodigo(Constant.APLICACION_CODIGO); // OBS HARD CODE
			global.setHostActual(Utiles.HOST_APP); // OBS HARD CODE
			global.setFechaSessionActual(UtilesCommons.getFechaHoy());	
			global.setPersona(objAcceso.getPersonaId());
			/* DATOS DE PERSONA/EMPLEADO*/							
			
			//global.setNombreCompleto("SIN PERSONA");
			/***** SET PARAMETROS CLIENTE *****/
			InicializaSession.InitGlobal(global);						
			/** CARGAR VALORES ... **/
			setParametrosSystem(true, null);
			setParametrosCliente();			
			setSesionComponent(tokenSesion);
			if (numPersona>0){
				//EntityGlobal.getInstance().setEmpleadoID(numPersona);
			}						
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/** Set parámetros generales del SISTEMA a la SESIÓN ACTUAL */
	public void setParametrosSystem(boolean indicaCarga, String companiaCodigo) {
		try{	
			EntidadSession.getInstance().setLocaleConfigActual(FacesUtil.getContextLocale());			
			if(indicaCarga){
				//AGREGAR LOS CÓDIGOS DE PAR�?METROS QUE QUEREMOS CARGAR EN SESIÓN				
				/**Parámetro para la validación o no de la terminal del CAJERO*/				
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
	
	
	/** Establece los valores de Alfresco para la Sesión, de acuerdo al usuario Logueado
	 * debe asegurarse su uso, luego de que exista la instancia para nuestro objeto de sesión : EntityGlobal

	 * @param indicaUserActual
	 */
	public void setParametrosAlfresco(String usuario,String clave, boolean indicaUserActual) {
		try{			
			if(indicaUserActual){
				/*String objTicket = cmisAlfrescoService.getTicket(usuario,clave);
				if(objTicket!=null){

				}	*/
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/** Set parámetros generales del CLEINTE la SESIÓN ACTUAL */
	public void setParametrosCliente() {
		if (EntidadSession.getInstance() != null) {
			EntidadSession.getInstance().setIpCliente(Utiles.getIPCliente());
			EntidadSession.getInstance().setPcNameCliente(
					Utiles.getPcNombreCliente());
			EntidadSession.getInstance().setMacCliente(Utiles.getMacCliente());
			EntidadSession.getInstance().setSysVersion(
					FacesUtil.getPropertyLabelsVistas("VERSION"));
			// URL
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			String url = "";
			if (request != null) {
				url = request.getRequestURL().toString();
				EntidadSession.getInstance().setSysUrl(url);
			}
		}
	}



	public boolean esNecesarioCambioClave(SysUsuario objValidar,
			String claveIngresada) {
		boolean result = false;
		/*if (Utiles.EST_CAMBIAR_db.equals(objValidar.getSqlpassword())) {
			result = true;
		}*/
		return result;
	}

	
	/**
	 * Cargar los accesos de seguridad que posee el usuario y set en la Sesión
	 * (Si poseyera menos de dos opciones)
	 */
	public void cargarAccesosDeSeguridad(SysUsuario objAcceso) {
		try{
			// SET DE PREFERNCIAS
			//String codigoCompanyowner = null;
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
