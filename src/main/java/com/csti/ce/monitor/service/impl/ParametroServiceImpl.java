/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.monitor.service.ParametroService; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
@Service
@Transactional
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroDAO parametroDAO;

    @Override
    @Transactional(readOnly = true)
    public Parametro getSociedad(String sociedad) {
        return parametroDAO.getSociedad(sociedad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parametro> getParametrosByPrograma(String programa) {
        return parametroDAO.getParametrosByPrograma(programa);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Parametro parametro) {
        parametroDAO.update(parametro);
    }

    @Override
    @Transactional(readOnly = true)
    public Parametro getParametroByCampo(String programa, String campo) {
        return parametroDAO.getParametroByCampo(programa, campo);
    }

    @Override
    @Transactional(readOnly = true)
    public Parametro getById(int id) {
        return parametroDAO.getById(id);
    }

}
