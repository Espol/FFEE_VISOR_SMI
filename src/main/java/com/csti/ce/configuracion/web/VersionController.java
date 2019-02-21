/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.VersionService;
import com.csti.ce.generic.GenericController;
import com.csti.ce.util.Response;
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
 * @author CSTICORP
 */
@Controller
@RequestMapping(value = "config/*/**")
public class VersionController extends GenericController {
    
    @Autowired
    private VersionService versionService;
    
    @RequestMapping(value = "config/version/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/version");
        return modelAndView;
    }
    
    @RequestMapping(value = "config/version/load")
    public @ResponseBody Response get(){
        Response respuesta = new Response();
        respuesta.setData(versionService.getVersionBySociedad(this.getSociedadRUC()));
        return respuesta;
    }
    
    @RequestMapping(value = "config/version/edit")
    public @ResponseBody Response edit(
    		@RequestParam("version") int version
    		, @RequestParam("url")  String url
    		){
        Response respuesta = new Response();
        versionService.updateVesionBySociedad(this.getSociedadRUC(), version);
        if(version == 1){
            respuesta.setMessage("Se actualizaron los datos correctamente");
        } else {
            respuesta.setMessage("Se actualizaron los datos correctamente");
        }
        return respuesta;
    }
    
    @RequestMapping(value = "config/portal/view")
    public ModelAndView portalWeb(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/portalWeb");
        return modelAndView;
    }
    
    @RequestMapping(value = "config/portal/load")
    public @ResponseBody Response getURLPortal(){
        Response respuesta = new Response();
        respuesta.setData(versionService.getVersionBySociedad(this.getSociedadRUC()));
        return respuesta;
    }
    
    @RequestMapping(value = "config/portal/edit")
    public @ResponseBody Response editURLPortal(
    		@RequestParam("url")  String url,
                @RequestParam("portalImp")  int portalImpl
    		){
        Response respuesta = new Response();
        versionService.updateURLPortalBySociedad(this.getSociedadRUC(), url, portalImpl);
        respuesta.setMessage("Se actualizaron los datos correctamente");
        return respuesta;
    }
    
}
