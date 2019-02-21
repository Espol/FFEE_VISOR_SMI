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

import com.csti.ce.seguridad.domain.UsuarioRol;
import com.csti.ce.seguridad.service.UsuarioRolService;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;

@Controller
@RequestMapping("usuario/rol/*")
public class UsuarioRolController {
	@Autowired
	UsuarioRolService usuarioRolService;

	@RequestMapping(value = "loadAll")
	public @ResponseBody
	Map<String, Object> getAll() {
		List<UsuarioRol> listUsuario = usuarioRolService.getAll();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", listUsuario);
		response.put("totalCount", listUsuario.size());
		return response;
	}
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public  @ResponseBody Response save(@RequestParam("accion") String accion,@ModelAttribute UsuarioRol entity,BindingResult result) throws Exception {
		Response respuesta=new Response();
		if(accion.equalsIgnoreCase("add")){
			respuesta =usuarioRolService.save(entity);
		}else if(accion.equalsIgnoreCase("update")){
			respuesta=usuarioRolService.update(entity);
		}
		return respuesta;
	}
	@RequestMapping(value = "edit/{id}")
	public @ResponseBody UsuarioRol edit(@PathVariable(value = "id") int id, ModelMap model) throws Exception {
		return usuarioRolService.get(new UsuarioRol(id));
	}
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody Response delete(@PathVariable(value = "id") int id) throws Exception {
		return usuarioRolService.delete(new UsuarioRol(id));
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
