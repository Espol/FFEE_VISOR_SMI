package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Rol;
import com.csti.ce.util.Response;

public interface RolService {
	/**
	  * persiste o graba cada Rol
	  * @param entity
	  */
	Response save(Rol entity);
	 /**
	  * actualiza cada Rol
	  * @param entity
	  */
	Response update (Rol entity);
	 /**
	  * elimina o da de baja un Rol
	  * @param entity
	  */
	Response delete(Rol entity);
	 /**
	  * obtiene un Rol para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 Rol get(Rol  entity);
	 /**
	  * listado de todos los Roles
	  * @return
	  */
	 List<Rol> getAll();
}
