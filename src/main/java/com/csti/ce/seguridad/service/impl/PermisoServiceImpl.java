package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.PermisoDAO;
import com.csti.ce.seguridad.domain.Permiso;
import com.csti.ce.seguridad.service.PermisoService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    PermisoDAO permisoDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response save(Permiso entity) {
        Response response = new Response();
        permisoDAO.save(entity);
        response.messageINSERT();
        return response;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response update(Permiso entity) {
        Response response = new Response();
        permisoDAO.update(entity);
        response.messageUPDATE();
        return response;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response delete(Permiso entity) {
        Response respuesta = new Response();
        permisoDAO.delete(entity);
        respuesta.messageDELETE();
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public Permiso get(Permiso entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permiso> getAll() {
        return permisoDAO.getAll();
    }

}
