package pe.jaav.sistemas.general.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import pe.jaav.common.json.UtilesJSON;
import pe.jaav.common.util.UtilesCommons;
import pe.jaav.common.util.model.PaginacionModel;
import pe.jaav.common.util.model.ResultTx;
import pe.jaav.sistemas.general.service.UsuarioService;
import pe.jaav.sistemas.general.util.UtilesService;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

@Service("usuarioService")
public class UsuarioServiceImplBridge   implements UsuarioService{

	private static final String URI_USER="user/";
	private static final String URI_USERS="users/";
	private static final String URI_USERS_PAG="users/pag/";
	private static final String URI_LOGIN="login";
	
	
	public String urlTemplateServistGen = UtilesService.getPropertyParametros("URL_TEMPLATE_SERVICERESTGENERAL");
	

	@Override
	public Optional<SysUsuario> obtenerLogin(String usuario, String clave) {
		String urlParam = urlTemplateServistGen+URI_LOGIN;//+usuario+"/"+clave;
		SysUsuario obj = new SysUsuario();
		obj.setUsuaUsuario(usuario);
		obj.setUsuaClave(clave);
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam, obj, SysUsuario.class);				
		return (result !=null ? Optional.of((SysUsuario)result):Optional.empty());
	}

	@Override
	public Optional<SysUsuario> obtenerPorID(Integer id) {
		String urlParam = urlTemplateServistGen+URI_USER+id;
		Object result = UtilesJSON.getObjectJson(urlParam, SysUsuario.class);				
		return (result !=null ? Optional.of((SysUsuario)result):Optional.empty());
	}

	@Override
	public int contarListado(SysUsuario objUsuario) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysUsuario> listar(SysUsuario objUsuario, boolean paginable) {		
		String urlParam = urlTemplateServistGen+URI_USERS;					
		Object result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario,new TypeReference<List<SysUsuario>>() {});
		List<SysUsuario> lista = null;
		if(result!=null){
			lista =  (List<SysUsuario>) result;			
		}		
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginacionModel<SysUsuario> listar(SysUsuario objUsuario) {
		PaginacionModel<SysUsuario> paginacion = PaginacionModel.crearPaginaVacia(); 
		
		
		String urlParam = urlTemplateServistGen+URI_USERS_PAG;
		//List<?> result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario, SysUsuario.class);		
		
		Object result = UtilesJSON.getListJsonFiltro(urlParam,objUsuario,new TypeReference<List<SysUsuario>>() {},true);
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
	public ResultTx<SysUsuario> guardar(SysUsuario objUsuario) {
		String urlParam = urlTemplateServistGen+URI_USER+"i/";					
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam,objUsuario,objUsuario.getClass());
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}

	@Override
	public ResultTx<SysUsuario> actualizar(SysUsuario objUsuario) {
		String urlParam = urlTemplateServistGen+URI_USER+"u/";					
		Object result = UtilesJSON.getObjectJsonFiltro(urlParam,objUsuario,objUsuario.getClass());
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}

	@Override
	public ResultTx<SysUsuario> eliminar(SysUsuario objUsuario) {
		String urlParam = urlTemplateServistGen+URI_USER+"d/";					
		Object result = UtilesJSON.deleteObjectJson(urlParam,objUsuario,objUsuario.getClass());
		return (result !=null ? ResultTx.ok((SysUsuario)result): ResultTx.error(objUsuario,UtilesCommons.TYPE_COD_NULL));
	}


}
