package pe.jaav.sistemas.general.util;

import java.util.Map;
import java.util.ResourceBundle;
import pe.jaav.common.util.UtilesCommons;

public class UtilesService {

	public static ResourceBundle propiedadesParam= ResourceBundle.getBundle("parametrosService");
	
    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer";    

	
	public static String getPropertyParametros(String prop){
		if(propiedadesParam.containsKey(prop)){
			return propiedadesParam.getString(prop);	
		}else{
			return "";
		}				
	}

	/**
	 * @param currentToken
	 * @return
	 */
	public static Map<String,String> getMapHeaderRequestToken(String currentToken){
		Map<String,String> mapHeaderRequest = UtilesCommons.getNewHashMap();
		mapHeaderRequest.put(UtilesService.JWT_TOKEN_HEADER_PARAM, currentToken);		
		return mapHeaderRequest;
	}	
	 
	
}
