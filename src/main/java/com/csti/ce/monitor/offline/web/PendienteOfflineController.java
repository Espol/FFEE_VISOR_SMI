/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.offline.web;

import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.service.EnvioDocumentoService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.ConstanteLocal;
import com.csti.ce.util.Response;
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
@RequestMapping("pendiente/*/**")
public class PendienteOfflineController extends GenericController {

    @Autowired
    ComprobanteService comprobanteService;
    
    @Autowired
    EnvioDocumentoService envioDocumentoService;

    @RequestMapping(value = "/pendiente/offline/view")
    public ModelAndView viewAutorizado(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("offline/pendiente");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/pendiente/offline/load")
    public @ResponseBody Map<String, Object> getComprobante(@RequestParam("page") int page,
            @RequestParam("rows") int rows,
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("fechaIni") String fechaDesde,
            @RequestParam("fechaFinal") String fechaHasta,
            @RequestParam("codInterlocutor") String codInterlocutor,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("nroReferencia") String nroReferencia) {
        int initRow = (page - 1) * rows;
        List<ComprobanteView> list = comprobanteService.getComprobantePendiente(getSociedadRUC(), tipoDoc, nroSri, nroReferencia, fechaDesde, fechaHasta, initRow, rows, ConstanteLocal.VERSION_OFFLINE,codInterlocutor);
        Integer totalRegistros = comprobanteService.getTotal();
        @SuppressWarnings("Convert2Diamond")
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rows", list);
        response.put("total", totalRegistros);
        return response;
    }

    @RequestMapping(value = "/pendiente/offline/pdf")
    public @ResponseBody Map<String, Object> viewPDFComprobante() {
        return null;
    }

    @RequestMapping(value = "/pendiente/offline/mail")
    public @ResponseBody Map<String, Object> sendMailComprobante() {
        return null;
    }
    
    @RequestMapping(value = "/pendiente/offline/autorizar")
    public @ResponseBody Response procesarPendientes(@RequestParam("nroSri") String nroSri,
            @RequestParam("tipoDoc") String tipoDoc){
        Response response = new Response();
        try {
            envioDocumentoService.procesarPendientes(getSociedadRUC(), nroSri, tipoDoc);
            response.setSuccess(true);
            response.setMessage("Se Cambio de estado el Documento, en un momento se procedera a su respectiva Autorización.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            response.setMessage("Error al cambiar el estado del documento, Comuniquese con soporte.");
        }
        return response;
    }
}
