package pe.jaav.sistemas.seguridadweb.configuracion;

import java.io.Serializable;
import java.util.Date;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pe.jaav.common.util.UtilesCommons;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;
import pe.jaav.sistemas.seguridadweb.util.Utiles;

/**
 * @author araucoj
 *
 */
@ManagedBean(name = "panelPrincipalMBean")
@ViewScoped
public class PanelPrincipalMBean  implements Serializable {

	private static final long serialVersionUID = 1L;		


	
	/**variables Show*/
	
	private String fechaShow;
	private String nombreUsuarioShow;
	private String equipoActualShow;
	private String lbFechaPrincipal;
	
	private String fechaBuildShow;
	
	/********/
	
	/***Variables EXTRA*****/	

	

	public void cargarFiltroCombosBienesRecursos() {

	}

	@PostConstruct
	public void inicializar() {			
		//eventoCargarPermisosMenu();
	}

	/**
	 * Cargar los permisos para el MENU
	 */
	public void eventoCargarPermisosMenu() {

	}

	public String getFechaShow() {		
		
		fechaShow = UtilesCommons.formatoFechaString(new Date(), Utiles.FORMA_DATECOMPLETA);				
		return fechaShow;
	}

	public void setFechaShow(String fechaShow) {
		this.fechaShow = fechaShow;
	}

	public String getNombreUsuarioShow() {
		if(EntidadSession.getInstance()!=null){
			nombreUsuarioShow = EntidadSession.getInstance().getNombreUsuario();
		}					
		return nombreUsuarioShow;
	}

	public void setNombreUsuarioShow(String nombreUsuarioShow) {
		this.nombreUsuarioShow = nombreUsuarioShow;
	}

	public String getEquipoActualShow() {
		if(EntidadSession.getInstance()!=null){
			equipoActualShow = EntidadSession.getInstance().getPcNameCliente();
		}
		return equipoActualShow;
	}

	public void setEquipoActualShow(String equipoActualShow) {
		this.equipoActualShow = equipoActualShow;
	}
	/**Obtener la vista de la fecha con un formato completo
	 * @return
	 */
	public String getLbFechaPrincipal() {
		String FechaFormato = UtilesCommons.formatoFechaString(new Date(), UtilesCommons.FORMA_DATECOMPLETA);
		lbFechaPrincipal = FechaFormato;
		return lbFechaPrincipal;
	}

	public void setLbFechaPrincipal(String lbFechaPrincipal) {
		this.lbFechaPrincipal = lbFechaPrincipal;
	}

	public String getFechaBuildShow() {
		fechaBuildShow = FacesUtil.getPropertyBuildInfo("build.timestamp");
		return fechaBuildShow;
	}

	public void setFechaBuildShow(String fechaBuildShow) {
		this.fechaBuildShow = fechaBuildShow;
	}


}


