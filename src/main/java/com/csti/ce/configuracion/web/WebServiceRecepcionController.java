/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.configuracion.service.WebServiceRecepcionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;
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
public class WebServiceRecepcionController extends GenericController {

    @Autowired
    WebServiceRecepcionService webServiceRecepcionService;

    @RequestMapping(value = "config/wsrecepcion/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/wsSriRecepcion");
        return modelAndView;
    }

    @RequestMapping(value = "config/wsrecepcion/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> data = new HashMap<>();
            List<Parametro> list = webServiceRecepcionService.getParametros(this.getSociedadRUC());
            data.put("url", util.getParametro(list, ParametroConstants.RECEPCION_WSDL).getParametroLow());
            data.put("id-url", util.getParametro(list, ParametroConstants.RECEPCION_WSDL).getIdParametro());
            data.put("nroIntento", util.getParametro(list, ParametroConstants.RECEPCION_INTENTOS).getParametroLow());
            data.put("id-nroIntento", util.getParametro(list, ParametroConstants.RECEPCION_INTENTOS).getIdParametro());
            data.put("tiempoEspera", util.getParametro(list, ParametroConstants.RECEPCION_ESPERA).getParametroLow());
            data.put("id-tiempoEspera", util.getParametro(list, ParametroConstants.RECEPCION_ESPERA).getIdParametro());
            response.setData(data);
            response.setMessage("Se cargarón los datos correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "config/wsrecepcion/edit")
    public @ResponseBody
    Response edit(@RequestParam("id-url") String idUrl, @RequestParam("url") String url,
            @RequestParam("id-nroIntento") String idNroIntento, @RequestParam("nroIntento") String nroIntento,
            @RequestParam("id-tiempoEspera") String idTiempoEspera, @RequestParam("tiempoEspera") String tiempoEspera) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("id-url", idUrl);
            data.put("url", url);
            data.put("id-nroIntento", idNroIntento);
            data.put("nroIntento", nroIntento);
            data.put("id-tiempoEspera", idTiempoEspera);
            data.put("tiempoEspera", tiempoEspera);
            response = webServiceRecepcionService.updateParametros(data, this.getSociedadRUC());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
