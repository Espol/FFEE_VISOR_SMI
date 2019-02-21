/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.configuracion.service.FacturaService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;
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
@RequestMapping(value = "config/*/**")
public class FacturaController extends GenericController {

    @Autowired
    FacturaService facturaService;

    @RequestMapping(value = "config/factura/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/factura");
        return modelAndView;
    }

    @RequestMapping(value = "config/factura/edit")
//    public @ResponseBody  Response edit(@RequestParam("parametros") Map<String, Object> parametros) {
    public @ResponseBody
    Response edit(@RequestParam("id-autorizacion") String idAutorizacion, @RequestParam("autorizacion") String autorizacion,
            @RequestParam("id-rechazado") String idRechazado, @RequestParam("rechazado") String rechazado,
            @RequestParam("id-contingencia") String idContingencia, @RequestParam("contingencia") String contingencia,
            @RequestParam("id-inconsistente") String idInconsistente, @RequestParam("inconsistente") String inconsistente,
            @RequestParam("id-reporte") String idReporte, @RequestParam("reporte") String reporte,
            @RequestParam("id-xml") String idXml, @RequestParam("xml") String xml,
            @RequestParam("id-pdf") String idPdf, @RequestParam("pdf") String pdf,
            @RequestParam("id-xsd100") String idXsd100, @RequestParam("xsd100") String xsd100,
            @RequestParam("id-xsd110") String idXsd110, @RequestParam("xsd110") String xsd110) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id-autorizacion", idAutorizacion);
            parametros.put("autorizacion", autorizacion);
            parametros.put("id-rechazado", idRechazado);
            parametros.put("rechazado", rechazado);
            parametros.put("id-contingencia", idContingencia);
            parametros.put("contingencia", contingencia);
            parametros.put("id-inconsistente", idInconsistente);
            parametros.put("inconsistente", inconsistente);
            parametros.put("id-reporte", idReporte);
            parametros.put("reporte", reporte);
            parametros.put("id-xml", idXml);
            parametros.put("xml", xml);
            parametros.put("id-pdf", idPdf);
            parametros.put("pdf", pdf);
            parametros.put("id-xsd100", idXsd100);
            parametros.put("xsd100", xsd100);
            parametros.put("id-xsd110", idXsd110);
            parametros.put("xsd110", xsd110);
            response = facturaService.updateParametros(parametros, this.getSociedadRUC());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "config/factura/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            List<Parametro> list = facturaService.getParametros(this.getSociedadRUC());
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("autorizacion", util.getParametro(list, ParametroConstants.FACTURA_PATH_AUTORIZACION).getParametroLow());
            data.put("id-autorizacion", util.getParametro(list, ParametroConstants.FACTURA_PATH_AUTORIZACION).getIdParametro());
            data.put("rechazado", util.getParametro(list, ParametroConstants.FACTURA_PATH_RECHAZADO).getParametroLow());
            data.put("id-rechazado", util.getParametro(list, ParametroConstants.FACTURA_PATH_RECHAZADO).getIdParametro());
            data.put("contingencia", util.getParametro(list, ParametroConstants.FACTURA_PATH_CONTINGENCIA).getParametroLow());
            data.put("id-contingencia", util.getParametro(list, ParametroConstants.FACTURA_PATH_CONTINGENCIA).getIdParametro());
            data.put("inconsistente", util.getParametro(list, ParametroConstants.FACTURA_PATH_INCONSISTENTE).getParametroLow());
            data.put("id-inconsistente", util.getParametro(list, ParametroConstants.FACTURA_PATH_INCONSISTENTE).getIdParametro());
            data.put("reporte", util.getParametro(list, ParametroConstants.FACTURA_PATH_REPORT).getParametroLow());
            data.put("id-reporte", util.getParametro(list, ParametroConstants.FACTURA_PATH_REPORT).getIdParametro());
            data.put("xml", util.getParametro(list, ParametroConstants.FACTURA_PATH_XML).getParametroLow());
            data.put("id-xml", util.getParametro(list, ParametroConstants.FACTURA_PATH_XML).getIdParametro());
            data.put("pdf", util.getParametro(list, ParametroConstants.FACTURA_PATH_PDF).getParametroLow());
            data.put("id-pdf", util.getParametro(list, ParametroConstants.FACTURA_PATH_PDF).getIdParametro());
            data.put("xsd100", util.getParametro(list, ParametroConstants.FACTURA_PATH_XSD_100).getParametroLow());
            data.put("id-xsd100", util.getParametro(list, ParametroConstants.FACTURA_PATH_XSD_100).getIdParametro());
            data.put("xsd110", util.getParametro(list, ParametroConstants.FACTURA_PATH_XSD_110).getParametroLow());
            data.put("id-xsd110", util.getParametro(list, ParametroConstants.FACTURA_PATH_XSD_110).getIdParametro());
            response.setData(data);
            response.setMessage("Los datos se Cargaron Correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
