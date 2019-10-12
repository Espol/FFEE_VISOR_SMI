/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.configuracion.service.EmailService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.util.Response;

/**
 *
 * @author User
 */
@Controller
@RequestMapping(value = "config/*/**")
public class EmailXTipoDocController extends GenericController {

    @Autowired
    EmailService emailService;
    
    @Autowired
    private AboutSystemService aboutSystemService;

    @RequestMapping(value = "config/emailxtipodoc/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/emailXTipoDoc");
        return modelAndView;
    }

    @RequestMapping(value = "config/emailxtipodoc/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try{
            response.setData(aboutSystemService.getSociedadByRuc( this.getSociedadRUC() ) );
        }catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        
        return response;
    }

    @RequestMapping(value = "config/emailxtipodoc/edit")
    public @ResponseBody
    Response edit(
    		@RequestParam("mailFactura") String mailFactura, 
    		@RequestParam("mailCredito") String mailCredito,
            @RequestParam("mailDebito") String mailDebito, 
            @RequestParam("mailGuia") String mailGuia,
            @RequestParam("mailRetencion") String mailRetencion,
            @RequestParam("mailLiquidacionCompra") String mailLiquidacionCompra,
            @RequestParam("iniTimeAvailableCorrecion") int iniTimeAvailableCorrecion,
            @RequestParam("intervalNotifDocRechazado") int intervalNotifDocRechazado
   ){
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
        	
        	Sociedad sociedad = aboutSystemService.getSociedadByRuc( this.getSociedadRUC() );
        	sociedad.setMailFactura(mailFactura);
        	sociedad.setMailCredito(mailCredito);
        	sociedad.setMailDebito(mailDebito);
        	sociedad.setMailGuia(mailGuia);
        	sociedad.setMailRetencion(mailRetencion);
        	sociedad.setMailLiquidacionCompra(mailLiquidacionCompra);
                
        	sociedad.setIniTimeAvailableCorrecion(iniTimeAvailableCorrecion-1);//se resta 1, porque se añade 59 minutos en el thread
        	sociedad.setIntervalNotifDocRechazado(intervalNotifDocRechazado);
        	
        	aboutSystemService.updateSociedad(sociedad);
            response.setMessage("Se actualizaron los datos correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
