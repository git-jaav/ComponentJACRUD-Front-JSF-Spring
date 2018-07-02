package pe.jaav.sistemas.seguridadweb.util;


public class Constant{

	/** CONSTANTES GENERALES NEGOCIO **/
	
	public static final String USUARIO_ADMINSYS= "ADMINSYS";
	public static final String COMPANIA_VARIABLE = "999999";
	public static final String APLICACION_CODIGO = "TW";
	public static final String APLICACION_CODIGO_GEN = "WF";	
	
	public static final String APP_CODIGO = "TW";
	public static final String APP_COMPANIA = "999999";		
	
	public static final String DB_CODE = "DB";
	//public static ResourceBundle propiedadesParam= ResourceBundle.getBundle("parametrosService");

	
	/** CONSTANTES DE PARAMETRO **/
	public static final String PARAMETRO_TIPODATO_NUMERICO = "N";
	public static final String PARAMETRO_TIPODATO_FECHA = "D";
	public static final String PARAMETRO_TIPODATO_TEXTO = "T";
	
	/** CONSTANTES DE PERSONA **/
	public static final String PERSONA_TIPOPERSONA_GRUPO = "G";
	public static final String PERSONA_TIPOPERSONA_NATURAL = "N";
	public static final String PERSONA_TIPOPERSONA_JURIDICA = "J";
	public static final String PERSONA_TIPOPERSONA_NATURAL_CON_NEGOCIO = "B";
	
	public static final String PERSONA_TIPODOCUMENTO_DNI = "D";
	public static final String PERSONA_TIPODOCUMENTO_CARNETMILITAR = "C";
	public static final String PERSONA_TIPODOCUMENTO_NITEXTRANJERO = "E";
	public static final String PERSONA_TIPODOCUMENTO_LIBRETAMILITAR = "M";
	public static final String PERSONA_TIPODOCUMENTO_PARTIDANACIMIENTO = "N";
	public static final String PERSONA_TIPODOCUMENTO_OTROSTIPOSDEDOCUMENTOS = "O";
	public static final String PERSONA_TIPODOCUMENTO_PASAPORTE = "P";
	public static final String PERSONA_TIPODOCUMENTO_RUC = "R";
	public static final String PERSONA_TIPODOCUMENTO_CARNETEXTRANJERA = "X";
	public static final String PERSONA_TIPODOCUMENTO_DOCADMINISTRATIVO = "A";
 	
	/** CONSTANTES PARA SEXO **/
	public static final String SEXO_MASCULINO = "M";
	public static final String SEXO_FEMENINO = "F";
	
	/** CONSTANTES PARA ESTADO CIVIL **/
	public static final String ESTADOCIVIL_SOLTERO = "S";
	public static final String ESTADOCIVIL_CASADO = "C";
	public static final String ESTADOCIVIL_VIUDO = "V";
	public static final String ESTADOCIVIL_DIVORCIADO = "D";
	public static final String ESTADOCIVIL_CONVIVIENTE = "T";
	public static final String ESTADOCIVIL_OTRO = "O";
	
	/**CONSTANTES PARA LA TABLA DE USUARIO **/
	
	public static final String DEFAULT_PASSWORD = "2";
					
		
	/** BOOLEAN EN INT **/
	public static final int INT_BOOLEAN_TRUE = 1;
	public static final int INT_BOOLEAN_FALSE = 0;

	public static final String PERSONA_NATURAL = "N";
	public static final String PERSONA_JURIDICA = "J";
	
	
	/** UNIDAD DE TIEMPO **/
	public static String PAR_UTIME_SEGUNDO="S";
	public static String PAR_UTIME_MINUTO="M";
	public static String PAR_UTIME_HORA="H";
	public static String PAR_UTIME_DIA="D";
		
	public static final String SI_db = "S";
	public static final String NO_db = "N";
	public static final int INT_SI = 1;
	public static final int INT_NO = 2;
	
	public static final String DELETE_db = "D";
	public static final String UPDATE_db = "U";
	public static final String INSERT_db = "I";
	
	public static final int YEAR_db = 1970;
	public static final int MONTH_db = 1;
	public static final int DAY_db = 1;
	public static final int HOUR_INI_db = 0;
	public static final int MINUTE_INI_db = 0;
	public static final int SECOND_INI_db = 0;
	public static final int MILLIS_INI_db = 0;
	public static final int HOUR_FIN_db = 23;
	public static final int MINUTE_FIN_db = 59;
	public static final int SECOND_FIN_db = 59;
	public static final int MILLIS_FIN_db = 59;
	
	public static final String INACTIVO_db = "I";
	public static final String ACTIVO_db = "A";
	public static final String ELIMINADO_db = "E";
	
	
	
	public static final String TIPO_REQUEST_POST= "POST";
	public static final String TIPO_REQUEST_GET= "GET";
	public static final String TIPO_REQUEST_PUT= "PUT";
	public static final String TIPO_REQUEST_DELETE= "DELETE";
	
	
	public static final String ORDER_ASC  = "ASC";
	public static final String ORDER_DESC = "DESC";
         
}
