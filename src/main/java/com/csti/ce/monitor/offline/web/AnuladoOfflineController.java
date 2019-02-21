/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.offline.web;

import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.service.Emision;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.seguridad.domain.UsuarioApp;
import com.csti.ce.util.ConstanteLocal;
import com.csti.ce.util.Constants;
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
 * @author Ammiel
 */
@Controller
@RequestMapping("anulado/*/**")
public class AnuladoOfflineController extends GenericController {

    @Autowired
    ComprobanteService comprobanteService;
    @Autowired
    Emision emision;

    @RequestMapping(value = "/anulado/offline/view")
    public ModelAndView viewAnulado(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("offline/anulado");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/anulado/offline/load")
    public @ResponseBody
    Map<String, Object> getComprobante(@RequestParam("page")int page,
            @RequestParam("rows") int rows,
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("nroReferencia") String nroReferencia,
            @RequestParam("codInterlocutor") String codInterlocutor,
            @RequestParam("fechaIni") String fechaIni,
            @RequestParam("fechaFinal") String fechaFin) throws Exception {
        int initRow = (page - 1) * rows;
        List<ComprobanteView> list = comprobanteService.getComprobanteAnulado(getSociedadRUC(),tipoDoc, nroSri, nroReferencia, fechaIni, fechaFin, initRow, rows, ConstanteLocal.VERSION_OFFLINE,codInterlocutor);
        Integer totalRegistros = comprobanteService.getTotal();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("total", totalRegistros);
        response.put("rows", list);
        return response;
    }

    @RequestMapping(value = "/anulado/autorizar/offline")
    public @ResponseBody
    String anularAutorizado(@RequestParam("identificador") String identificador,
            @RequestParam("tipoDocumento") String tipoDoc,
            HttpServletRequest request,
            HttpServletResponse response){
        UsuarioApp usuarioValidado = (UsuarioApp) request.getSession().getAttribute(Constants.USER_SESSION);
        String user = usuarioValidado.getUsuario().getUserName();
        if (user.equalsIgnoreCase("ECVENTAS")){
            emision.setIdentificador(identificador);
            emision.setUsuario(this.getUsuarioSesion().getUserName());
            emision.setTipoDoc(tipoDoc);
            emision.setAmbiente(this.getAmbiente());
            emision.setRuc(this.getSociedadRUC());
            return emision.anularComprobante();
        }else {
            return "Usted no tiene permiso de Anular Documentos.";
        }
    }

    @RequestMapping(value = "/anulado/offline/mail")
    public ModelAndView sendMailComprobante() {
        return null;
    }

}
