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

import com.csti.ce.seguridad.domain.Opcion;
import com.csti.ce.seguridad.service.OpcionService;
import com.csti.ce.util.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("gui/*")
public class OpcionController {
	@Autowired
	OpcionService opcionService;
        
        @RequestMapping(value = "view")
        public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
            ModelAndView modelAndView =  new ModelAndView();
            modelAndView.setViewName("seguridad/opcion");
            return modelAndView;
        }

	@RequestMapping(value = "loadAll")
	public @ResponseBody
	Map<String, Object> getAll(@RequestParam("query") String value) {
		List<Opcion> listOpcion = null;
                listOpcion = opcionService.getAll();
//		if (value.length()<=0) {
//			listOpcion = opcionService.getAll();
//		}else{
//			listOpcion = opcionService.getByQuery(value);
//		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("rows", listOpcion);
		response.put("total", listOpcion.size());
		return response;
	}
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public  @ResponseBody Response save(@RequestParam("accion") String accion,@ModelAttribute Opcion entity,BindingResult result) throws Exception {
		Response respuesta=new Response();
		if(accion.equalsIgnoreCase("add")){
			respuesta =opcionService.save(entity);
		}else if(accion.equalsIgnoreCase("update")){
			respuesta=opcionService.update(entity);
		}
		return respuesta;
	}
	@RequestMapping(value = "edit/{id}")
	public @ResponseBody Opcion edit(@PathVariable(value = "id") int id, ModelMap model) throws Exception {
		return opcionService.get(new Opcion(id));
	}
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody Response delete(@PathVariable(value = "id") int id) throws Exception {
		return opcionService.delete(new Opcion(id));
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
