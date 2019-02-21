package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.util.Response;

public interface AccesoService {
	/**
	 * persiste o graba cada Acceso
	 * 
	 * @param entity
	 */
	Response save(Acceso entity);

	/**
	 * actualiza cada Acceso
	 * 
	 * @param entity
	 */
	Response update(Acceso entity);

	/**
	 * elimina o da de baja un Acceso
	 * 
	 * @param entity
	 */
	Response delete(Acceso entity);

	/**
	 * obtiene un Acceso para editar o algo especifico
	 * 
	 * @param entity
	 * @return
	 */
	Acceso get(Acceso entity);

	/**
	 * listado de todos los Usuario
	 * 
	 * @return
	 */
	List<Acceso> getAll();
	

	List<Acceso> getAllByPagination(int initRow, int countRow);
}
