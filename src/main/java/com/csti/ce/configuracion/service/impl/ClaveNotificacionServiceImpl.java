/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.service.impl;

import com.csti.ce.configuracion.service.ClaveNotificacionService;
import com.csti.ce.constant.MailConstant;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import java.util.ArrayList;
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
public class ClaveNotificacionServiceImpl implements ClaveNotificacionService {
    
    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametro(String sociedad) {
        List<Parametro> list = new ArrayList<>();
        String parametroHigh = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.CLAVES_TOTAL));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.CLAVES_PORCENT_NOTIFI));
        return list;
    }
}
