/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.service;

import com.csti.ce.monitor.view.UsuarioSistemaView;
import com.csti.ce.seguridad.domain.UsuarioSistema;
import com.csti.ce.util.Response;
import java.util.List;

/**
 *
 * @author User
 */
public interface UsuarioSistemaService {
    /**
	  * persiste o graba cada UsuarioSistema
	  * @param entity
	  */
	Response save(UsuarioSistema entity);
	 /**
	  * actualiza cada UsuarioSistema
	  * @param entity
	  */
	Response update (UsuarioSistema entity);
	 /**
	  * elimina o da de baja un UsuarioSistema
	  * @param entity
	  */
	Response delete(UsuarioSistema entity);
	 /**
	  * obtiene un UsuarioSistema para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 UsuarioSistema get(UsuarioSistema  entity);
	 /**
	  * listado de todos los UsuarioSistemaes
	  * @return
	  */
	 List<UsuarioSistemaView> getAll();
}
