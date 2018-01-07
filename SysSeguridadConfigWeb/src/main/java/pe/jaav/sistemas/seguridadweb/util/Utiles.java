package pe.jaav.sistemas.seguridadweb.util;



import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;


public class Utiles {


	/***PARÁMETROS GENERALES***/	
	public static String PUNTO_EXTENSION_PAGs= ".xhtml";
	public static String FLAG_PANEL_OPCIONES= "OPCIONES";
	public static String FLAG_PANEL_RECURSOS= "RECURSOS";

	public static String HOST_APP = "HOST_UNDEF";
	

	public static final String SI_db = "S";
	public static final String NO_db = "N";

	public static final String SI_desc = "Sí";
	public static final String NO_desc = "No";
	public static final String DELETE_db = "D";
	public static final String UPDATE_db = "U";
	public static final String INSERT_db = "I";

	public static final String TYPE_MSG_WARN = "warn";
	public static final String TYPE_MSG_INFO = "info";
	public static final String TYPE_MSG_ERROR = "error";

	public static final String FORMA_DATECOMPLETA = "FECHACOMLETA";	

	public static String UNID_SEGUNDOS ="segundos";
	
	public static final String UTF_8 = "UTF-8";
	
	public static final String LOCALHOST_IPV4 = "127.0.0.1";
	public static final String LOCALHOST_IPV6_1 = "0:0:0:0:0:0:0:1";
	public static final String LOCALHOST_IPV6_2 = "::1";
	public static final String LOCALHOST = "localhost";

	/***PAR�?METROS REPORTES***/
	public static final String COD_REPORTE_SERVLET="reporte";			
	
	public static final String PUNTO_EXT_REPORTES_TEMPLATE= ".jrxml";
	public static final String PUNTO_EXT_REPORTES_COMPILE= ".jasper";
	
	public static final String FORMATO_PDF= "PDF";
	public static final String FORMATO_XLS= "XLS";
	public static final String FORMATO_XHTML= "xhtml";
	
	
	
	/****************************************************************************/


	public static String obtenerTipoRegistroMatricula(int tipoRegistro){
		switch(tipoRegistro){
			case  1 : return "Presencial";
			default : return "";
		}
	}

	
	/** Obtener IP del cliente **/
	public static String getIPCliente(){//192.168, 172.16. o 10.0.
		String ipAddress="";
		try{
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			ipAddress = request.getHeader("X-FORWARDED-FOR");
			if ( ipAddress == null ) {
				ipAddress = request.getRemoteAddr();
				if(ipAddress.equals("127.0.0.1")){
					try {
						ipAddress = InetAddress.getLocalHost().getHostAddress();
					} catch (Exception e) {
						ipAddress = "";
					}
				}
			}
			String[] ips = ipAddress.split(",");
			for(String ip : ips){
				if(!ip.trim().startsWith("127.0.")){
					ipAddress = ip;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ipAddress;
	}
	/** Obtener Work Satation Name  del cliente **/
	public static String getPcNombreCliente(){
		String host="";
		try{
			String ips[] = getIPCliente().split("\\.");
			byte[] ipAddr = new byte[]{(byte)Integer.parseInt(ips[0]),
					(byte)Integer.parseInt(ips[1]),
					(byte)Integer.parseInt(ips[2]),
					(byte)Integer.parseInt(ips[3])};
			InetAddress inet = InetAddress.getByAddress(ipAddr);
			host = inet.getHostName();
		}catch(Exception ex){
			//Log.error(ex, "Utiles :: getPcNombreCliente :: controlado");
		}
		return host;
	}

	/** Obtener MAC del cliente **/
	public static String getMacCliente(){
		InetAddress ip;
		byte[] mac = null;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],(i < mac.length - 1) ? "-" : ""));
			}
		} catch (UnknownHostException e) {
			//Log.error(e, "Utiles :: getMacCliente :: controlado");
		} catch (SocketException e) {
			//Log.error(e, "Utiles :: getMacCliente :: controlado");
		} catch (NullPointerException e){
			//Log.error(e, "Utiles :: getMacCliente :: controlado");
		}
		return sb.toString();
	}

	
	public static String codificarCadena(String cadena, String codificacion){
		try {
			return new String(cadena.getBytes(Charset.defaultCharset()), codificacion);
		} catch (UnsupportedEncodingException e) {
			return cadena;
		}
	}
	
	public static String codificarEnUtf8(String cadena){
		try {
			return new String(cadena.getBytes(Charset.defaultCharset()), UTF_8);
		} catch (UnsupportedEncodingException e) {
			return cadena;
		}
	}
	
	public static boolean esLocalHost(String ip){
		if(ip == null){
			return false;
		}
		return (ip.equalsIgnoreCase(LOCALHOST) || ip.equalsIgnoreCase(LOCALHOST_IPV4) || ip.equalsIgnoreCase(LOCALHOST_IPV6_1) || ip.equalsIgnoreCase(LOCALHOST_IPV6_2) ? true : false);	
	}
	
    /**
     * @return
     */
    public static String obtenerIpRemota(){
    	FacesContext ctx = FacesContext.getCurrentInstance();
    	HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
    	String remoteAddress = request.getRemoteAddr();
    	return remoteAddress;
    }
    
    /**
     * @return
     */
    public static String obtenerNombrePcRemota(){
    	FacesContext ctx = FacesContext.getCurrentInstance();
    	HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
    	
    	String remoteAddress = request.getRemoteAddr();
		try {
			InetAddress inetAddress = null;
			if(esLocalHost(remoteAddress)){
				inetAddress = InetAddress.getLocalHost();
			}else{
				inetAddress = InetAddress.getByName(remoteAddress);
			}
			String pcName = inetAddress.getHostName();
			if(esLocalHost(pcName)){
				pcName = inetAddress.getCanonicalHostName();
			}
			return pcName;
		} catch (UnknownHostException e) {
			return null;
		}
    }

	
	public static String getContentType(String fileName){
		File file = new File(fileName);
		String contentType = null;
		try {
			contentType = Files.probeContentType(file.toPath());
		} catch (IOException e) {
			contentType = "application/pdf";
		}
		return contentType;
	}		
	
}
