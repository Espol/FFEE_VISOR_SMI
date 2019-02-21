package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Opcion;
import com.csti.ce.util.Response;

public interface OpcionService {
	/**
	 * persiste o graba cada Rol
	 * 
	 * @param entity
	 */
	Response save(Opcion entity);

	/**
	 * actualiza cada Rol
	 * 
	 * @param entity
	 */
	Response update(Opcion entity);

	/**
	 * elimina o da de baja un Rol
	 * 
	 * @param entity
	 */
	Response delete(Opcion entity);

	/**
	 * obtiene un Rol para editar o algo especifico
	 * 
	 * @param entity
	 * @return
	 */
	Opcion get(Opcion entity);

	/**
	 * listado de todos los Roles
	 * 
	 * @return
	 */
	List<Opcion> getAll();

	List<Opcion> getByQuery(String value);
}
