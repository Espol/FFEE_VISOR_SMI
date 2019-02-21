package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Permiso;
import com.csti.ce.util.Response;

public interface PermisoService {
	/**
	  * persiste o graba cada Permiso
	  * @param entity
	  */
	Response save(Permiso entity);
	 /**
	  * actualiza cada Permiso
	  * @param entity
	  */
	Response update (Permiso entity);
	 /**
	  * elimina o da de baja un Permiso
	  * @param entity
	  */
	Response delete(Permiso entity);
	 /**
	  * obtiene un Permiso para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 Permiso get(Permiso  entity);
	 /**
	  * listado de todos los Permisoes
	  * @return
	  */
	 List<Permiso> getAll();
}
