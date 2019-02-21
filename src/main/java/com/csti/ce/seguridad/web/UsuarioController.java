package com.csti.ce.seguridad.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.csti.ce.generic.GenericController;
import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;
import com.csti.ce.seguridad.service.UsuarioRolService;
import com.csti.ce.seguridad.service.UsuarioService;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;

@Controller
@RequestMapping("usuario/*")
public class UsuarioController extends GenericController {
	
	@Autowired
	UsuarioService usuarioService;
	
    @Autowired
    UsuarioRolService usuarioRolService;
    
    @RequestMapping(value = "view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("seguridad/usuario");
        return modelAndView;
    }
    
	@RequestMapping(value = "loadAll")
	public @ResponseBody
	Map<String, Object> getAll(@RequestParam("query") String value) {
		List<Usuario> listUsuario = null;
		if (value.length() <= 0) {
			listUsuario = usuarioService.getAll();
		} else {
			listUsuario = usuarioService.getByQuery(value);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("rows", listUsuario);
		response.put("total", listUsuario.size());
		return response;
	}
	
    @RequestMapping(value = "getUsersBySociedad")
    public @ResponseBody
    Map<String, Object> getUsuariosBySociedad(
    		@RequestParam("page") int page,
            @RequestParam("rows") int rows
    		) {
    	int initRow = (page - 1) * rows;
    	
    	String ruc =  getSociedadRUC();
        List<UsuarioRol> usuarioRolsCount = usuarioRolService.getAllBySociedad(ruc);
        List<UsuarioRol> usuarioRols = usuarioRolService.getAllBySociedadPagination(initRow, rows, ruc);
        
//        List<Object> list = new ArrayList<Object>();
//        for (UsuarioRol usuarioRol : usuarioRols) {
//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("idUsuario", usuarioRol.getUsuario().getIdUsuario() );
//            data.put("persona.idPersona", usuarioRol.getUsuario().getPersona().getIdPersona() );
//            data.put("userName", usuarioRol.getUsuario().getUserName() );
//            data.put("sociedad", usuarioRol.getUsuario().getSociedad() );
//            data.put("rol", usuarioRol.getRol().getNombre());
//            data.put("activo", usuarioRol.getUsuario().getActivo() );
//            data.put("userPassword", usuarioRol.getUsuario().getUserPassword() );
//            list.add(data);
//        }
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rows", usuarioRols);
        response.put("total", usuarioRolsCount.size());
        return response;
    }

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody
	Response save(
			@RequestParam("accion") String accion,
			@ModelAttribute UsuarioRol usuarioRol
			, BindingResult result
			)
			throws Exception {
//		String cadenaMD5 = entity.getUserPassword();
//		entity.setUserPassword(util.findMD5(cadenaMD5));
		Response respuesta = new Response();
		if (accion.equalsIgnoreCase("add")) {
			usuarioRol.getUsuario().setSociedad( getSociedadRUC() );
			respuesta = usuarioRolService.save(usuarioRol);
		} else if (accion.equalsIgnoreCase("update")) {
			respuesta = usuarioRolService.updateByAdministrador(usuarioRol);
		}
		return respuesta;
	}

	@RequestMapping(value = "edit/{id}")
	public @ResponseBody
	Usuario edit(@PathVariable(value = "id") int id, ModelMap model)
			throws Exception {
		return usuarioService.get(new Usuario(id));
	}
	
	@RequestMapping(value = "delete/{id}")
	public @ResponseBody
	Response delete(@PathVariable(value = "id") int id) throws Exception {
		return usuarioService.delete(new Usuario(id));
	}

	@RequestMapping(value = "deleteUser")
	public @ResponseBody
	Response deleteUser(
			@ModelAttribute UsuarioRol usuarioRol
		) throws Exception {

		Response respuesta = new Response();
		respuesta = usuarioRolService.deleteByAdministrador(usuarioRol);
		
		return respuesta;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
