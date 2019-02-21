/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.configuracion.service.WebServiceAutorizacionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.dao.hibernate.ParametroDAOHibernate;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class WebServiceAutorizacionServiceImpl implements WebServiceAutorizacionService {

    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<Parametro>();
        String parametroHigh = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.AUTORIZACION_WSDL));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.AUTORIZACION_INTENTOS));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.AUTORIZACION_ESPERA));
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {
        Response response = new Response();
        try {
            // Url
            int idUrl = Integer.parseInt(parametros.get("id-url").toString());
            Parametro parametroUrl = parametroDAOHibernate.getById(idUrl);
            parametroUrl.setParametroLow(parametros.get("url").toString());
            parametroDAOHibernate.update(parametroUrl);

            // Número de Intento
            int idNroIntento = Integer.parseInt(parametros.get("id-nroIntento").toString());
            Parametro intento = parametroDAOHibernate.getById(idNroIntento);
            intento.setParametroLow(parametros.get("nroIntento").toString());
            parametroDAOHibernate.update(intento);

            //Tiempo de Espera
            int idTiempoEspera = Integer.parseInt(parametros.get("id-tiempoEspera").toString());
            Parametro tiempoEspera = parametroDAOHibernate.getById(idTiempoEspera);
            tiempoEspera.setParametroLow(parametros.get("tiempoEspera").toString());
            parametroDAOHibernate.update(tiempoEspera);
            response.setMessage(MensajeConstante.WEB_SERVICE_AUTORIZACION_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.WEB_SERVICE_AUTORIZACION_ERROR);
            response.setSuccess(false);
            return response;
        }
    }
}
