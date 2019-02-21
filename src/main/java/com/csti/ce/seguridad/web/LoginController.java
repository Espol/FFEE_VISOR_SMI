package com.csti.ce.seguridad.web;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.generic.GenericController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioApp;
import com.csti.ce.seguridad.service.LoginService;
import com.csti.ce.util.Constants;
import com.csti.ce.util.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("login/*/**")
public class LoginController extends GenericController {

    // Here the authenticate service is automatically injected into this
    // controller
    @Autowired
    private LoginService loginService;

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @RequestMapping(value = "/login/init")
    public ModelAndView init(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Response respuesta = new Response();
        modelAndView.addObject("response", respuesta);
        try {
            Usuario usuario = this.getUsuarioSesion();
            if (usuario == null) {
                modelAndView.setViewName("seguridad/login");
            } else {
                modelAndView.setViewName("application");
                respuesta.setMessage("Bienvenido " + usuario.getUserName());
            }
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login/verify")
//    @RequestMapping(value = "/login/verify", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Response processCredentials(HttpServletRequest request,
    		HttpServletResponse response,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("sociedad") String sociedad,
            @RequestParam("ambiente") String ambiente
            ) {
        Response respuesta = new Response("Credenciales Inválidas");
        UsuarioApp usuarioAValidar = new UsuarioApp();
        usuarioAValidar.setUserNameApp(userName);
        usuarioAValidar.setSociedad(sociedad);
        usuarioAValidar.setAmbiente("ambiente");
//	usuarioAValidar.setPasswordApp(util.findMD5(password));
        usuarioAValidar.setPasswordApp(password);
        try {
            UsuarioApp usuarioValidado = loginService.verifyUserNameAndPassword(usuarioAValidar);
            respuesta.setSuccess(true);
            respuesta.setMessage("Bienvenido " + userName);
            request.getSession().setAttribute(Constants.USER_SESSION, usuarioValidado);
            request.getSession().setAttribute(Constants.SOCIEDAD, sociedad);
            request.getSession().setAttribute(Constants.AMBIENTE, ambiente);
            
        } catch (Exception e) {
            respuesta.setMessage(e.getMessage());
        }
        return respuesta;
    }

    @RequestMapping(value = "/login/salir")
    public @ResponseBody
    Response logout(HttpServletRequest request
    		, HttpServletResponse response) {
        Response respuesta = new Response("Se ha cerrado sessión con éxito.");
        this.getRequest().getSession().invalidate();
//        try {
//			response.sendRedirect( request.getContextPath() );
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        respuesta.setSuccess(true);
        return respuesta;
        

		// ModelAndView modelAndView = new ModelAndView();
		// modelAndView.setViewName("seguridad/login");
    }

//    @RequestMapping(value = "/login/legacy", method = RequestMethod.GET)
    public ModelAndView loginLegacy(HttpServletRequest request,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            @RequestParam("sociedad") String sociedad,
            @RequestParam("ambiente") String ambiente) {
        String view = "Error";
        Response respuesta = new Response("Las Credenciales no Existe.");
        UsuarioApp usuarioAValidar = new UsuarioApp();
        usuarioAValidar.setUserNameApp(userName);
        usuarioAValidar.setSociedad(sociedad);
        usuarioAValidar.setAmbiente(ambiente);
        usuarioAValidar.setSistema(AplicacionConstants.IDENTIFICADOR_MONITOR);
//	usuarioAValidar.setPasswordApp(util.findMD5(password));
        usuarioAValidar.setPasswordApp(password);
        try {
            UsuarioApp usuarioValidado = loginService.verifyUserNameAndPassword(usuarioAValidar);
            view = "application";
            respuesta.setMessage("Bienvenido " + userName);
            request.getSession().setAttribute(Constants.USER_SESSION, usuarioValidado);
            request.getSession().setAttribute(Constants.SOCIEDAD, sociedad);
            request.getSession().setAttribute(Constants.AMBIENTE, ambiente);
        } catch (Exception e) {
            respuesta.setMessage(e.getMessage());
        }
        return new ModelAndView(view, "message", respuesta.getMessage());
    }

}
