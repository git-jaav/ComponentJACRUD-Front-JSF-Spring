package pe.jaav.sistemas.general.service;

import java.util.List;
import java.util.Optional;

import pe.jaav.common.util.model.PaginacionModel;
import pe.jaav.common.util.model.ResultTx;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

public interface UsuarioService {
	
	public Optional<SysUsuario> obtenerLogin(String usuario , String clave);
	public Optional<SysUsuario> obtenerLoginAuth(Object objCredential);
	
	public Optional<SysUsuario> obtenerPorID(Integer objUsuario,String currentToken);
	public int contarListado(SysUsuario objUsuario,String currentToken);	
	public List<SysUsuario> listar(SysUsuario objUsuario,boolean paginable,String currentToken);
	
	public PaginacionModel<SysUsuario> listar(SysUsuario objUsuario,String currentToken);
	
	public ResultTx<SysUsuario> guardar(SysUsuario objUsuario,String currentToken);
	public ResultTx<SysUsuario> actualizar(SysUsuario objUsuario,String currentToken);
	public ResultTx<SysUsuario> eliminar(SysUsuario objUsuario,String currentToken);
	
}
