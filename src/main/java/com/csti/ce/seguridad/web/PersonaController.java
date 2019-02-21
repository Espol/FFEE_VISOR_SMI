/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.web;

import com.csti.ce.seguridad.domain.Persona;
import com.csti.ce.seguridad.service.PersonaService;
import com.csti.ce.util.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping(value = "persona/*")
public class PersonaController {
    
    @Autowired
    PersonaService personaService;
    
    @RequestMapping(value = "view")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("seguridad/persona");
            return modelAndView;
        }
        
        @RequestMapping(value = "loadAll")
        public @ResponseBody Map<String,Object> getAll(){
            Map<String, Object> response = new HashMap<>();
            List<Persona> list = personaService.getAll();
            response.put("rows", list);
            response.put("total", list.size());
            return response;
        }
        
        @RequestMapping(value = "save", method = RequestMethod.POST)
        public @ResponseBody Response save(@RequestParam("accion") String accion,@ModelAttribute Persona entity,BindingResult result){
            Response response = new Response();
            if(accion.equalsIgnoreCase("add")){
                response = personaService.save(entity);
            } else if(accion.equalsIgnoreCase("update")) {
                response = personaService.update(entity);
            }
            return response;
        }
        
    
}
