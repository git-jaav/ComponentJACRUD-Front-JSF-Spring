package pe.jaav.sistemas.general.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import pe.jaav.common.json.UtilesJSON;
import pe.jaav.common.util.UtilesCommons;
import pe.jaav.common.util.model.PaginacionModel;
import pe.jaav.common.util.model.ResultTx;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.general.util.SesionComponent;
import pe.jaav.sistemas.general.util.UtilesService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

@Service("usuarioService")
public class UsuarioServiceImplBridge   implements UsuarioService{

	private static final String URI_SECURITY_AUTH="api/auth/autorizar";
	private static final String URI_USER="user/";
	private static final String URI_USERS="users/";
	private static final String URI_USERS_PAG="users/pag/";
	private static final String URI_LOGIN="login";
	
	
	public String urlTempServicetGenPathApi = UtilesService.getPropertyParametros("URL_TEMPLATE_SERVICERESTGENERAL_PATH_API");
	//public String urlTempServiceGenPathServer = UtilesService.getPropertyPathServer();		

	/**Si se desea tratar Map de Sesiones y Tokesn*/
	@SuppressWarnings("unused")
	@Autowired
	private SesionComponent sesionComponent;
	
	@Override
	public Optional<SysUsuario> obtenerLogin(String usuario, String clave) {
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi +URI_LOGIN;//+usuario+"/"+clave;
		SysUsuario obj = new SysUsuario();
		obj.setUsuaUsuario(usuario);
		obj.setUsuaClave(clave);
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam, obj, SysUsuario.class);				
		return (result !=null ? Optional.of((SysUsuario)result):Optional.empty());
	}

	@Override
	public Optional<SysUsuario> obtenerPorID(Integer id,String currentToken) {
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USER+id;			
		Object result = UtilesJSON.getObjectJson(urlParam, SysUsuario.class,
				UtilesService.getMapHeaderRequestToken(currentToken));				
		return (result !=null ? Optional.of((SysUsuario)result):Optional.empty());
	}

	@Override
	public int contarListado(SysUsuario objUsuario,String currentToken) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUsuario> listar(SysUsuario objUsuario, boolean paginable,String currentToken) {		
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USERS;					
		Object result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario,new TypeReference<List<SysUsuario>>() {},
				UtilesService.getMapHeaderRequestToken(currentToken),true);
		List<SysUsuario> lista = null;
		if(result!=null){
			lista =  (List<SysUsuario>) result;			
		}		
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginacionModel<SysUsuario> listar(SysUsuario objUsuario,String currentToken) {
		PaginacionModel<SysUsuario> paginacion = PaginacionModel.crearPaginaVacia(); 				
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USERS_PAG;
		//List<?> result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario, SysUsuario.class);		
		
		Object result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario,new TypeReference<List<SysUsuario>>() {},
				UtilesService.getMapHeaderRequestToken(currentToken),true);
		List<SysUsuario> lista = null;
		if(result!=null){
			lista =  (List<SysUsuario>) result;			
			if(lista.size()>0){
				paginacion = PaginacionModel.crearPagina(lista, lista.get(0).getContadorTotal());	
			}			
		}		
		
		return paginacion;
	}
	
	@Override
	public ResultTx<SysUsuario> guardar(SysUsuario objUsuario,String currentToken) {
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USER+"i/";					
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam,objUsuario,objUsuario.getClass(),
				UtilesService.getMapHeaderRequestToken(currentToken));
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}

	@Override
	public ResultTx<SysUsuario> actualizar(SysUsuario objUsuario,String currentToken) {
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USER+"u/";					
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam,objUsuario,objUsuario.getClass(),
				UtilesService.getMapHeaderRequestToken(currentToken));
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}

	@Override
	public ResultTx<SysUsuario> eliminar(SysUsuario objUsuario,String currentToken) {
		String urlParam = UtilesService.getPropertyPathServer() +urlTempServicetGenPathApi+URI_USER+"d/";					
		Object result = UtilesJSON.deleteObjectJson(urlParam,objUsuario,objUsuario.getClass(),
				UtilesService.getMapHeaderRequestToken(currentToken));
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}

	@Override
	public Optional<SysUsuario> obtenerLoginAuth(Object objCredential) {
		String urlParam = UtilesService.getPropertyPathServer() +URI_SECURITY_AUTH;//+usuario+"/"+clave;
		
		/**Tratar MAP header*/
		Map<String,String> mapToken = UtilesCommons.getNewHashMap();
		String key = UtilesService.JWT_TOKEN_HEADER_PARAM;
		mapToken.put(key,null);				
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam, objCredential, SysUsuario.class,mapToken);
		
		/**Retornar Obj. Auth*/
		if(result!=null && result instanceof SysUsuario){
			SysUsuario usuarioResult = (SysUsuario)result;
			if(UtilesCommons.noEsVacio(mapToken)){
				if(mapToken.containsKey(key)){
					String token = mapToken.get(key);
					if(UtilesCommons.noEsVacio(token)){
						usuarioResult.setTokenSecurity(token);
						//sesionComponent.setTokenScurity(token);
					}
				}								
			}						
			return (Optional.of(usuarioResult));
		}else{
			return (Optional.empty());
		}					
	}

}
