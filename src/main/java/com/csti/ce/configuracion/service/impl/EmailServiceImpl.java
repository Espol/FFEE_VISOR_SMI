/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.constant.MailConstant;
import com.csti.ce.configuracion.service.EmailService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.monitor.dao.ParametroDAO;
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
public class EmailServiceImpl implements EmailService {

    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<Parametro>();
        String parametroHigh = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.MAIL_HOST));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.MAIL_USUARIO));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.MAIL_PASSWORD));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, MailConstant.MAIL_NOTIFICACION_SMPT));
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {
        Response response = new Response();
        try {
            // HOST DE CORREO
            int idHostCorreo = Integer.parseInt(parametros.get("id-hostCorreo").toString());
            Parametro parametroHostCorreo = parametroDAOHibernate.getById(idHostCorreo);
            parametroHostCorreo.setParametroLow(parametros.get("hostCorreo").toString());
            parametroDAOHibernate.update(parametroHostCorreo);

            // USUARIO DE CORREO
            int idUsuarioCorreo = Integer.parseInt(parametros.get("id-usuarioCorreo").toString());
            Parametro usuarioCorreo = parametroDAOHibernate.getById(idUsuarioCorreo);
            usuarioCorreo.setParametroLow(parametros.get("usuarioCorreo").toString());
            parametroDAOHibernate.update(usuarioCorreo);

            // PASSWORD DE CORREO
            int idPassword = Integer.parseInt(parametros.get("id-password").toString());
            Parametro password = parametroDAOHibernate.getById(idPassword);
            password.setParametroLow(parametros.get("password").toString());
            parametroDAOHibernate.update(password);

            // EMAIL DE NOTIFICACIÓN
            int idEmailNotificacion = Integer.parseInt(parametros.get("id-emailNotificaion").toString());
            Parametro emailNotificacion = parametroDAOHibernate.getById(idEmailNotificacion);
            emailNotificacion.setParametroLow(parametros.get("emailNotificacion").toString());
            parametroDAOHibernate.update(emailNotificacion);
            response.setMessage(MensajeConstante.EMAIL_NOTIFICACION_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.EMAIL_NOTIFICACION_ERROR);
            response.setSuccess(false);
            return response;
        }
    }
}
