/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.service.impl;

import com.csti.ce.seguridad.dao.SistemaDAO;
import com.csti.ce.seguridad.domain.Sistema;
import com.csti.ce.seguridad.service.SistemaService;
import com.csti.ce.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class SistemaServiceImpl implements SistemaService {

    @Autowired
    SistemaDAO sistemaDAOhibernate;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response save(Sistema entity) {
        Response response = new Response();
        sistemaDAOhibernate.save(entity);
        response.messageINSERT();
        return response;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response update(Sistema entity) {
        Response response = new Response();
        sistemaDAOhibernate.update(entity);
        response.messageUPDATE();
        return response;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response delete(Sistema entity) {
        Response response = new Response();
        sistemaDAOhibernate.delete(entity);
        response.messageDELETE();
        return response;
    }

    @Override
    public Sistema get(Sistema entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Sistema> getAll() {
         return sistemaDAOhibernate.findAll();
    }

}
