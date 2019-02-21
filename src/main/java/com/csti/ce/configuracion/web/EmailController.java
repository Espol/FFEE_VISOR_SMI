/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.constant.MailConstant;
import com.csti.ce.configuracion.beanXML.MailSetting;
import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.configuracion.service.EmailService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;	
import javax.xml.bind.Unmarshaller;

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
public class EmailController extends GenericController {

    @Autowired
    EmailService emailService;
    
    @Autowired
    private AboutSystemService aboutSystemService;

    @RequestMapping(value = "config/email/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/email");
        return modelAndView;
    }

    @RequestMapping(value = "config/email/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try{
            response.setData(aboutSystemService.getCorreoBySociedad(this.getSociedadRUC()));
        }catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        return response;
    }

    @RequestMapping(value = "config/email/edit")
    public @ResponseBody
    Response edit(@RequestParam("hostCorreo") String hostCorreo,
            @RequestParam("usuarioCorreo") String usuarioCorreo,
            @RequestParam("password") String password,
            @RequestParam("puerto") String puerto) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            aboutSystemService.updateCorreoBySociedad(this.getSociedadRUC(), hostCorreo, puerto, usuarioCorreo, password);
            response.setMessage("Se Actualizaron los datos correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
