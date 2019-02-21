/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.configuracion.service.EmailNotificacionService;
import com.csti.ce.constant.MailConstant;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;
import java.math.BigDecimal;
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
public class EmailNotificacionServiceImpl implements EmailNotificacionService {

    @Autowired
    ParametroDAO parametroDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<>();
        String programa = parametroDAO.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.MAIL_NOTIFICACION_SMPT));
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.MAIL_NOTIFICACION_FIRMA));
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.MAIL_NOTIFICACION_CLAVES));
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.MAIL_NOTIFICACION_PUNTO_EMISION));
        list.add(parametroDAO.getParametroByCampo(programa, ParametroConstants.FIRMA_FECHA_CADUCIDAD));
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.CLAVES_TOTAL));
        list.add(parametroDAO.getParametroByCampo(programa, MailConstant.CLAVES_PORCENT_NOTIFI));
 
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {
        Response response = new Response();
        try {
            // EMAIL NOTIFICACION
            int idEmail = Integer.parseInt(parametros.get("idCorreoNotificacion").toString());
            Parametro emailNotificacion = parametroDAO.getById(idEmail);
            emailNotificacion.setParametroLow(parametros.get("correoNotificacion").toString());
            parametroDAO.update(emailNotificacion);

            // FIRMA NOTIFICACION
            int idFirmaNotificacion = Integer.parseInt(parametros.get("idFirmaNotificacion").toString());
            Parametro firmaNotificacion = parametroDAO.getById(idFirmaNotificacion);
            firmaNotificacion.setParametroLow(parametros.get("firmaNotificacion").toString());
            parametroDAO.update(firmaNotificacion);

            // CLAVE NOTIFICACION
            int idClaveNotificacion = Integer.parseInt(parametros.get("idClaveNotificacion").toString());
            Parametro claveNotificacion = parametroDAO.getById(idClaveNotificacion);
            claveNotificacion.setParametroLow(parametros.get("claveNotificacion").toString());
            parametroDAO.update(claveNotificacion);

            // PUNTO DE EMISION NOTIFICACION
            int idPuntoEmisionNotificaion = Integer.parseInt(parametros.get("idPuntoEmisionNotificacion").toString());
            Parametro puntoEmisionNotificaion = parametroDAO.getById(idPuntoEmisionNotificaion);
            puntoEmisionNotificaion.setParametroLow(parametros.get("puntoEmisionNotificacion").toString());
            parametroDAO.update(puntoEmisionNotificaion);

            response.setMessage(MensajeConstante.NOTIFICACION_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.NOTIFICACION_ERROR);
            response.setSuccess(false);
            return response;
        }
    }

}
