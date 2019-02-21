/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.web;

import com.csti.ce.monitor.view.UsuarioSistemaView;
import com.csti.ce.seguridad.service.UsuarioSistemaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("usuasis/*")
public class UsuarioSistemaController {
    
    @Autowired
    UsuarioSistemaService sistemaSistemaService;
    
    @RequestMapping(value = "view")
        public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
            ModelAndView modelAndView =  new ModelAndView();
            modelAndView.setViewName("seguridad/usuariosistema");
            return modelAndView;
        }
        
        @RequestMapping(value = "loadAll")
	public @ResponseBody
	Map<String, Object> getAll() {
		List<UsuarioSistemaView> list = sistemaSistemaService.getAll();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("rows", list);
		response.put("total", list.size());
		return response;
	}
    
}
