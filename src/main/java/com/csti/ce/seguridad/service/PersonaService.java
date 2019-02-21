/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.service;

import com.csti.ce.seguridad.domain.Persona;
import com.csti.ce.util.Response;
import java.util.List;

/**
 *
 * @author User
 */
public interface PersonaService {
    
    Response save(Persona entity);
	 /**
	  * actualiza cada Persona
	  * @param entity
	  */
	Response update (Persona entity);
	 /**
	  * elimina o da de baja un Persona
	  * @param entity
	  */
	Response delete(Persona entity);
	 /**
	  * obtiene un Persona para editar o algo especifico
	  * @param entity
	  * @return
	  */
	 Persona get(Persona  entity);
	 /**
	  * listado de todos los Personaes
	  * @return
	  */
	 List<Persona> getAll();
    
}
