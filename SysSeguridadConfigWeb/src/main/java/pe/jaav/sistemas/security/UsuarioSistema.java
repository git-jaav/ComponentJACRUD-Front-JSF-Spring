package pe.jaav.sistemas.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pe.jaav.sistemas.seguridadgeneral.model.domain.SysUsuario;

import java.util.Collection;

public class UsuarioSistema extends User{

	private static final long serialVersionUID = 1L;
	private SysUsuario login;

	public UsuarioSistema(SysUsuario login, Collection<? extends GrantedAuthority> authorities) {
		super(login.getUsuaUsuario(), login.getUsuaClave(), authorities);
		this.login = login;
	}

	public SysUsuario getLogin() {
		return login;
	}

	public void setLogin(SysUsuario login) {
		this.login = login;
	}




}
