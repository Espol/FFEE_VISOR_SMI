/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.service;

import com.csti.ce.seguridad.domain.Sistema;
import com.csti.ce.util.Response;
import java.util.List;

/**
 *
 * @author User
 */
public interface SistemaService {
    
    /**
	  * persiste o graba cada Sistema
	  * @param entity
	  */
	Response save(Sistema entity);
	 /**
	  * actualiza cada Sistema
	  * @param entity
	  */
	Response update (Sistema entity);
	 /**
	  * elimina o da de baja un Sistema
	  * @param entity
	  */
	Response delete(Sistema entity);
	 /**
	  * obtiene un Sistema para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 Sistema get(Sistema  entity);
	 /**
	  * listado de todos los Sistemaes
	  * @return
	  */
	 List<Sistema> getAll();
    
}
