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

import com.csti.ce.seguridad.domain.Rol;
import com.csti.ce.seguridad.service.RolService;
import com.csti.ce.util.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("rol/*")
public class RolController {
	@Autowired
	RolService rolService;
        
        @RequestMapping(value = "view")
        public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
            ModelAndView modelAndView =  new ModelAndView();
            modelAndView.setViewName("seguridad/rol");
            return modelAndView;
        }

	@RequestMapping(value = "loadAll")
	public @ResponseBody
	Map<String, Object> getAll() {
		List<Rol> listRol = rolService.getAll();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("rows", listRol);
		response.put("total", listRol.size());
		return response;
	}
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public  @ResponseBody Response save(@RequestParam("accion") String accion,@ModelAttribute Rol entity,BindingResult result) throws Exception {
		Response respuesta=new Response();
		if(accion.equalsIgnoreCase("add")){
			respuesta =rolService.save(entity);
		}else if(accion.equalsIgnoreCase("update")){
			respuesta=rolService.update(entity);
		}
		return respuesta;
	}
	@RequestMapping(value = "edit/{id}")
	public @ResponseBody Rol edit(@PathVariable(value = "id") int id, ModelMap model) throws Exception {
		return rolService.get(new Rol(id));
	}
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody Response delete(@PathVariable(value = "id") int id) throws Exception {
		return rolService.delete(new Rol(id));
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
