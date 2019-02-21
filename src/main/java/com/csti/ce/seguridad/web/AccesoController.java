package com.csti.ce.seguridad.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.seguridad.service.AccesoService;
import com.csti.ce.util.Response;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("gui/acceso/*")
public class AccesoController {

    @Autowired
    AccesoService accesoService;

    @RequestMapping(value = "view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("seguridad/acceso");
        return modelAndView;
    }

    @RequestMapping(value = "loadAll")
    public @ResponseBody
    Map<String, Object> getAll(
    		@RequestParam("page") int page,
            @RequestParam("rows") int rows
    		) {
    	int initRow = (page - 1) * rows;
        List<Acceso> listAcceso = accesoService.getAll();
        List<Acceso> listAccesoByPagination = accesoService.getAllByPagination(initRow, rows);
        
        List<Object> list = new ArrayList<Object>();
        for (Acceso acceso : listAccesoByPagination) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("opcion", acceso.getOpcion().getDescripcion());
            data.put("modulo", acceso.getOpcion().getModulo().getNombre());
            data.put("rol", acceso.getRol().getNombre());
            data.put("permiso", acceso.getPermiso().getNombre());
            list.add(data);
        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rows", list);
        response.put("total", listAcceso.size());
        return response;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    Response save(@RequestParam("accion") String accion, @ModelAttribute Acceso entity, BindingResult result) throws Exception {
        Response respuesta = new Response();
        if (accion.equalsIgnoreCase("add")) {
            respuesta = accesoService.save(entity);
        } else if (accion.equalsIgnoreCase("update")) {
            respuesta = accesoService.update(entity);
        }
        return respuesta;
    }

    @RequestMapping(value = "edit/{id}")
    public @ResponseBody
    Acceso edit(@PathVariable(value = "id") int id, ModelMap model) throws Exception {
        return accesoService.get(new Acceso(id));
    }

    @RequestMapping(value = "delete/{id}")
    public @ResponseBody
    Response delete(@PathVariable(value = "id") int id) throws Exception {
        return accesoService.delete(new Acceso(id));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
