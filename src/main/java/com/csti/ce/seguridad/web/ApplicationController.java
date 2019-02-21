package com.csti.ce.seguridad.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("application/*/**")
public class ApplicationController {

	@RequestMapping(value = "/application/init", method = RequestMethod.POST)
	public ModelAndView init(HttpServletRequest request,	HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName("monitor/aplicacion");
		} catch (Exception ex) {
			modelAndView.setViewName("Error");
			modelAndView.addObject("message", ex.getLocalizedMessage());
		}
		return modelAndView;
	}
	@RequestMapping(value = "/application/welcome")
	public ModelAndView welcome(HttpServletRequest request,	HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName("welcome");
		} catch (Exception ex) {
			modelAndView.setViewName("Error");
			modelAndView.addObject("message", ex.getLocalizedMessage());
		}
		return modelAndView;
	}
}
