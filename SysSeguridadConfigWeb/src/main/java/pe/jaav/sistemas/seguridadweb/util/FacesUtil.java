package pe.jaav.sistemas.seguridadweb.util;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import pe.jaav.sistemas.seguridadweb.commons.EventosBean;
import pe.jaav.sistemas.seguridadweb.server.EntidadSession;


public class FacesUtil {

	/**VARIABLES DEL SISTEMA*/
	public static ResourceBundle propiedades = ResourceBundle.getBundle("mensajes",FacesUtil.getContextLocale());	
	public static ResourceBundle propiedadesParam= ResourceBundle.getBundle("parametrosSys");
	public static ResourceBundle propiedadesLabelVistas= ResourceBundle.getBundle("labelsVistas",FacesUtil.getContextLocale());
	
	public static ResourceBundle propiedadesBuildInfo = ResourceBundle.getBundle("build/buildInfo",FacesUtil.getContextLocale());
	
	public static String LOCALE_CODIGO_SPAIN_DESC="Español";
	public static String LOCALE_CODIGO_ENGLISH_DESC="English";
	
	public static String LOCALE_CODIGO_SPAIN="_es";
	public static String LOCALE_CODIGO_ENGLISH="_en";
	public static String LOCALE_CODIGO_ITALIAN="_it";
	public static String LOCALE_CODIGO_CHINA="_zh_CN";
		
	public static String LOCALE_CODIGO_ACTUAL=LOCALE_CODIGO_SPAIN;
	
	public static final String VAR_DIALOG_LOGIN_CAJERO = "dlgAutorizaCajero";		
	
	public static boolean isPostback(){
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	public static void adicionarMensajeError(String id, String mensaje){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));	
	}
	
	public static void adicionarMensajeError(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje));	
	}
	
	public static void adicionarMensajeInfo(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));	
	}

	public static void adicionarMensajeWarning(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));	
	}
	
	public static void adicionarMensajeWarning(String id,String mensaje){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));	
	}
	
	public static void mensajeErrorPropiedades(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, getMSJProperty(mensaje), getMSJProperty(mensaje)));		
	}
	
	public static void mensajeErrorPropiedades(String id, String mensaje){
		FacesContext.getCurrentInstance().addMessage(id, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, getMSJProperty(mensaje), getMSJProperty(mensaje)));	
	}
	
	public static void mensajeInfoPropiedades(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, getMSJProperty(mensaje), getMSJProperty(mensaje)));	
	}

	public static void mensajeWarningPropiedades(String mensaje){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, getMSJProperty(mensaje), getMSJProperty(mensaje)));	
	}
	
	public static String getMSJPropertyParams(String mensajePropiedades, Object ... parametros){
		String mensaje = getMSJProperty(mensajePropiedades);
		return MessageFormat.format(mensaje, parametros);
	}
	
	public static void mensajeInfoPropiedadesParametro(String mensaje, Object ... parametros){
		mensajePropiedadesParametro(FacesMessage.SEVERITY_INFO, mensaje, parametros);
	}
	
	public static void mensajeInfoPropiedadesParametro(String id, String mensaje, Object ... parametros){
		mensajePropiedadesParametro(id, FacesMessage.SEVERITY_INFO, mensaje, parametros);
	}
	
	public static void mensajeWarningPropiedadesParametro(String mensaje, Object ... parametros){
		mensajePropiedadesParametro(FacesMessage.SEVERITY_WARN, mensaje, parametros);
	}
	
	public static void mensajeWarningPropiedadesParametro(String id, String mensaje, Object ... parametros){
		mensajePropiedadesParametro(id, FacesMessage.SEVERITY_WARN, mensaje, parametros);
	}
	
	public static void mensajeErrorPropiedadesParametro(String mensaje, Object ... parametros){
		mensajePropiedadesParametro(FacesMessage.SEVERITY_ERROR, mensaje, parametros);
	}
	
	public static void mensajeErrorPropiedadesParametro(String id, String mensaje, Object ... parametros){
		mensajePropiedadesParametro(id, FacesMessage.SEVERITY_ERROR, mensaje, parametros);
	}
	
	public static void mensajePropiedadesParametro(FacesMessage.Severity severity, String mensajePropiedades, Object ... parametros){
		mensajePropiedadesParametro(null, severity, mensajePropiedades, parametros);
	}
	
	public static void mensajePropiedadesParametro(String id, FacesMessage.Severity severity, String mensajePropiedades, Object ... parametros){
		String mensaje = getMSJProperty(mensajePropiedades);
		String mensajeFormateado = MessageFormat.format(mensaje, parametros);
		FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(severity, mensajeFormateado, mensajeFormateado));
	}
	
	public static void jsMensajeManual(String mensaje, String tipo){
		String script = "parent.swal({title: '"+ 
						mensaje +"',text: '', type: '"+ 
						tipo +"' ,timer: 1500, showConfirmButton: false });";
		RequestContext.getCurrentInstance().execute(script);
	}
	
	public static void jsMensajePropiedades(String mensaje, String tipo){
		String script = "parent.swal({title: '"+ 
						getMSJProperty(mensaje) +"', text: '', type: '"+ 
						tipo +"' , timer: 2000,  showConfirmButton: false });";
		RequestContext.getCurrentInstance().execute(script);
	}
	
	public static Calendar dateToCalendar(Date date) {
		if (date!=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} else {
			return null;
		}
	}
	
	public static Locale getContextLocale(){		
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public static Locale getSessionLocale(){
		if(EntidadSession.getInstance()!=null){
			if(EntidadSession.getInstance().getLocaleConfigActual()!=null){
				return EntidadSession.getInstance().getLocaleConfigActual();
			}
		}			
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}
	
	public static String getMSJProperty(String prop){	
		//propiedades.getLocale().setDefault(FacesContext.getCurrentInstance().getViewRoot().getLocale());
		//propiedades = ResourceBundle.getBundle("mensajes",FacesUtil.getContextLocale());
		if(propiedades.containsKey(prop)){
			return propiedades.getString(prop);	
		}else{
			return "[Definir mensaje]";
		}				
	}

	public static String getPropertyParametros(String prop){
		if(propiedadesParam.containsKey(prop)){
			return propiedadesParam.getString(prop);	
		}else{
			return "";
		}				
	}

	public static String getPropertyLabelsVistas(String prop){
		if(propiedadesLabelVistas.containsKey(prop)){
			return propiedadesLabelVistas.getString(prop);	
		}else{
			return "";
		}					
	}
	
	public static String getPropertyBuildInfo(String prop){
		if(propiedadesBuildInfo.containsKey(prop)){
			return propiedadesBuildInfo.getString(prop);
		}else{
			return "";
		}
	}
	
    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        FacesContext.getCurrentInstance().validationFailed(); // Invalidate JSF page if we raise an error message

    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static Throwable getRootCause(Throwable cause) {
        if (cause != null) {
            Throwable source = cause.getCause();
            if (source != null) {
                return getRootCause(source);
            } else {
                return cause;
            }
        }
        return null;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static boolean isDummySelectItem(UIComponent component, String value) {
        for (UIComponent children : component.getChildren()) {
            if (children instanceof UISelectItem) {
                UISelectItem item = (UISelectItem) children;
                if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static String getComponentMessages(String clientComponent, String defaultMessage) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            if (inputComponent.isValid()) {
                return defaultMessage;
            } else {
                Iterator<FacesMessage> iter = fc.getMessages(inputComponent.getClientId());
                if (iter.hasNext()) {
                    return iter.next().getDetail();
                }
            }
        }
        return "";
    }
	
    public static UIComponent getUIComponent(String id) {  
        return FacesContext.getCurrentInstance().getViewRoot().findComponent(id) ;  
    }

    public static void putViewMap(String key, Object object){
    	FacesContext.getCurrentInstance().getViewRoot().getViewMap().put(key, object);
    } 
    
    public static Object getViewMap(String key){
    	return FacesContext.getCurrentInstance().getViewRoot().getViewMap().get(key);
    }
    
    public static boolean containsKeyViewMap(String key){
    	return FacesContext.getCurrentInstance().getViewRoot().getViewMap().containsKey(key);
    }
    
    public static <T> T getViewMap(String key, Class<T> clazz){
    	Object result = FacesContext.getCurrentInstance().getViewRoot().getViewMap().get(key);
    	if(result == null){
    		return null;
    	}else{
    		return clazz.cast(result);
    	}
    }
    
    public static void removeViewMap(String key){
    	FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(key);
    }
    
    public static void removeIfExistsViewMap(String key){
    	if(containsKeyViewMap(key)){
    		removeViewMap(key);
    	}
    }
    
    
    public static void putSessionMap(String key, Object object){
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, object);
    } 
    
    public static Object getRequestMap(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    } 
    
    public static void putRequestMap(String key, Object object){
    	FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, object);
    }
    
    public static boolean containsKeyRequestMap(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().containsKey(key);
    }
    
    public static boolean containsKeySessionMap(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey(key);
    } 
    
    public static Object getSessionMap(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }
    
    public static void removeIfExistsSessionMap(String key){
    	if(containsKeySessionMap(key)){
    		removeSessionMap(key);
    	}
    }
    
    public static <T> T getSessionMap(String key, Class<T> clazz){
    	Object result = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    	if(result == null){
    		return null;
    	}else{
    		return clazz.cast(result);
    	}
    }

    public static void removeSessionMap(String key){
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }
    
    public static Object getRequestParam(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }
    
    public static void putRequestParam(String key, String value){
    	FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put(key, value);
    }
    
    public static boolean containsRequesParam(String key){
    	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey(key);
    }
    
    public static void removeRequestParam(String key){
    	if(containsRequesParam(key)){
    		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().remove(key);
    	}
    }
    
    public static void getRenderId(String key){
    	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(key);
    }
    
    public static Object getCookie(String key){
    	Cookie cookie = (Cookie)FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(key);
    	return cookie == null ? null : cookie.getValue();
    }
    
    public static void setCookie(String key, Object value){/**CHEQUEAR**/
    	FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().put(key, value);
    }  
    
    public static void removeCookie(String key){/**CHEQUEAR**/
    	FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().remove(key);
    }
    
    /** METODOS FLASH **/
    
    public static Flash getFlashMap(){
    	return FacesContext.getCurrentInstance().getExternalContext().getFlash();
    }
    
    public static void putFlash(String key, Object value){
    	getFlashMap().put(key, value);
    }
    
    public static Object getFlash(String key){
    	return getFlashMap().get(key);
    }
    
    public static boolean containsKeyFlash(String key){
    	return getFlashMap().containsKey(key);
    }
    
    public static void removeFlash(String key){
    	Flash flash = getFlashMap();
    	if(flash.containsKey(key)){
    		flash.remove(key);
    	}
    }
    
    /****** MENSAJES ESPECIFICOS********/
    

	public static void mensajeErrorAbrirBuscador(){
		mensajeErrorPropiedades("MSJ_ERROR_abrirBuscador");
	}
    
    public static void mensajeErrorAbrirRegistro(){
		mensajeErrorPropiedades("MSJ_ERROR_abrirRegistro");
	}
    
    public static void mensajeErrorBuscarRegistros(){
    	mensajeErrorPropiedades("MSJ_ERROR_buscarRegistros");
    }
    
    
	
	public static void update(String ... ids){
		RequestContext.getCurrentInstance().update(Arrays.asList(ids));
	}

	public static void execute(String script){
		RequestContext.getCurrentInstance().execute(script);
	}
	
	public static void executeJSFunction(String functionName, String ... parameters){
		String script = functionName+"(";
		if(parameters != null && parameters.length > 0){
			for(int i = 0; i < parameters.length; i++){
				script = script+parameters[i]+", ";
			}
			script = script.substring(0, script.lastIndexOf(", "))+");";
		}else{
			script = script+");";
		}
		execute(script);
	}
	
	public static void updateSchedulePrimefaces(String widgetVarSchedule){
		operationWidgetVarPrimefaces(widgetVarSchedule, "update();");
	}
	
	public static void operationWidgetVarPrimefaces(String widgetVar, String operation){
		execute("PF('"+widgetVar+"')."+operation+";");
	}
	
	public static void showDialogPrimefaces(String widgetVarDialog){
		operationWidgetVarPrimefaces(widgetVarDialog, "show();");
	}
	
	public static void hideDialogPrimefaces(String widgetVarDialog){
		operationWidgetVarPrimefaces(widgetVarDialog, "hide();");
	}
	
	/**Redirecciona a otra página del contexto actual*/
    public static void redirect(String paginaSgt){
        //String paginaSgt=UrlBandejaEducativo;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url="";
        String contextPath="";
        if(request!=null){
            url = request.getRequestURL().toString();
            contextPath = request.getContextPath();
        }else{
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            url = contextPath+paginaSgt;
            ec.redirect(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
	/**Redirecciona a otra página del contexto actual*/
    public static String getPathSys(){    
    	 String url="";
    	try {
	        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();	       
	        String contextPath="";	        
	        if(request!=null){
	            url = request.getRequestURL().toString();
	            contextPath = request.getContextPath();
	        }                      
            url = contextPath;            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return url;
    }    
    
    public static boolean esDataValida(SelectEvent event, Class<?> clazz){
    	if(event != null && event.getObject() != null && clazz.isInstance(event.getObject())){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    
    
	/** Evento para obtener algún recurso (cualquier objeto ) desde otro BEAN
	 * @param nombreBean
	 * @param nombreEvento
	 * @return
	 */
	public static EventosBean getGenericEventoBean(String nombreBean, String nombreEvento) {
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

	public static Object getGenericObjectBean(String nombreBean, String nombreAtributo, Class<?> tipo) {
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
	
	
	/**
	 * Metodo para cerrar la ventana login de cajero
	 */
	public static void cerrarVentanaAutorizacionCajero(){
		hideDialogPrimefaces(VAR_DIALOG_LOGIN_CAJERO);
	}

	
//	/** Método para inicializar el contenedor genérico de un reporte
//	 * @param parametros: un Map con los parámetros para los filtros: 
//	 * @param formularioPadre: enviar el ID del formulario Web que contiene el reporte
//	 * @param dialogVar: variable del Dialog que contiene el reporte, si lo tuviere.
//	 * @param nombreReporte: Nombre del Reporte
//	 * @param formatoReporte: FOrmato del reporte, pe: 'PFD' de Utiles.FORMATO_PDF
//	 * @param eventoRetorno: el evento de retorno del botón aceptar 
//	 */
//	public static void setContenedor_ReporteGen(Map<String, Object> parametros,
//			String formularioPadre, String dialogVar,String nombreReporte,String formatoReporte,String height,
//			EventosBean.EventoGeneracionReportesGen eventoRetorno) {		
//		try {		
//			
//			/** (1:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTOS A LIMPIAR */
//			EntidadReporte obj1 = (EntidadReporte) getGenericObjectBean("dataContenReporteBean",
//					"entidadRepFiltro", EntidadReporte.class);										
//			obj1.setNombreReportGen(nombreReporte);
//			obj1.setFormPadreReportGen(formularioPadre);
//			obj1.setVarDialogReportGen(dialogVar);
//			obj1.setParametros(parametros);
//			obj1.setVisibleAceptarReportGen(true);
//			obj1.setFormatoReportGen(formatoReporte);
//			obj1.setHeightReporteGen(height);
//			/************************************************/
//
//			/** (2:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTO EVENTO */
//			
//			EventosBean obj = getGenericEventoBean("dataContenReporteBean", "eventoSeleccion");	
//			if (obj != null) {
//				obj.setEventoGeneracionReportesGen(eventoRetorno);								
//				RequestContext.getCurrentInstance().execute("inicializarReportConten();");				
//				
//			} else {				
//			}			
//			/************************************************/			
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	/** Método para inicializar el contenedor genérico de un reporte (válido solo para la visualización)
	 * @param parametros :
	   Un Map con los parámetros para los filtros: 
	 * @param formularioPadre: 
	 	Enviar el ID del formulario Web que contiene el reporte
	 * @param dialogVar : variable del Dialog que contiene el reporte, si lo tuviere.
	 * @param nombreReporte : 
	 * Nombre del Reporte
	 * @param formatoReporte : FOrmato del reporte, pe: 'PFD' de Utiles.FORMATO_PDF
	 * @param formatoReporte : Una dimensión para la altura (Es recomendable que el Host tenga el control)
	 * @param height : Altura opcional para el componente que contenga al reporte
	 * @param indicaTarget_Blank : Booleano que indica si se abrirá en otro Tab o no el reporte
	
	 */
//	public static void setContenedor_ReporteGen(Map<String, Object> parametros,
//			String formularioPadre, String dialogVar,String nombreReporte,String formatoReporte,String height
//			,boolean indicaTarget_Blank
//			) {		
//		try {					
//			/** (1:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTOS A LIMPIAR */
//			EntidadReporte obj1 = (EntidadReporte) getGenericObjectBean("dataContenReporteBean",
//					"entidadRepFiltro", EntidadReporte.class);										
//			obj1.setNombreReportGen(nombreReporte);
//			obj1.setFormPadreReportGen(formularioPadre);
//			obj1.setVarDialogReportGen(dialogVar);
//			obj1.setParametros(parametros);
//			obj1.setVisibleAceptarReportGen(false);
//			obj1.setFormatoReportGen(formatoReporte);
//			obj1.setHeightReporteGen(height);
//			/************************************************/
//			/** (2:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTO EVENTO */
//			
//			EventosBean obj = getGenericEventoBean("dataContenReporteBean", "eventoSeleccion");	
//			if (obj != null) {
//				obj.setEventoGeneracionReportesGen(new EventosBean.EventoGeneracionReportesGen() {					
//					@Override
//					public void retornoGeneracionAceptar(String mensaje) {
//						//SIN ACCION
//					}
//				});
//				
//				if(indicaTarget_Blank){
//					RequestContext.getCurrentInstance().execute("cargarBlankReporte();");	
//				}else{
//					RequestContext.getCurrentInstance().execute("inicializarReportConten();");	
//				}
//			} 		
//			/************************************************/			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//	
//	/** Método para inicializar el contenedor genérico de un reporte (Válido también para persistencia)
//	 * @param parametros :
//	   Un Map con los parámetros para los filtros: 
//	 * @param formularioPadre : 
//	 	Enviar el ID del formulario Web que contiene el reporte
//	 * @param dialogVar : variable del Dialog que contiene el reporte, si lo tuviere.
//	 * @param nombreReporte : 
//	 * Nombre del Reporte
//	 * @param formatoReporte : FOrmato del reporte, pe: 'PFD' de Utiles.FORMATO_PDF
//	 * @param formatoReporte : Una dimensión para la altura (Es recomendable que el Host tenga el control)
//	 * @param height : Altura opcional para el componente que contenga al reporte
//	 * @param indicaTarget_Blank : Booleano que indica si se abrirá en otro Tab o no el reporte
//	 * @param indicaSave_ALFRESCO : Booleano que indica si se guardará en un REPOSITORIO ALFRESCO, establecido
//	 * @param nombreFile : Nombre del archivo (válido para cualquiera de las dos formas de persistencia)
//	 * @param indicaSave_PATH : Booleano que indica si se guardará en una ruta (PATH )establecida (Si 'indicaSave_ALFRESCO' = true, prevalecerá siempre el modo 'indicaSave_ALFRESCO')
//	 * @param pathFile : Nombre del PATH, que se utilizará para persistir el archivo, si 'indicaSave_PATH' = true	
//	 */
//	public static void setContenedor_ReporteGen(Map<String, Object> parametros,
//			String formularioPadre, String dialogVar,String nombreReporte,String formatoReporte,String height
//			,boolean indicaTarget_Blank
//			,boolean indicaSave_ALFRESCO,String nombreFile,boolean indicaSave_PATH,String pathFile
//			) {		
//		try {					
//			/** (1:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTOS A LIMPIAR */
//			EntidadReporte obj1 = (EntidadReporte) getGenericObjectBean("dataContenReporteBean",
//					"entidadRepFiltro", EntidadReporte.class);										
//			obj1.setNombreReportGen(nombreReporte);
//			obj1.setFormPadreReportGen(formularioPadre);
//			obj1.setVarDialogReportGen(dialogVar);
//			obj1.setParametros(parametros);
//			obj1.setVisibleAceptarReportGen(false);
//			obj1.setFormatoReportGen(formatoReporte);
//			obj1.setHeightReporteGen(height);
//			/////SEGUNDA PARTE DE PAR�?METROS
//			obj1.setNombreFileReportGen(nombreFile);
//			obj1.setPathFileReportGen(pathFile);
//			obj1.setIndicaPersistenciaGestorContenido(indicaSave_ALFRESCO);
//			obj1.setIndicaPersistenciaPath(indicaSave_PATH);
//			/************************************************/
//			/** (2:)Param. BEAN DEL SELECCIONADOR Y ATRIBUTO EVENTO */
//			
//			EventosBean obj = getGenericEventoBean("dataContenReporteBean", "eventoSeleccion");	
//			if (obj != null) {
//				obj.setEventoGeneracionReportesGen(new EventosBean.EventoGeneracionReportesGen() {					
//					@Override
//					public void retornoGeneracionAceptar(String mensaje) {
//						//SIN ACCION
//					}
//				});
//				
//				if(indicaTarget_Blank){
//					RequestContext.getCurrentInstance().execute("cargarBlankReporte();");	
//				}else{
//					RequestContext.getCurrentInstance().execute("inicializarReportConten();");	
//				}
//			} 		
//			/************************************************/			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}	

	
	public static void mostarMensajeDialog(FacesMessage.Severity severity, String titulo, String detalle){
		@SuppressWarnings("unused")
		FacesMessage message;
		if(severity == null){
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, detalle);
		}else{
			message = new FacesMessage(severity, titulo, detalle);
		}	
		//mostrarMensajeDialog(message);	
	}
	
	public static void mostarMensajeInfoDialog(String titulo, String detalle){
		mostarMensajeDialog(null, titulo, detalle);
	}

	/**Redirecciona a la página set por defecto en el sistema*/
    public static void redirectPaginaDefault(){    			
		if(EntidadSession.getInstance()!=null){
			redirect(FacesUtil.getPropertyParametros("PAR_PAGININA_DEFAULT"));
//			EntidadSession.getInstance().setGrupoActual(EntidadSession.getInstance().getSysGrupo());
//			EntidadSession.getInstance().setConceptoActual(EntidadSession.getInstance().getSysConcepto());
//			EntidadSession.getInstance().setSysUrl(EntidadSession.getInstance().getUrlPaginaInicio());
//			EntidadSession.getInstance().setConceptoActualDesc(EntidadSession.getInstance().getSysConceptoDesc());			
		}		    
    }
    
	
}
