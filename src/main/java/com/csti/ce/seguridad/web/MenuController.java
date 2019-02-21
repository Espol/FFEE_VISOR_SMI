package com.csti.ce.seguridad.web;

import com.csti.ce.generic.GenericController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csti.ce.seguridad.domain.NodoInteface;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.service.MenuService;
import com.csti.ce.util.ConstanteLocal;

@Controller
@RequestMapping("menu/*")
public class MenuController extends GenericController {

    @Autowired
    MenuService menuService;

    @RequestMapping(value = "loadMenu", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getMenu() {
        Usuario usuario = this.getUsuarioSesion();
        List<NodoInteface> nodoIntefaces = menuService.getMenu(usuario);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(ConstanteLocal.MENU_CHILDREN, nodoIntefaces);
        response.put(ConstanteLocal.MENU_TOTAL_COUNT, nodoIntefaces.size());
//        System.out.println("Total: " + response.get("totalCount"));
//        System.out.println("1 data: " + response.get("children"));
        return response;
    }

    @RequestMapping(value = "permiso", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPerimiso() {
        // // String idComponent =
        // this.getRequest().getParameter("idComponent");
        //
        // Boolean permiso =
        // usuarioService.getPermiso(this.getUsuarioSesion(request), "");
        //
        // Map<String, Object> response = new HashMap<String, Object>();
        // response.put("success", permiso);
        // response.put("message",
        // "Ud., No cuenta con los permisos necesarios.");
        return null;
    }

//	private Usuario getUsuarioSesion(HttpServletRequest request) {
//		HttpSession sesion = request.getSession(true);
//		Usuario usuario = (Usuario) sesion.getAttribute(Constants.USER_SESSION);
//		return usuario;
//	}
}
