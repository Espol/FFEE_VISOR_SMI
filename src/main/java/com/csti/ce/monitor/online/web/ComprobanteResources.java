/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.online.web;

import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.service.EnvioEmailService;
import com.csti.ce.monitor.service.GeneracionReporteService;
import com.csti.ce.util.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Usuario
 */
@Controller
@RequestMapping("resources/*/**")
public class ComprobanteResources extends GenericController {
    
    @Autowired
    ComprobanteService comprobanteService;
    
    @RequestMapping(value = "/resources/{identificacion}/pdf")
    public void getPdf(@PathVariable Long identificacion,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Comprobante comprobante = comprobanteService.getPDF(identificacion, this.getSociedadRUC());
        try {
            String resource = comprobante.getPathPdf();
            String filename = AplicacionUtil.obtenerComprobanteByCodigo(comprobante.getTipoDoc()) + "-"
                    + comprobante.getNroSri();
            this.viewPdf(resource, filename, request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
            ServletOutputStream out = response.getOutputStream();
            out.println("PROBLEMAS: " + ex);
            out.println(ex + "");
            out.println("Comunicarse con el soporte del sistema.");
            out.flush();
            out.close();
            Logger.getLogger(AutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @RequestMapping(value = "/resources/{identificacion}/xml", produces = {"application/xml"})
    public void getXML(@PathVariable Long identificacion,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Comprobante comprobante = comprobanteService.getPDF(identificacion, this.getSociedadRUC());
        try {
            String resource = comprobante.getPathXml();
            String filename = AplicacionUtil.obtenerComprobanteByCodigo(comprobante.getTipoDoc()) + "-"
                    + comprobante.getNroSri();
            this.viewXML(resource, filename, request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
            ServletOutputStream out = response.getOutputStream();
            out.println("PROBLEMAS: " + ex);
            out.println(ex + "");
            out.println("Comunicarse con el soporte del sistema.");
            out.flush();
            out.close();
            Logger.getLogger(AutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @RequestMapping(value = "/resources/{identificacion}/log")
    public void getlog(@PathVariable Long identificacion,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Comprobante comprobante = comprobanteService.getPDF(identificacion, this.getSociedadRUC());
        try {
            String resource = comprobante.getLog();
            String filename = AplicacionUtil.obtenerComprobanteByCodigo(comprobante.getTipoDoc()) + "-"
                    + comprobante.getNroSri();
            this.viewLOG(resource, filename, request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
            ServletOutputStream out = response.getOutputStream();
            out.println("PROBLEMAS: " + ex);
            out.println(ex + "");
            out.println("Comunicarse con el soporte del sistema.");
            out.flush();
            out.close();
            Logger.getLogger(AutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @RequestMapping(value = "/resources/{identificacion}/emails")
    public @ResponseBody
    Response getEmailDestino(@PathVariable Long identificacion,
            HttpServletRequest request,
            HttpServletResponse response) {
        Response respuesta = new Response();
        Map<String, Object> maps = new HashMap<String, Object>();
        Comprobante comprobante = comprobanteService.getEmailsDestino(identificacion, this.getSociedadRUC());
        respuesta.setSuccess(true);
        respuesta.setMessage("Se obtubo datos correctamente.");
        maps.put("emails", comprobante.getEmailDestino());
        maps.put("mensaje", comprobante.getMensaje());
        respuesta.setData(maps);
        return respuesta;
    }
    @Autowired
    EnvioEmailService envioEmail;
    
    @RequestMapping(value = "/resources/{identificacion}/emails/send")
    public @ResponseBody
    Response sendEmail(@PathVariable("identificacion") long identificador,
            @RequestParam("emails") String emails,
            @RequestParam("mensaje") String mensaje,
            HttpServletRequest request,
            HttpServletResponse response) {
        envioEmail.setRuc(this.getSociedadRUC());
//        envioEmail.setComprobante(comprobante);
        envioEmail.setIdentificador(identificador);
        envioEmail.setEmailsDestino(emails);
        envioEmail.setAmbiente(this.getAmbiente());
        envioEmail.setMensaje(mensaje);
        envioEmail.envioEmailAutorizado();
        return envioEmail.getRespuesta();
    }
    @Autowired
    GeneracionReporteService generacionPDF;
    
    @RequestMapping(value = "/resources/{identificacion}/pdf/generar")
    public @ResponseBody
    Response generarPDF(@PathVariable("identificacion") long identificador,
            @RequestParam("email") int emails,
            HttpServletRequest request,
            HttpServletResponse response) {
        generacionPDF.setAmbiente(this.getAmbiente());
        generacionPDF.setIdentificador(identificador);
        generacionPDF.sendEmail(emails);
        generacionPDF.setRuc(this.getSociedadRUC());
        generacionPDF.generarPDF();
        return generacionPDF.getRespuesta();
    }
    
    @RequestMapping(value = "/resources/correo/notificacion")
    public @ResponseBody
    Response cancelarNotificacion(@RequestParam("identificacion") long identificador,
            @RequestParam("nroSri") String nroSri,
            @RequestParam("tipoDocumento") String tipoDoc,
            HttpServletRequest request,
            HttpServletResponse response) {
        return comprobanteService.cancelarNotificacionCorreo(nroSri, tipoDoc);
    }
}
