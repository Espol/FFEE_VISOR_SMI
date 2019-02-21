/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.configuracion.service.FirmaElectronicaService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.util.Response;

/**
 *
 * @author User
 */
@Controller
@RequestMapping(value = "config/*/**")
public class FirmaElectronicaController extends GenericController {

    @Autowired
    FirmaElectronicaService firmaElectronicaService;
    
    @Autowired
    AboutSystemService aboutSystemService;
    
    @RequestMapping(value = "config/firmaelectronica/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/firmaElectronica");
        return modelAndView;
    }

    @RequestMapping(value = "config/firmaelectronica/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
        	response.setData(aboutSystemService.getConfiguracionFirma(this.getSociedadRUC()));
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "config/firmaelectronica/edit", method = RequestMethod.POST)
    public @ResponseBody
    Response edit(
//    		@RequestParam("base") String base,
            @RequestParam("firma") String firma,
            @RequestParam("clave") String clave,
            @RequestParam("fechaCaducidad") String fechaCaducidad,
            
            @RequestParam("iniNotifFirma") int iniNotifFirma,
            @RequestParam("intervalNotifFirma") int intervalNotifFirma,
            
            @RequestParam("mailNotificacion") String mailNotificacion
    		) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
        	String base ="";
//        	Pattern pat = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
//   	     	Matcher mat = pat.matcher(fechaCaducidad);
//   	     	if(mat.matches()){
	   	     	aboutSystemService.updateConfiguracionFirma(this.getSociedadRUC(), base, firma, clave, fechaCaducidad, iniNotifFirma, intervalNotifFirma, mailNotificacion);
	            response.setMessage("Se Editaron los datos Correctamente.");
	            response.setSuccess(true);
//   	     	} else {
//   	     	response.setMessage("Formato de Fecha 2015-01-23.");
//            response.setSuccess(false);
//   	     	}
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
