/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.configuracion.service.SapConexionService;
import com.csti.ce.constant.ConexionSAPConstants;
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
 * @author Usuario
 */
@Controller
@RequestMapping(value = "config/*/**")
public class SapConexionController extends GenericController {

    @Autowired
    SapConexionService sapConexionServiceImpl;
    
    @Autowired
    private AboutSystemService aboutSystemService;

    @RequestMapping(value = "config/sap/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/sapConexion");
        return modelAndView;
    }

    @RequestMapping(value = "config/sap/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            response.setData(aboutSystemService.getConfiguracionSAPBySociedad(this.getSociedadRUC()));
            response.setMessage("Se cargarón los datos correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "config/sap/edit")
    public @ResponseBody
    Response edit(@RequestParam("mandante") String mandante,
            @RequestParam("usuarioSap") String usuarioSap,
            @RequestParam("passwordSap") String passwordSap,
            @RequestParam("ipSap") String ipSap,
            @RequestParam("idiomaSap") String idiomaSap,
            @RequestParam("instanciaSap") String instanciaSap,
            @RequestParam("idSistema") String idSistema) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
        	aboutSystemService.updateConfiguracionSAPBySociedad(this.getSociedadRUC(), mandante, usuarioSap, passwordSap, ipSap, idiomaSap, instanciaSap, idSistema);
        	response.setMessage("Se cargarón los datos correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
