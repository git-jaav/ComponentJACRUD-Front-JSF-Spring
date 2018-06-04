package pe.jaav.sistemas.seguridadweb.controller.seguridad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import pe.jaav.sistemas.seguridadweb.controller.AbstractGenericManagedBean;
import pe.jaav.sistemas.seguridadweb.controller.InterfaceGenericManagedBean;
import pe.jaav.common.util.UtilesCommons;
import pe.jaav.common.util.model.PaginacionModel;
import pe.jaav.common.util.model.ResultTx;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.util.Constant;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;


@ManagedBean(name = "sysUsuarioMBean")
@ViewScoped
public class SysUsuarioMBean  extends AbstractGenericManagedBean implements InterfaceGenericManagedBean {

	public final String DIALOG_PERSONA ="dlgPersona";
	
	
	@ManagedProperty(value="#{usuarioService}")
	private UsuarioService usuarioService;
	
	private static final long serialVersionUID = 1L;

	private boolean chkEstadoBoolean;
	private boolean chkUsuarioBoolean;
	
	private String claveRepeticion;
	private boolean compoCaducaVisible;		
	
	private SysUsuario perfilUsuarioFiltro = new SysUsuario();
	private SysUsuario usuarioSeleccionado = new SysUsuario();
	private SysUsuario registroUsuario = new SysUsuario();
	private LazyDataModel<SysUsuario> listaModelPrincipal;
	private List<SysUsuario> listaTablaPrincipal;
	

	@PostConstruct
	public void inicializar() {
		super.initComponentes();
		inicializarLista();
		inicializarEntidades();
		inicializarDatos();
		buscarPrincipal();
	}
	
	
	public void inicializarEntidades(){
		perfilUsuarioFiltro = new SysUsuario();
		usuarioSeleccionado = new SysUsuario();
		registroUsuario = new SysUsuario();
	}
	
	public void inicializarLista(){
		listaTablaPrincipal = new ArrayList<SysUsuario>();
	}
	
	public void inicializarDatos(){
		perfilUsuarioFiltro.setUsuaEstado(Constant.ACTIVO_db);		
		//Valores boolean
		claveRepeticion=null;
		compoCaducaVisible=false;
	} 

	@Override
	public void btnBuscar() {
		buscarPrincipal();
	}

	public boolean esValidoFormulario(SysUsuario objSave) {
		boolean esValidoFormulario = true;
		if( UtilesCommons.esVacio(objSave.getUsuaUsuario()) ){
			FacesUtil.adicionarMensajeWarning( FacesUtil.getMSJProperty("MSJ_INFO_WARN_USUARIO_REQUIRED_USUARIO"));
			esValidoFormulario = false;
		}
		
		if(!MODO_DELETE.equals(MODO_ACTUAL) && !MODO_INACTIVAR.equals(MODO_ACTUAL)){
			/*if(objSave.getPersona()==null 
					&& Utiles.CODIGO_USUARIO.equals(objSave.getUsuarioPerfil())){
				esValidoFormulario=false;
				FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_USAURIO_noSeleccPersona"));
			}*/		
			if (!((objSave.getUsuaClave()!=null?objSave.getUsuaClave():"")+"").equals(claveRepeticion!=null?claveRepeticion:"")) {
				esValidoFormulario=false;
				FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_noCoincidenClaveNueva"));						
			}
		

			//VALIDAR REPETICION
			if(MODO_NEW.equals(MODO_ACTUAL)){
				Optional<SysUsuario> objResultValid = usuarioService.obtenerPorID(objSave.getUsuaId());
				if(objResultValid.isPresent()){
					FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_USUARIO_codUsuarioRepetido"));
					
				}				
			}

		}
		return esValidoFormulario;
	}
	
	@Override
	public void btnGuardar() {	
		String onSuccesMsg = "";
		ResultTx<SysUsuario> result = ResultTx.error(registroUsuario,UtilesCommons.TYPE_COD_NULL);		
		if(esValidoFormulario(registroUsuario)){				
			SysUsuario usuario = transformarEntidad(registroUsuario);
			
			if (MODO_ACTUAL.equals(MODO_NEW) ) {
//				/**CIFRADO****************/
//				String keyClave = encriptaKeyService.getCifrado(usuario.getClave());					
//				usuario.setClave(keyClave);				
				result = usuarioService.guardar(usuario);
				onSuccesMsg = FacesUtil.getMSJProperty("MSJ_INFO_saveCorrecto");
				/*************************/
			}else if (MODO_ACTUAL.equals(MODO_UPDATE) ) {				
//				/**CIFRADO****************/
//				String keyClave = encriptaKeyService.getCifrado(usuario.getClave());					
//				usuario.setClave(keyClave);				
				result = usuarioService.actualizar(usuario);
				onSuccesMsg = FacesUtil.getMSJProperty("MSJ_INFO_saveCorrecto");
				/*************************/
			} else if (MODO_ACTUAL.equals(MODO_DELETE) ) {
				/**OBS:ELIMINAR*/
				/***********/
				result = usuarioService.eliminar(usuario);
				onSuccesMsg = FacesUtil.getMSJProperty("MSJ_INFO_saveCorrecto");
			} 			
			if (result.isOk()) {											
				btnCancelar();
				btnBuscar();
				FacesUtil.adicionarMensajeInfo(onSuccesMsg);
			} else {
				FacesUtil.adicionarMensajeError(FacesUtil.getMSJProperty("MSJ_ERROR_noSaveCorrecto"));
			}		
		}
	}
	
	public SysUsuario transformarEntidad(SysUsuario objSave){							
		objSave.setUsuaUsuariomodif(EntidadSession.getInstance().getUsuario());
		objSave.setUsuaFechamodif(new Date());
		return objSave;
	}
	
	

	@Override
	public void btnAceptar() {
		visibleRegistro=false;
		registroUsuario = new SysUsuario();
		usuarioSeleccionado = new SysUsuario();
		
	}

	@Override
	public void btnCancelar() {
		visibleRegistro=false;
		registroUsuario = new SysUsuario();
		usuarioSeleccionado = new SysUsuario();
	
	}

	@Override
	public void btnNuevo() {
		MODO_ACTUAL = MODO_NEW;		
		setAtributosWindowsRegistro(MODO_ACTUAL);
	}

	
	
	@Override
	public void btnModificar() {
		if (usuarioSeleccionado != null) {			
			MODO_ACTUAL = MODO_UPDATE;			
			setAtributosWindowsRegistro(MODO_ACTUAL);
		} else {
			FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_elementoNoSelecc"));
		}
	}

	@Override
	public void btnVer() {
		if (usuarioSeleccionado != null) {			
			MODO_ACTUAL = MODO_VIEW;			
			setAtributosWindowsRegistro(MODO_ACTUAL);
		} else {
			FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_elementoNoSelecc"));
		}
	}

	@Override
	public void btnEliminar() {
		if (usuarioSeleccionado != null) {			
			MODO_ACTUAL = MODO_DELETE;		
			setAtributosWindowsRegistro(MODO_ACTUAL);
		} else {
			FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_elementoNoSelecc"));
		}
	}

	public void setAtributosWindowsRegistro(String modo) {
		super.setAtributosWindowsRegistro(modo);		
		claveRepeticion=null;
		if (MODO_NEW.equals(MODO_ACTUAL)) {					
			registroUsuario = new SysUsuario();			
			registroUsuario.setUsuaEstado(Constant.ACTIVO_db);						
			
		} else {
			registroUsuario = usuarioSeleccionado;
			
			claveRepeticion=registroUsuario.getUsuaClave();
			
//			/**DESCIFRAR CLAVE*/
//			String keyClaveDescifrada = encriptaKeyService.getDescifrado((registroUsuario.getClave()+"").trim());					
//			registroUsuario.setClave(keyClaveDescifrada);							
			/**************/
			//claveRepeticion=keyClaveDescifrada;
		} 
	
	}	
 
	
	@Override
	public void abrirRegistro() {		
		//abrirRegistroWindow(MODO_ACTUAL, DIALOG_REGISTRO_USUARIO_PERFIL, optionsWindowPrincipal, null);
	}

	/* (non-Javadoc)
	 * @see pe.jaav.sistemas.seguridadweb.controller.InterfaceGenericManagedBean#cargarObjetoFiltros(int, int, int)
	 */
	@Override
	public void cargarObjetoFiltros(int first, int pageSize, int counter) {
		if (perfilUsuarioFiltro == null) {
			perfilUsuarioFiltro = new SysUsuario();
		}
		perfilUsuarioFiltro.setInicio(first);
		perfilUsuarioFiltro.setNumeroFilas(pageSize);
	}

	/**Lisatr los elementos del DATATABLE
	 * @return
	 */
	public LazyDataModel<SysUsuario> buscarPrincipal() {		
		listaModelPrincipal = new LazyDataModel<SysUsuario>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<SysUsuario> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				cargarObjetoFiltros(first, pageSize, 0);
				PaginacionModel<SysUsuario> paginacionModel = usuarioService.listar(perfilUsuarioFiltro);				
				listaTablaPrincipal = paginacionModel.getListaElementos();
				setRowCount(paginacionModel.getNroTotalElementos());
				setPageSize(pageSize);
				setRowIndex(first);
				return listaTablaPrincipal;
			}

			@Override
			public SysUsuario getRowData(String rowKey) {
				for (SysUsuario event : listaTablaPrincipal) {
					if (event.getUsuaId().equals(rowKey)) {
						usuarioSeleccionado = (SysUsuario) event;
						return usuarioSeleccionado;
					}
				}
				return null;
			}

			@Override
			public Object getRowKey(SysUsuario event) {
				return event.getUsuaId();
			}
		};
		return listaModelPrincipal;
	}

	
	/** iniciara e evento de modificar con un elemento
	 * @param elem
	 */
	public void btnModificarElem(SysUsuario elem) {
		usuarioSeleccionado = elem;
		btnModificar();
	}
	
	/** iniciara e evento de ver con un elemento
	 * @param elem
	 */
	public void btnVerElem(SysUsuario elem) {
		usuarioSeleccionado = elem;
		btnVer();		
	}

	/** iniciara e evento de eliminar con un elemento
	 * @param elem
	 */
	public void btnEliminarElem(SysUsuario elem) {
		usuarioSeleccionado = elem;
		btnEliminar();		
	}
	
	/**
	 * Accion invocada al Abrir un Dialog de selecci�n: Permite establecer el
	 * evento de retorno del Bean propio del Seleccionador
	 */
	public void setSeleccionEvento_Personamast() {		
		try {		
//			
//			/** (1:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTOS A LIMPIAR */
//			Personamast obj1 = (Personamast) getGenericObjectBean("dataSeleccionadorPersonaBean","personaEntidadSeleccionFiltro", Personamast.class);			
//			obj1.setNombreCompleto(null);		
//			/**********SET FILTROS*******/
//			//obj1.setEsCliente(UtilesCommons.SI_db);
//			obj1.setEsEmpleado(Constant.SI_db);
//			obj1.setTipoPersona(Constant.PERSONA_TIPOPERSONA_NATURAL);
//			obj1.setEstado(Constant.ACTIVO);		 
//			obj1.setAccion(DataSeleccionadorPersonaBean.TIPOCONSULTA_PERSONAS);
//			//OBS DIFERENCIAR TIPO DE USUARIO
//			obj1.setDialogVar("dlg");
//			/************************************************/
//
//			/** (2:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTO EVENTO */
//			
//			EventosBean obj = getGenericEventoBean("dataSeleccionadorPersonaBean", "eventoSeleccion");
//			
//			if (obj != null) {
//				obj.setEventoSeleccionPersonaGeneral(new EventosBean.EventoSeleccionPersonaGeneral() {
//					@Override
//					public void retornoSeleccion(Personamast personamast, EmpleadoMast empleado, String mensaje) {
//						eventoSeleccionPersona2(personamast,empleado);	
//					} 
//				});		
//				
//				FacesUtil.execute("inicializarBPersona();");
//				FacesUtil.showDialogPrimefaces(DIALOG_PERSONA);				
//			} 
//			else {				
//			}
			
			/************************************************/
			
		} catch (Exception ex) {
			FacesUtil.mensajeErrorAbrirBuscador();
		}
	}

//	/**Evento post Selecci�n de la persona (Set campos en el formulario)*/
//	public void eventoSeleccionPersona2(Personamast seleccion,EmpleadoMast seleccionEmpleado) {		
//		if (seleccion != null) {
//			if(seleccion.getDocumento()!=null){
//				registroUsuario.setPersona(seleccion.getPersona());
//				registroUsuario.setNombre(seleccion.getNombreCompleto());
//				
//				if( MODO_ACTUAL.equals(MODO_NEW) && seleccionEmpleado!=null){
//					if(seleccionEmpleado.getCodigoUsuario()!=null){
//						registroUsuario.setUsuario(seleccionEmpleado.getCodigoUsuario());	
//					}				
//				}
//				
//				FacesUtil.update("FormUsuario:pgPersona","FormUsuario:pgUsuario");
//				FacesUtil.hideDialogPrimefaces(DIALOG_PERSONA);							
//						
//			}else{
//				FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_USAURIO_noSeleccPersonaConDocum"));
//			}
//		} else {
//			FacesUtil.adicionarMensajeWarning(FacesUtil.getMSJProperty("MSJ_WARN_USAURIO_noSeleccPersona"));			
//		}
//	}


	
	/** evento al seleccionar un elemento del datatable
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {		
	}	
	
	
	public void eventocheckboxCaduca() {		
	}	
	
	
	/************TEST REPORTE***********/
	/**
	 * PRUEBA DE SET DEL CONTENEDOR REPORTE
	 */
//	public void setContenedor_Reporte() {
//		String varDialog="";
//		Map<String, Object> parametros = new HashMap<String, Object>();
//		//parametros.put(Utiles.COD_REPORTE_SERVLET,Utiles.COD_REPORTE_USUARIO);						
//		//LLAMAR A CONTNEDOR GENERAL
//		//FacesUtil.setContenedor_ReporteGen(parametros,"FormUsuario",varDialog,"",Utiles.FORMATO_PDF,"600",false);	
//	}		
	
	
	
	/******* GETTERS AND SETTERS *******/
	public boolean isChkEstadoBoolean() {
		return chkEstadoBoolean;
	}

	public void setChkEstadoBoolean(boolean chkEstadoBoolean) {
		this.chkEstadoBoolean = chkEstadoBoolean;
	}

	public boolean isChkUsuarioBoolean() {
		return chkUsuarioBoolean;
	}

	public void setChkUsuarioBoolean(boolean chkUsuarioBoolean) {
		this.chkUsuarioBoolean = chkUsuarioBoolean;
	}

	public SysUsuario getPerfilUsuarioFiltro() {
		return perfilUsuarioFiltro;
	}

	public void setPerfilUsuarioFiltro(SysUsuario perfilUsuarioFiltro) {
		this.perfilUsuarioFiltro = perfilUsuarioFiltro;
	}

	public LazyDataModel<SysUsuario> getListaModelPrincipal() {
		return listaModelPrincipal;
	}

	public void setListaModelPrincipal(LazyDataModel<SysUsuario> listaModelPrincipal) {
		this.listaModelPrincipal = listaModelPrincipal;
	}

	public List<SysUsuario> getListaTablaPrincipal() {
		return listaTablaPrincipal;
	}

	public void setListaTablaPrincipal(List<SysUsuario> listaTablaPrincipal) {
		this.listaTablaPrincipal = listaTablaPrincipal;
	}

	public SysUsuario getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(SysUsuario usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}


	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public SysUsuario getRegistroUsuario() {
		return registroUsuario;
	}

	public void setRegistroUsuario(SysUsuario registroUsuario) {
		this.registroUsuario = registroUsuario;
	}

	public String getClaveRepeticion() {
		return claveRepeticion;
	}

	public void setClaveRepeticion(String claveRepeticion) {
		this.claveRepeticion = claveRepeticion;
	}

	public boolean isCompoCaducaVisible() {
		return compoCaducaVisible;
	}

	public void setCompoCaducaVisible(boolean compoCaducaVisible) {
		this.compoCaducaVisible = compoCaducaVisible;
	}
}

