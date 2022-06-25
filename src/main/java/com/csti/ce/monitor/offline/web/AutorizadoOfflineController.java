/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.offline.web;


import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.service.ReporteExcelService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.ConstanteLocal;
import com.csti.ce.util.Excel;
import com.csti.ce.util.Response;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
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
@RequestMapping("autorizado/*/**")
public class AutorizadoOfflineController extends GenericController {

    @Autowired
    ComprobanteService comprobanteService;
    
    @Autowired
    private ReporteExcelService reporteExcelService;

    @RequestMapping(value = "/autorizado/offline/view")
    public ModelAndView viewAutorizado(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.setViewName("offline/autorizado");
        } catch (Exception ex) {
            modelAndView.setViewName("Error");
            modelAndView.addObject("message", ex.getLocalizedMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/autorizado/offline/load")
    public @ResponseBody
    Map<String, Object> getComprobante(@RequestParam("page") int page,
            @RequestParam("rows") int rows,
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("nroReferencia") String nroReferencia,
            @RequestParam("codInterlocutor") String codInterlocutor,
            @RequestParam("fechaIni") String fechaIni,
            @RequestParam("fechaFinal") String fechaFin) throws Exception {
        int initRow = (page - 1) * rows;
        List<ComprobanteView> list = comprobanteService.getComprobanteAutorizado(getSociedadRUC(), tipoDoc, nroSri, nroReferencia, fechaIni, fechaFin, initRow, rows, ConstanteLocal.VERSION_OFFLINE,codInterlocutor);
        Integer totalRegistros = comprobanteService.getTotal();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("total", totalRegistros);
        response.put("rows", list);
        return response;
    }

    @RequestMapping(value = "/autorizado/offline/mail")
    public ModelAndView sendMailComprobante() {
        return null;
    } 
    
    /**
     *
     * @param tipoDoc
     * @param nroSri
     * @param nroReferencia
     * @param codInterlocutor
     * @param fechaIni
     * @param fechaFin
     * @return
     */
    @RequestMapping(value = "/autorizado/offline/comprobante/generar")
    public @ResponseBody
    Response generarReporte(
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("nroReferencia") String nroReferencia,
            @RequestParam("codInterlocutor") String codInterlocutor,
            @RequestParam("fechaIni") String fechaIni,
            @RequestParam("fechaFinal") String fechaFin) {
        Response respuesta = new Response();
        try {
            List<ComprobanteView> comprobantes = comprobanteService.getComprobanteByExcel(getSociedadRUC(), tipoDoc, nroSri, nroReferencia, fechaIni, fechaFin, codInterlocutor, ConstanteLocal.VERSION_OFFLINE);
            reporteExcelService.setRucSociedad(this.getSociedadRUC());
            reporteExcelService.setEsquema(Excel.OFFLINE);
            reporteExcelService.generarCabecera();
            reporteExcelService.setComprobantes(comprobantes);
            reporteExcelService.generarReporte();
            reporteExcelService.crearFileExcel();
            respuesta.setMessage("Se genero Correctamente el Reporte EXCEL.");
            respuesta.setSuccess(true);
            respuesta.setData(reporteExcelService.getFileExcel());
            
            //this.viewXLS(reporteExcelService.getFileExcel(), request, response);
            
        } catch (Exception e) {
            respuesta.setMessage("Error al generar Reporte EXCEL.");
            respuesta.setSuccess(false);
            System.out.println("Error: " + e.getMessage());
            
        }
        return respuesta;
    }

    @RequestMapping(value = "/autorizado/comprobante/excel")
    public void viewExcel(@RequestParam("path") String path,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            this.viewXLS(path, request, response);
        } catch (IOException ex) {
            ServletOutputStream out = response.getOutputStream();
            out.println("PROBLEMAS: " + ex);
            out.println(ex + "");
            out.println("Comunicarse con el soporte del sistema.");
            out.flush();
            out.close();
            Logger.getLogger(AutorizadoOfflineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
