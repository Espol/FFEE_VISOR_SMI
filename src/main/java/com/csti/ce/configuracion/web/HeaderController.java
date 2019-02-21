/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.web;

import com.csti.ce.generic.GenericController;
import com.csti.ce.seguridad.domain.UsuarioApp;
import com.csti.ce.util.Constants;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
//@Controller
//@RequestMapping(value = "config/*/**")
public class HeaderController extends GenericController {
    
//    @RequestMapping(value = "/config/header/view")
//    public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("configuracion/header");
//        return modelAndView;
//    }
//    
//    @RequestMapping(value = "/config/header/load")
//    public @ResponseBody Map<String, Object> get(HttpServletRequest request){
//        Map<String, Object> response = new HashMap<String, Object>();
//        HttpSession httpSession = request.getSession();
//        UsuarioApp usuario = (UsuarioApp) httpSession.getAttribute(Constants.USER_SESSION);
//        response.put("idNombre", usuario.getUsuario().getUserName());
//        return response;
//    }
//    
}
