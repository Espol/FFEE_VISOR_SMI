/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.EmailNotificacionService;
import com.csti.ce.constant.MailConstant;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping(value = "config/*/**")
public class EmailNotificacionController extends GenericController {

    @Autowired
    EmailNotificacionService emailNotificacionService;

    @RequestMapping(value = "config/emailnotificacion/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/emailNotificacion");
        return modelAndView;
    }

    @RequestMapping(value = "config/emailnotificacion/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> data = new HashMap<>();
            List<Parametro> list = emailNotificacionService.getParametros(this.getSociedadRUC());
            data.put("idCorreoNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_SMPT).getIdParametro());
            data.put("correoNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_SMPT).getParametroLow());
            data.put("idFirmaNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_FIRMA).getIdParametro());
            data.put("firmaNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_FIRMA).getParametroLow());
            data.put("idClaveNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_CLAVES).getIdParametro());
            data.put("claveNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_CLAVES).getParametroLow());
            data.put("idPuntoEmisionNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_PUNTO_EMISION).getIdParametro());
            data.put("puntoEmisionNotificacion", util.getParametro(list, MailConstant.MAIL_NOTIFICACION_PUNTO_EMISION).getParametroLow());
            data.put("diaCaducidad", util.obtenerDias(util.getParametro(list, ParametroConstants.FIRMA_FECHA_CADUCIDAD).getParametroLow()));
            String PorcentajeTotal = util.getParametro(list, MailConstant.CLAVES_TOTAL).getParametroLow();
            String PorcentajeParcial = util.getParametro(list, MailConstant.CLAVES_PORCENT_NOTIFI).getParametroLow();
            BigDecimal porcentajeActual = util.getPorcentageClave(PorcentajeTotal, PorcentajeParcial);
            data.put("claveUsadas", porcentajeActual);
            response.setData(data);
            response.setMessage("Los datos se Cargaron Correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "config/emailnotificacion/edit")
    public @ResponseBody
    Response edit(@RequestParam("idCorreoNotificacion") int idEmail, @RequestParam("correoNotificacion") String email,
            @RequestParam("idFirmaNotificacion") int idFirma, @RequestParam("firmaNotificacion") String firma,
            @RequestParam("idClaveNotificacion") int idClave, @RequestParam("claveNotificacion") String clave,
            @RequestParam("idPuntoEmisionNotificacion") int idPntEmision, @RequestParam("puntoEmisionNotificacion") String pntEmision) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idCorreoNotificacion", idEmail);
            parametros.put("correoNotificacion", email);
            parametros.put("idFirmaNotificacion", idFirma);
            parametros.put("firmaNotificacion", firma);
            parametros.put("idClaveNotificacion", idClave);
            parametros.put("claveNotificacion", clave);
            parametros.put("idPuntoEmisionNotificacion", idPntEmision);
            parametros.put("puntoEmisionNotificacion", pntEmision);
            response = emailNotificacionService.updateParametros(parametros, this.getSociedadRUC());
            response.setMessage("Correcto");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
