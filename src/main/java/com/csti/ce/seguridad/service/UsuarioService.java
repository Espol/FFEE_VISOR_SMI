package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.util.Response;

public interface UsuarioService {
	/**
	  * persiste o graba cada Usuario
	  * @param entity
	  */
	Response save(Usuario entity);
	 /**
	  * actualiza cada Usuario
	  * @param entity
	  */
	Response update (Usuario entity);
	 /**
	  * elimina o da de baja un Usuario
	  * @param entity
	  */
	Response delete(Usuario entity);
	 
	/**
	 * obtiene un Usuario para editar o algo especifico
	 * 
	 * @param entity
	 * @return
	 */
	Usuario get(Usuario entity);

	/**
	 * listado de todos los Usuario
	 * 
	 * @return
	 */
	List<Usuario> getAll();

	List<Usuario> getByQuery(String value);
}
