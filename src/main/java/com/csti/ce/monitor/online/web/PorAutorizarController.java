/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.online.web;

import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.ConstanteLocal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("porautorizar/*/**")
public class PorAutorizarController extends GenericController {

    @Autowired
    ComprobanteService comprobanteService;

    @RequestMapping(value = "/porautorizar/view")
    public ModelAndView viewAutorizado(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("online/porautorizar");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/porautorizar/comprobante/load") 
    public @ResponseBody
    Map<String, Object> getComprobante(@RequestParam("page") int page,
            @RequestParam("rows") int rows,
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("fechaIni") String fechaDesde,
            @RequestParam("fechaFinal") String fechaHasta,
            @RequestParam("codInterlocutor") String codInterlocutor,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("nroReferencia") String nroReferencia) throws Exception {
        int initRow = (page - 1) * rows;
        List<ComprobanteView> list = comprobanteService.getComprobantePorAutorizar(getSociedadRUC(), tipoDoc, nroSri, nroReferencia, fechaDesde, fechaHasta, initRow, rows, ConstanteLocal.VERSION_ONLINE,codInterlocutor);
        Integer totalRegistros = comprobanteService.getTotal();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("total", totalRegistros);
        response.put("rows", list);
        return response;
    }

    @RequestMapping(value = "/porautorizar/comprobante/pdf")
    public @ResponseBody Map<String, Object> viewPDFComprobante() {
        return null;
    }

    @RequestMapping(value = "/porautorizar/comprobante/mail")
    public @ResponseBody Map<String, Object> sendMailComprobante() {
        return null;
    }

}
