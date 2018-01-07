package pe.jaav.sistemas.seguridadweb.server;

import pe.jaav.sistemas.seguridadweb.util.FacesUtil;

/**
 * @author JAAV
 *
 */
public abstract class InicializaSession {
    public   static void  InitGlobal(EntidadSession objglobal){
        
    	FacesUtil.putSessionMap("EntidadSession", objglobal);
    }
}
