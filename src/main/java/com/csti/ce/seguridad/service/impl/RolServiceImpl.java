package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.AccesoDAO;
import com.csti.ce.seguridad.dao.RolDAO;
import com.csti.ce.seguridad.dao.SistemaDAO;
import com.csti.ce.seguridad.domain.Rol;
import com.csti.ce.seguridad.domain.Sistema;
import com.csti.ce.seguridad.service.RolService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RolServiceImpl implements RolService {
	@Autowired
	RolDAO rolDAO;
	@Autowired
	AccesoDAO accesoDAO;
        @Autowired
        SistemaDAO sistemaDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response save(Rol entity) {
		Response respuesta = new Response();
                Sistema sistema = sistemaDAO.findById(2);
                entity.setSistema(sistema);
		rolDAO.save(entity);
		respuesta.messageINSERT();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response update(Rol entity) {
		Response respuesta = new Response();
                Sistema sistema = sistemaDAO.findById(2);
                entity.setSistema(sistema);
		rolDAO.update(entity);
		respuesta.messageUPDATE();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(Rol entity) {
		Response respuesta = new Response();
		if (accesoDAO.countByRol(entity.getIdRol()) == 0) {
                    Sistema sistema = sistemaDAO.findById(2);
                    entity.setSistema(sistema);
			rolDAO.delete(entity);
			respuesta.messageDELETE();
		} else {
			respuesta.setMessage("La Entidad Esta Relacionada.");
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly=true)
	public Rol get(Rol entity) {
		return rolDAO.getById(entity.getIdRol());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Rol> getAll() {
		return rolDAO.getAll();
	}

}
