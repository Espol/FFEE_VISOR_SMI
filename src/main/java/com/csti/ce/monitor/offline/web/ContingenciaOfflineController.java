/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.offline.web;

import com.csti.ce.monitor.service.Emision;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.pojos.comprobante.webservices.Respuesta;
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
@RequestMapping("contingencia/*/**")
public class ContingenciaOfflineController extends GenericController {

    @Autowired
    ComprobanteService comprobanteService;
    @Autowired
    Emision emision;
//    @Autowired
//    GeneracionReporteService reporte;
//    @Autowired
//    EnvioEmailService email;

    @RequestMapping(value = "/contingencia/offline/view")
    public ModelAndView viewAutorizado(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("offline/contingencia");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/contingencia/offline/load")
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
        List<ComprobanteView> list = comprobanteService.getComprobanteContingencia(getSociedadRUC(), tipoDoc, nroSri, nroReferencia, fechaDesde, fechaHasta, initRow, rows, ConstanteLocal.VERSION_OFFLINE,codInterlocutor);
        Integer totalRegistros = comprobanteService.getTotal();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("rows", list);
        response.put("total", totalRegistros);
        return response;
    }

    @RequestMapping(value = "/contingencia/autorizar/offline")
    public @ResponseBody
    Respuesta autorizar(@RequestParam("identificador") String identificador,
            @RequestParam("tipoDocumento") String tipoDoc,
            HttpServletRequest request,
            HttpServletResponse response) {
        emision.setIdentificador(identificador);
        emision.setUsuario(this.getUsuarioSesion().getUserName());
        emision.setTipoDoc(tipoDoc);
        emision.setAmbiente(this.getAmbiente());
        emision.setRuc(this.getSociedadRUC());
        emision.emitir(); 
        return emision.getRespuesta();
    }

    @RequestMapping(value = "/contingencia/offline/mail")
    public @ResponseBody
    Map<String, Object> sendMailComprobante() {
        return null;
    }

}
