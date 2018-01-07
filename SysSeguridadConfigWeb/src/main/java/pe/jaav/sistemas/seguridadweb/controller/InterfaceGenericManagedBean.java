package pe.jaav.sistemas.seguridadweb.controller;

public interface InterfaceGenericManagedBean {
	
	public void cargarObjetoFiltros(int first, int pageSize,int counter);
	public void btnBuscar() ;
    public void btnGuardar();   
    public void btnAceptar() ;
    public void btnCancelar();
   
    public void btnNuevo() ;
    public void btnModificar();
    public void btnVer();
    public void btnEliminar();
        
    public void abrirRegistro();
    
		
}
