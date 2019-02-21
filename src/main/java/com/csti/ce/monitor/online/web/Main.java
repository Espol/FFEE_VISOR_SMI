/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.online.web;

import com.csti.ce.monitor.service.ParametroService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("app/*/**")
public class Main {

    @RequestMapping(value = "/app/init")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        try { 
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("application");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/app/welcome")
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("online/welcome");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }
}
