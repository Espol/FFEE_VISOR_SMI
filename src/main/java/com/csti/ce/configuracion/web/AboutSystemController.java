/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.configuracion.service.AdministracionConfiguracionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
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
public class AboutSystemController extends GenericController {
	
	@Autowired
	private AboutSystemService aboutSystemService;
    
    @RequestMapping(value = "config/about/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/aboutSystem");
        return modelAndView;
    }
    
    @RequestMapping(value = "config/about/load")
    public @ResponseBody
    Response get() {
    	
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        String path = aboutSystemService.getPathBySociedad(this.getSociedadRUC());
        response.setData(path);
        return response;
    }
    
    @RequestMapping(value = "config/about/edit")
    public @ResponseBody
    Response edit() {
        Response response = new Response();
        
        return response;
    }
}
