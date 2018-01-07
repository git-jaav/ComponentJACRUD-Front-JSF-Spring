package pe.jaav.sistemas.general.service;

import java.util.List;
import java.util.Optional;

import pe.jaav.common.util.model.PaginacionModel;
import pe.jaav.common.util.model.ResultTx;
import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

public interface UsuarioService {
	
	public Optional<SysUsuario> obtenerLogin(String usuario , String clave);
	
	public Optional<SysUsuario> obtenerPorID(Integer objUsuario);
	public int contarListado(SysUsuario objUsuario);	
	public List<SysUsuario> listar(SysUsuario objUsuario,boolean paginable);
	
	public PaginacionModel<SysUsuario> listar(SysUsuario objUsuario);
	
	public ResultTx<SysUsuario> guardar(SysUsuario objUsuario);
	public ResultTx<SysUsuario> actualizar(SysUsuario objUsuario);
	public ResultTx<SysUsuario> eliminar(SysUsuario objUsuario);
	
}
