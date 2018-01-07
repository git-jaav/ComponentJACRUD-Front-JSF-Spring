package pe.jaav.sistemas.general.util;

import java.util.ResourceBundle;

public class UtilesService {

	public static ResourceBundle propiedadesParam= ResourceBundle.getBundle("parametrosService");
	

	public static String getPropertyParametros(String prop){
		if(propiedadesParam.containsKey(prop)){
			return propiedadesParam.getString(prop);	
		}else{
			return "";
		}				
	}

	
}
