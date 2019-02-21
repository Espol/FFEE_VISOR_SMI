package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Modulo;
import com.csti.ce.util.Response;

public interface ModuloService {
	 /**
	  * persiste o graba cada modulo
	  * @param entity
	  */
	 Response save(Modulo entity);
	 /**
	  * actualiza cada modulo
	  * @param entity
	  */
	 Response update (Modulo entity);
	 /**
	  * elimina o da de baja un modulo
	  * @param entity
	  */
	 Response delete(Modulo entity);
	 /**
	  * obtiene un modulo para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 Modulo get(Modulo  entity);
	 /**
	  * listado de todos los modulos
	  * @return
	  */
	 List<Modulo> getAll();
}
