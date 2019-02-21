package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;
import com.csti.ce.util.Response;

public interface UsuarioRolService {
	/**
	 * persiste o graba cada Usuario
	 * 
	 * @param entity
	 */
	Response save(UsuarioRol entity);

	/**
	 * actualiza cada Usuario
	 * 
	 * @param entity
	 */
	Response update(UsuarioRol entity);
	
	Response updateByAdministrador(UsuarioRol usuarioRol);

	/**
	 * elimina o da de baja un Usuario
	 * 
	 * @param entity
	 */
	Response delete(UsuarioRol entity);
	
	Response deleteByAdministrador(UsuarioRol usuarioRol);

	/**
	 * obtiene un Usuario para editar o algo especifico
	 * 
	 * @param entity
	 * @return
	 */
	UsuarioRol get(UsuarioRol entity);

	/**
	 * listado de todos los Usuario
	 * 
	 * @return
	 */
	List<UsuarioRol> getAll();
	

    List<UsuarioRol> getAllBySociedad(String ruc);

    List<UsuarioRol> getAllBySociedadPagination(int initRow, int rows, String ruc);
    
        
        /**
	 * obtiene un Usuario para editar o algo especifico
	 * 
	 * @param entity
	 * @return
	 */
	UsuarioRol getUsuarioRolByIdUsuario(int IdUsuario);
}
