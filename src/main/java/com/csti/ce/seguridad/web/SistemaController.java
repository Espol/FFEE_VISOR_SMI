/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.web;

import com.csti.ce.seguridad.domain.Sistema;
import com.csti.ce.seguridad.service.SistemaService;
import com.csti.ce.util.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("sistema/*")
public class SistemaController {

    @Autowired
    SistemaService sistemaServiceImpl;

    @RequestMapping(value = "view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("seguridad/sistema");
        return modelAndView;
    }

    @RequestMapping(value = "loadAll")
    public @ResponseBody
    Map<String, Object> getAll() {
        List<Sistema> list = sistemaServiceImpl.getAll();
        Map<String, Object> response = new HashMap<>();
        response.put("rows", list);
        response.put("total", list.size());
        return response;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    Response save(@RequestParam("accion") String accion, @ModelAttribute Sistema entity, BindingResult result) throws Exception {
        Response respuesta = new Response();
        entity.setUsuarioSistemas(new HashSet(1));
        if (accion.equalsIgnoreCase("add")) {
            respuesta = sistemaServiceImpl.save(entity);
        } else if (accion.equalsIgnoreCase("update")) {
            respuesta = sistemaServiceImpl.update(entity);
        }
        return respuesta;

    }

    @RequestMapping(value = "edit/{id}")
    public @ResponseBody
    Sistema edit(@PathVariable(value = "id") int id, ModelMap model) throws Exception {
        return sistemaServiceImpl.get(new Sistema(id));
    }

    @RequestMapping(value = "delete/{id}")
    public @ResponseBody
    Response delete(@PathVariable(value = "id") int id) throws Exception {
        return sistemaServiceImpl.delete(new Sistema(id));
    }
}
