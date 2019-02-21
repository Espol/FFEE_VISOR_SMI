package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.AccesoDAO;
import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.seguridad.service.AccesoService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class AccesoServiceImpl implements AccesoService {
	@Autowired
	AccesoDAO accesoDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response save(Acceso entity) {
		Response respuesta = new Response();
		if (accesoDAO.existAcceso(entity)) {
			respuesta.setMessage("Ya Existe un registro con el mismo GUI y Rol indicado.");
		}else{
			accesoDAO.save(entity);
			respuesta.messageINSERT();
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response update(Acceso entity) {
		Response respuesta = new Response();
		accesoDAO.update(entity);
		respuesta.messageUPDATE();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(Acceso entity) {
		Response respuesta = new Response();
//		if (usuarioRolDAO.countByUsuario(entity.getIdUsuarioRol()) == 0) {
			accesoDAO.delete(entity);
			respuesta.messageDELETE();
//		} else {
//			respuesta.setMessage("La Entidad Esta Relacionada.");
//		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly=true)
	public Acceso get(Acceso entity) {
		return accesoDAO.getById(entity.getIdAcceso());
	}

	@Override
	@Transactional(readOnly=true)
	public List<Acceso> getAll() {
		return accesoDAO.getAll();
	}


	@Override
	@Transactional(readOnly=true)
	public List<Acceso> getAllByPagination(int initRow, int countRow) {
		return accesoDAO.getAllByPagination(initRow,countRow);
	}

}
