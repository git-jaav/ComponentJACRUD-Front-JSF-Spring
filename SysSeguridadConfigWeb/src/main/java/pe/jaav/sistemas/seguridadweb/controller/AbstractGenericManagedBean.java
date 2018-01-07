package pe.jaav.sistemas.seguridadweb.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import pe.jaav.common.util.UtilesCommons;

//import com.lowagie.text.BadElementException;
//import com.lowagie.text.Chunk;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//
//import com.lowagie.text.Element;
//import com.lowagie.text.Font;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;

import pe.jaav.sistemas.seguridadweb.commons.EventosBean;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;
import pe.jaav.sistemas.seguridadweb.util.FacesUtil;
import pe.jaav.sistemas.seguridadweb.util.Utiles;



public abstract class AbstractGenericManagedBean  implements Serializable {
		
	public static String URL_DEFAULT=  FacesUtil.getPropertyParametros("PAR_PAGINA_DEFAULT_BLANK");
	
	/***/
	private String lenguaje;
	
	/** VARIABLES DEL SISTEMA */
	// public ResourceBundle propiedades = ResourceBundle.getBundle("mensajes");	
	
	/** VARIABLES DEL SISTEMA */
	// public ResourceBundle propiedades = ResourceBundle.getBundle("mensajes");

	private static final long serialVersionUID = 1L;

	//public Map<String, Object> optionsWindowPrincipal;
	
	//@Autowired
	//private UsuarioService usuarioService;

	public static String APP_CODIGO = "...";
	public static String USUARIO_ACTUAL = "...";
	public static String UNOMBRE_SUARIO_ACTUAL = "...";
	public static Long USUARIO_EMPLEADO_ID = new Long(0);
	/** MODOS */
	public static final String MODO_NEW = "Nuevo";
	public static final String MODO_UPDATE = "Editar";
	public static final String MODO_DELETE = "Eliminar";
	public static final String MODO_INACTIVAR = "Inactivar";
	public static final String MODO_VIEW = "Ver";
	public static String MODO_ACCION_MAESTRO = "Maestro";
	public static String MODO_CERRAR = "Cerrar";
	public static String MODO_ACCION = MODO_ACCION_MAESTRO;
	
	/** MENSAJES TIPOS **/
	public static String SUCCESS = "success";
	public static String ERROR = "error";
	public static String WARN = "warning";
	public static String INFO = "info";
	
	public String MODO_ACTUAL = MODO_NEW;
	// PARA MENSAJES POST WINDOW

	public String onSuccesMsg;
	public String onSuccesType;
	public boolean onSuccesIndica;

	public boolean btnBuscarVisible;
	public boolean btnAceptarVisible;
	public boolean btnCancelarVisible;
	public boolean btnNuevoVisible;
	public boolean btnModificarVisible;
	public boolean btnEliminarVisible;
	public boolean btnVerVisible;
	public boolean btnGuardarVisible;
	public boolean btnGuardarEliminarVisible;
	public boolean btnGuardarInactivarVisible;
	
	public boolean componentesReadonly;

	private boolean modoNew;
	private boolean modoUpdate;
	private boolean modoView;
	private boolean modoDelete;
	private boolean modoInactivate;
	
	public String paginaDefault;
	public String paginaActual;
	public String conceptoDescActual;
	
	public String grupoDefault;
	public String conceptoDefault;
	
	public boolean visibleRegistro;
	
	public Map<String,String>  mapPropSeguridad= new HashMap<String ,String>();
	private boolean evitarSeguridad;
	
	public String localeSession;
	
	public void initComponentes() {
	
		///////////		
		visibleRegistro = false;
		
		btnBuscarVisible = true;
		btnAceptarVisible = true;
		btnCancelarVisible = true;
		btnNuevoVisible = true;
		btnModificarVisible = true;
		btnEliminarVisible = true;		
		btnVerVisible = true;
		
		modoNew=false;
		modoView=false;
		modoUpdate=false;
		modoDelete=false;
		/////////////
		btnGuardarVisible = true;
		btnGuardarEliminarVisible=true;
		btnGuardarInactivarVisible=true;
		
		componentesReadonly = false;
		onSuccesMsg = "...";
		onSuccesType = Utiles.TYPE_MSG_INFO;
		onSuccesIndica = false;
		evitarSeguridad=false;		
	}
	
	

	
	public EventosBean getGenericEventoBean(String nombreBean, String nombreEvento) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ELContext el = facesContext.getELContext();
			Application app = facesContext.getApplication();
			ExpressionFactory ef = app.getExpressionFactory();
			ValueExpression ve = ef.createValueExpression(el, "#{" + nombreBean + "." + nombreEvento + "}",
					EventosBean.class);
			EventosBean obj = (EventosBean) ve.getValue(el);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Object getGenericObjectBean(String nombreBean, String nombreAtributo, Class<?> tipo) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ELContext el = facesContext.getELContext();
			Application app = facesContext.getApplication();
			ExpressionFactory ef = app.getExpressionFactory();
			ValueExpression ve = ef.createValueExpression(el, "#{" + nombreBean + "." + nombreAtributo + "}", tipo);
			Object obj = ve.getValue(el);
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
//	public <T> T getGenericObjectBeanCast(String nombreBean, String nombreAtributo, Class<T> tipo){
//		Object obj = getGenericObjectBean(nombreBean, nombreAtributo, tipo);
//		return UtilesCommons.castearObjeto(obj, tipo);
//	}
	
	public boolean evaluarSeguridad(String idObjecto){
		Map<String, String> seguridad = getMapPropSeguridad();
		if(seguridad.containsKey(idObjecto)){
			String rpta = seguridad.get(idObjecto);
			if(UtilesCommons.esVacio(rpta)){
				if(evitarSeguridad){
					return true;
				}else{
					return false;
				}
			}else{
				if(rpta.equalsIgnoreCase(Utiles.SI_db) || evitarSeguridad){
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(evitarSeguridad){
				return true;
			}else{
				return false;
			}
		}
		
	}

	public void setAtributosWindowsRegistro(String modo) {
		switch(modo){
			case MODO_VIEW 		: modoView(); break;
			case MODO_NEW  		: modoNew(); break;
			case MODO_UPDATE  	: modoUpdate(); break;
			case MODO_DELETE 	: modoDelete(); break;
			case MODO_INACTIVAR	: modoInactivate(); break;
		}
	}
		
	public void modoView(){
		visibleRegistro=true;
		componentesReadonly = true;
		btnGuardarVisible = false;
		btnAceptarVisible = true;
		btnCancelarVisible = false;			
		btnGuardarEliminarVisible=false;
		btnGuardarInactivarVisible=false;
		setModoView(true);
	}
		
	public void modoNew(){
		visibleRegistro=true;
		componentesReadonly = false;
		btnGuardarVisible = true;
		btnAceptarVisible = false;
		btnCancelarVisible = true;			
		btnGuardarEliminarVisible=false;
		btnGuardarInactivarVisible=false;
		setModoNew(true);	
	}
	
	public void modoUpdate(){
		visibleRegistro=true;
		componentesReadonly = false;
		btnGuardarVisible = true;
		btnAceptarVisible = false;
		btnCancelarVisible = true;			
		btnGuardarEliminarVisible=false;
		btnGuardarInactivarVisible=false;
		setModoUpdate(true);
	}
	
	public void modoDelete(){
		visibleRegistro=true;
		componentesReadonly = true;
		btnGuardarVisible = false;
		btnAceptarVisible = false;
		btnCancelarVisible = true;			
		btnGuardarEliminarVisible=true;
		btnGuardarInactivarVisible=false;			
		setModoDelete(true);
	}
	
	public void modoInactivate(){
		visibleRegistro=true;
		componentesReadonly = true;
		btnGuardarVisible = false;
		btnAceptarVisible = false;
		btnCancelarVisible = true;			
		btnGuardarEliminarVisible=false;
		btnGuardarInactivarVisible=true;			
		setModoInactivate(true);
	}	
	
//	public void setAtributosWindowsRegistro(String modo) {
//		if (MODO_VIEW.equals(modo)) {
//			visibleRegistro=true;
//			componentesReadonly = true;
//			btnGuardarVisible = false;
//			btnAceptarVisible = true;
//			btnCancelarVisible = false;			
//			btnGuardarEliminarVisible=false;
//			btnGuardarInactivarVisible=false;
//			setModoView(true);
//		} else if (MODO_NEW.equals(modo) || MODO_UPDATE.equals(modo)) {
//			visibleRegistro=true;
//			componentesReadonly = false;
//			btnGuardarVisible = true;
//			btnAceptarVisible = false;
//			btnCancelarVisible = true;			
//			btnGuardarEliminarVisible=false;
//			btnGuardarInactivarVisible=false;
//			if(MODO_UPDATE.equals(modo)){
//				setModoUpdate(true);
//			}else{
//				setModoNew(true);	
//			}			
//		} else if (MODO_DELETE.equals(modo) ) {
//			visibleRegistro=true;
//			componentesReadonly = true;
//			btnGuardarVisible = false;
//			btnAceptarVisible = false;
//			btnCancelarVisible = true;			
//			btnGuardarEliminarVisible=true;
//			btnGuardarInactivarVisible=false;			
//			setModoDelete(true);
//		}else if (MODO_INACTIVAR.equals(modo)) {
//			visibleRegistro=true;
//			componentesReadonly = true;
//			btnGuardarVisible = false;
//			btnAceptarVisible = false;
//			btnCancelarVisible = true;			
//			btnGuardarEliminarVisible=false;
//			btnGuardarInactivarVisible=true;			
//			setModoInactivate(true);
//		}
//	}



	public boolean isBtnBuscarVisible() {
		return btnBuscarVisible;
	}

	public void setBtnBuscarVisible(boolean btnBuscarVisible) {
		this.btnBuscarVisible = btnBuscarVisible;
	}

	public boolean isBtnAceptarVisible() {
		return btnAceptarVisible;
	}

	public void setBtnAceptarVisible(boolean btnAceptarVisible) {
		this.btnAceptarVisible = btnAceptarVisible;
	}

	public boolean isBtnCancelarVisible() {
		return btnCancelarVisible;
	}

	public void setBtnCancelarVisible(boolean btnCancelarVisible) {
		this.btnCancelarVisible = btnCancelarVisible;
	}

	public boolean isBtnNuevoVisible() {
		return btnNuevoVisible;
	}

	public void setBtnNuevoVisible(boolean btnNuevoVisible) {
		this.btnNuevoVisible = btnNuevoVisible;
	}

	public boolean isBtnModificarVisible() {
		return btnModificarVisible;
	}

	public void setBtnModificarVisible(boolean btnModificarVisible) {
		this.btnModificarVisible = btnModificarVisible;
	}

	public boolean isBtnEliminarVisible() {
		return btnEliminarVisible;
	}

	public void setBtnEliminarVisible(boolean btnEliminarVisible) {
		this.btnEliminarVisible = btnEliminarVisible;
	}

	public void setBtnVerVisible(boolean btnVerVisible) {
		this.btnVerVisible = btnVerVisible;
	}

	public boolean isBtnGuardarVisible() {
		return btnGuardarVisible;
	}

	public void setBtnGuardarVisible(boolean btnGuardarVisible) {
		this.btnGuardarVisible = btnGuardarVisible;
	}




	public boolean isComponentesReadonly() {
		return componentesReadonly;
	}

	
	public boolean isBtnGuardarEliminarVisible() {
		return btnGuardarEliminarVisible;
	}

	public void setBtnGuardarEliminarVisible(boolean btnGuardarEliminarVisible) {
		this.btnGuardarEliminarVisible = btnGuardarEliminarVisible;
	}

	public void setComponentesReadonly(boolean componentesReadonly) {
		this.componentesReadonly = componentesReadonly;
	}

	public String getOnSuccesMsg() {
		return onSuccesMsg;
	}

	public void setOnSuccesMsg(String onSuccesMsg) {
		this.onSuccesMsg = onSuccesMsg;
	}

	public String getOnSuccesType() {
		return onSuccesType;
	}

	public void setOnSuccesType(String onSuccesType) {
		this.onSuccesType = onSuccesType;
	}

	public boolean isOnSuccesIndica() {
		return onSuccesIndica;
	}

	public void setOnSuccesIndica(boolean onSuccesIndica) {
		this.onSuccesIndica = onSuccesIndica;
	}

	public static Long getUSUARIO_EMPLEADO_ID() {
		return USUARIO_EMPLEADO_ID;
	}

	public static void setUSUARIO_EMPLEADO_ID(Long uSUARIO_EMPLEADO_ID) {
		USUARIO_EMPLEADO_ID = uSUARIO_EMPLEADO_ID;
	}

	public static String getAPP_CODIGO() {
		return APP_CODIGO;
	}

	public static void setAPP_CODIGO(String aPP_CODIGO) {
		APP_CODIGO = aPP_CODIGO;
	}

	public static String getUSUARIO_ACTUAL() {
		//return USUARIO_ACTUAL;
		return EntidadSession.getInstance().getUsuario();
	}

	public static void setUSUARIO_ACTUAL(String uSUARIO_ACTUAL) {
		USUARIO_ACTUAL = uSUARIO_ACTUAL;
	}

	public static String getUNOMBRE_SUARIO_ACTUAL() {
		// UNOMBRE_SUARIO_ACTUAL = EntityGlobal.getInstance()
		// !=null?EntityGlobal.getInstance().getNombreCompleto():"Nombre";
		return UNOMBRE_SUARIO_ACTUAL;
	}

	public static void setUNOMBRE_SUARIO_ACTUAL(String uNOMBRE_SUARIO_ACTUAL) {
		UNOMBRE_SUARIO_ACTUAL = uNOMBRE_SUARIO_ACTUAL;
	}

	public boolean isModoUpdate() {
		return modoUpdate;
	}

	public void setModoUpdate(boolean modoUpdate) {
		this.modoNew = !modoUpdate;
		this.modoView = !modoUpdate;
		this.modoDelete = !modoUpdate;
		this.modoUpdate = modoUpdate;
		this.modoInactivate= !modoUpdate;/* MLUYO */
	}

	public boolean isModoView() {
		return modoView;
	}

	public void setModoView(boolean modoView) {
		this.modoNew = !modoView;
		this.modoView = modoView;
		this.modoDelete = !modoView;
		this.modoUpdate = !modoView;
		this.modoInactivate= !modoView;
	}

	public boolean isModoDelete() {
		return modoDelete;
	}

	public void setModoDelete(boolean modoDelete) {
		this.modoNew = !modoDelete;
		this.modoView = !modoDelete;
		this.modoDelete = modoDelete;
		this.modoUpdate = !modoDelete;
		this.modoInactivate= !modoDelete;
	}

	public boolean isModoNew() {
		return modoNew;
	}

	public void setModoNew(boolean modoNew) {
		this.modoNew = modoNew;
		this.modoView = !modoNew;
		this.modoDelete = !modoNew;
		this.modoUpdate = !modoNew;
		this.modoInactivate= !modoNew;/* MLUYO */
	}
	public boolean isModoInactivate() {
		return modoInactivate;
	}
	public void setModoInactivate(boolean modoInactivate) {
		this.modoNew = !modoInactivate;
		this.modoView = !modoInactivate;
		this.modoDelete = !modoInactivate;
		this.modoUpdate = !modoInactivate;
		this.modoInactivate= modoInactivate;
	}

	public Map<String, String> getMapPropSeguridad() {
		mapPropSeguridad = EntidadSession.getInstance().getMapPropSeguridad();
		if(mapPropSeguridad==null){
			mapPropSeguridad = new HashMap<String,String>();
		}
		return mapPropSeguridad;
	}

	public void setMapPropSeguridad(Map<String, String> mapPropSeguridad) {
		this.mapPropSeguridad = mapPropSeguridad;
	}

	public boolean isEvitarSeguridad() {
		return evitarSeguridad;
	}

	public void setEvitarSeguridad(boolean evitarSeguridad) {
		this.evitarSeguridad = evitarSeguridad;
	}
	
	
	
	
	
	/*public void eventPreRender() {				
		setMapPropSeguridad(EntityGlobal.getInstance().getMapPropSeguridad());
	}*/
	
	public String getMODO_ACTUAL() {
		return MODO_ACTUAL;
	}
	
	public void setMODO_ACTUAL(String mODO_ACTUAL) {
		MODO_ACTUAL = mODO_ACTUAL;
	}
	/***EDICIÓN DE DESCARGAS******/
	@SuppressWarnings("unused")
	private String titulo = "";
	public void actionLinkDescarga(String infoDoc) {
		//System.out.println("TEST actionLinkDescarga..:"+infoDoc);
		titulo = infoDoc;
	}
    public void postProcessXLS_descarga(Object document) {
    	//System.out.println("TEST POST DESCARGA XLS...:"+titulo);
    	try{                          
            /******************/
            
    	}catch(Exception ex){    		
    		ex.printStackTrace();    		
    	}    	
   
    }      
    
	public void preProcessPDF_descarga(Object document) throws IOException
		/*, BadElementException, DocumentException*/ {
		//System.out.println("TEST PRE  DESCARGA PDF...:"+titulo);
//        Document pdf = (Document) document;
//        
//        pdf.setPageSize(PageSize.A4.rotate());        
//        pdf.open();                       
//        pdf.addCreationDate();        
//
//		//ADD TITULO
//        Paragraph paragraph = new Paragraph();
//        paragraph.setSpacingAfter(20);
//        paragraph.setSpacingBefore(30);
//        paragraph.setAlignment(Element.ALIGN_CENTER);                                        
//        paragraph.add(new Chunk(new String(titulo.getBytes(), "ISO-8859-1"), new Font(Font.BOLD,22)));
//        //Phrase  phTit = new Phrase();
//        //phTit.setFont(new Font(Font.BOLD, 25));
//        //phTit.add("ESTE ES EL TITULO");               
//        pdf.add(paragraph);
//                
//        titulo="";
	}

	public boolean isBtnGuardarInactivarVisible() {
		return btnGuardarInactivarVisible;
	}

	public void setBtnGuardarInactivarVisible(boolean btnGuardarInactivarVisible) {
		this.btnGuardarInactivarVisible = btnGuardarInactivarVisible;
	}





	public void setPaginaActual(String paginaActual) {
		this.paginaActual = paginaActual;
	}



	public void setGrupoDefault(String grupoDefault) {
		this.grupoDefault = grupoDefault;
	}





	public void setConceptoDefault(String conceptoDefault) {
		this.conceptoDefault = conceptoDefault;
	}



	public void setConceptoDescActual(String conceptoDescActual) {
		this.conceptoDescActual = conceptoDescActual;
	}



	public boolean isVisibleRegistro() {
		return visibleRegistro;
	}



	public void setVisibleRegistro(boolean visibleRegistro) {
		this.visibleRegistro = visibleRegistro;
	}
	
    public String getLenguaje() {
    	lenguaje =FacesUtil.getSessionLocale().getLanguage(); 
		return lenguaje;
	}



	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}



	public String getLocaleSession() {
		return localeSession;
	}

	public void setLocaleSession(String localeSession) {
		localeSession =FacesUtil.getSessionLocale().getLanguage(); 				
		this.localeSession = localeSession;
	}

	public String getUrlDefaultBlank() {		 			
		return URL_DEFAULT;
	}
}

