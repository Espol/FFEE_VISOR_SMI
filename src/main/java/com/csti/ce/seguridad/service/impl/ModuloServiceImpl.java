package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.ModuloDAO;
import com.csti.ce.seguridad.dao.OpcionDAO;
import com.csti.ce.seguridad.dao.SistemaDAO;
import com.csti.ce.seguridad.domain.Modulo;
import com.csti.ce.seguridad.domain.Sistema;
import com.csti.ce.seguridad.service.ModuloService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ModuloServiceImpl implements ModuloService {

    @Autowired
    ModuloDAO moduloDAO;
    @Autowired
    SistemaDAO SistemaDAO;

    Sistema sistema;

    @Autowired
    OpcionDAO opcionDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response save(Modulo entity) {
        Response respuesta = new Response();
        sistema = SistemaDAO.findById(2);
        entity.setSistema(sistema);
        moduloDAO.save(entity);
        respuesta.messageINSERT();
        return respuesta;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response update(Modulo entity) {
        Response respuesta = new Response();
        sistema = SistemaDAO.findById(2);
        entity.setSistema(sistema);
        moduloDAO.update(entity);
        respuesta.messageUPDATE();
        return respuesta;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response delete(Modulo entity) {
        Response respuesta = new Response();
        if (opcionDAO.countByModulo(entity.getIdModulo()) == 0) {
            sistema = SistemaDAO.findById(2);
            entity.setSistema(sistema);
            moduloDAO.delete(entity);
            respuesta.messageDELETE();
        } else {
            respuesta.setMessage("La Entidad Esta Relacionada.");
        }
        return respuesta;
    }

	@Override
	@Transactional(readOnly = true)
	public Modulo get(Modulo entity) {
		return moduloDAO.getById(entity.getIdModulo());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Modulo> getAll() {
		return moduloDAO.getAll();
	}

}
