package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

 import com.csti.ce.seguridad.dao.AccesoDAO;
import com.csti.ce.seguridad.dao.OpcionDAO;
import com.csti.ce.seguridad.domain.Opcion;
import com.csti.ce.seguridad.service.OpcionService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class OpcionServiceImpl implements OpcionService {
	@Autowired
	OpcionDAO opcionDAO;
	@Autowired
	AccesoDAO accesoDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response save(Opcion entity) {
		Response respuesta = new Response();
		opcionDAO.save(entity);
		respuesta.messageINSERT();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response update(Opcion entity) {
		Response respuesta = new Response();
		opcionDAO.update(entity);
		respuesta.messageUPDATE();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(Opcion entity) {
		Response respuesta = new Response();
		if (accesoDAO.countByRol(entity.getIdOpcion()) == 0) {
			opcionDAO.delete(entity);
			respuesta.messageDELETE();
		} else {
			respuesta.setMessage("La Entidad Esta Relacionada.");
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly=true)
	public Opcion get(Opcion entity) {
		return opcionDAO.getById(entity.getIdOpcion());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Opcion> getAll() {
		return opcionDAO.getAll();
	}
	@Override
	@Transactional(readOnly = true)
	public List<Opcion> getByQuery(String value) {
		return opcionDAO.getByQuery(value);
	};

}
