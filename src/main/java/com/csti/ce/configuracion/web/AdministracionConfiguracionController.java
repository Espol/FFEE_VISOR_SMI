/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.AdministracionConfiguracionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
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
public class AdministracionConfiguracionController extends GenericController {
    
    @Autowired
    AdministracionConfiguracionService administracionConfiguracionServiceImpl;
    
    @RequestMapping(value = "config/adminconfig/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/administracionConfiguracion");
        return modelAndView;
    }
    
    @RequestMapping(value = "config/adminconfig/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            List<Parametro> list = administracionConfiguracionServiceImpl.getParametros(this.getSociedadRUC());
            Map<String, Object> data = new HashMap<>();
            
            data.put("contabilizarContingencia", util.getParametro(list, ParametroConstants.CONTABILIZAR_CONTINGENCIA).getParametroLow());
            data.put("contabilizarPorAutorizar", util.getParametro(list, ParametroConstants.CONTABILIZAR_POR_AUTORIZAR).getParametroLow());
            data.put("idcontabilizarContingencia", util.getParametro(list, ParametroConstants.CONTABILIZAR_CONTINGENCIA).getIdParametro());
            data.put("idcontabilizarPorAutorizar", util.getParametro(list, ParametroConstants.CONTABILIZAR_POR_AUTORIZAR).getIdParametro());
            
            data.put("pdfAutorizado", util.getParametro(list, ParametroConstants.PDF_AUTORIZADO).getParametroLow());
            data.put("pdfContingencia", util.getParametro(list, ParametroConstants.PDF_CONTINGENCIA).getParametroLow());
            data.put("idpdfAutorizado", util.getParametro(list, ParametroConstants.PDF_AUTORIZADO).getIdParametro());
            data.put("idpdfContingencia", util.getParametro(list, ParametroConstants.PDF_CONTINGENCIA).getIdParametro());
            
            data.put("emailAutorizado", util.getParametro(list, ParametroConstants.SEND_EMAIL_AUTORIZADO).getParametroLow());
            data.put("emailContingencia", util.getParametro(list, ParametroConstants.SEND_EMAIL_CONTINGENCIA).getParametroLow());
            data.put("idemailAutorizado", util.getParametro(list, ParametroConstants.SEND_EMAIL_AUTORIZADO).getIdParametro());
            data.put("idemailContingencia", util.getParametro(list, ParametroConstants.SEND_EMAIL_CONTINGENCIA).getIdParametro());
            
            data.put("notificarAutorizado", util.getParametro(list, ParametroConstants.NOTIFICAR_AUTORIZADO).getParametroLow());
            data.put("notificarInconsistencia", util.getParametro(list, ParametroConstants.NOTIFICAR_INCONSISTENTE).getParametroLow());
            data.put("notificarContingencia", util.getParametro(list, ParametroConstants.NOTIFICAR_CONTINGENCIA).getParametroLow());
            data.put("notificarRechazado", util.getParametro(list, ParametroConstants.NOTIFICAR_RECHAZADO).getParametroLow());
            data.put("notificarPorAutorizar", util.getParametro(list, ParametroConstants.NOTIFICAR_POR_AUTORIZAR).getParametroLow());
            data.put("idnotificarAutorizado", util.getParametro(list, ParametroConstants.NOTIFICAR_AUTORIZADO).getIdParametro());
            data.put("idnotificarInconsistencia", util.getParametro(list, ParametroConstants.NOTIFICAR_INCONSISTENTE).getIdParametro());
            data.put("idnotificarContingencia", util.getParametro(list, ParametroConstants.NOTIFICAR_CONTINGENCIA).getIdParametro());
            data.put("idnotificarRechazado", util.getParametro(list, ParametroConstants.NOTIFICAR_RECHAZADO).getIdParametro());
            data.put("idnotificarPorAutorizar", util.getParametro(list, ParametroConstants.NOTIFICAR_POR_AUTORIZAR).getIdParametro());
            response.setData(data);
            response.setMessage("Los datos se cargaron corectamente.");
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
    
    @RequestMapping(value = "config/adminconfig/edit")
    public @ResponseBody
    Response edit(@RequestParam("contabilizarContingencia") boolean contabilizarContingencia,
            @RequestParam("idcontabilizarContingencia") String idcontabilizarContingencia,
            @RequestParam("contabilizarPorAutorizar") boolean contabilizarPorAutorizar,
            @RequestParam("idcontabilizarPorAutorizar") String idcontabilizarPorAutorizar,
            @RequestParam("pdfAutorizado") boolean pdfAutorizado,
            @RequestParam("idpdfAutorizado") String idpdfAutorizado,
            @RequestParam("pdfContingencia") boolean pdfContingencia,
            @RequestParam("idpdfContingencia") String idpdfContingencia,
            @RequestParam("emailAutorizado") boolean emailAutorizado,
            @RequestParam("idemailAutorizado") String idemailAutorizado,
            @RequestParam("emailContingencia") boolean emailContingencia,
            @RequestParam("idemailContingencia") String idemailContingencia,
            @RequestParam("notificarAutorizado") boolean notificarAutorizado,
            @RequestParam("idnotificarAutorizado") String idnotificarAutorizado,
            @RequestParam("notificarInconsistencia") boolean notificarInconsistencia,
            @RequestParam("idnotificarInconsistencia") String idnotificarInconsistencia,
            @RequestParam("notificarContingencia") boolean notificarConsistencia,
            @RequestParam("idnotificarContingencia") String idnotificarConsistencia,
            @RequestParam("notificarRechazado") boolean notificarRechazado,
            @RequestParam("idnotificarRechazado") String idnotificarRechazado,
            @RequestParam("notificarPorAutorizar") boolean notificarPorAutorizar,
            @RequestParam("idnotificarPorAutorizar") String idnotificarPorAutorizar) {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> parametros = new HashMap<>();
            int intContabilizarPorAutorizar = 0;
            if (contabilizarPorAutorizar) {
                intContabilizarPorAutorizar = 1;
            }
            parametros.put("contabilizarPorAutorizar", intContabilizarPorAutorizar);
            parametros.put("idcontabilizarPorAutorizar", idcontabilizarPorAutorizar);
            int intContabilizarContingencia = 0;
            if (contabilizarContingencia) {
                intContabilizarContingencia = 1;
            }
            parametros.put("contabilizarContingencia", intContabilizarContingencia);
            parametros.put("idcontabilizarContingencia", idcontabilizarContingencia);
            
            int intPdfAutorizado = 0;
            if (pdfAutorizado) {
                intPdfAutorizado = 1;
            }
            parametros.put("pdfAutorizado", intPdfAutorizado);
            parametros.put("idpdfAutorizado", idpdfAutorizado);
            
            int intPdfContingencia = 0;
            if (pdfContingencia) {
                intPdfContingencia = 1;
            }
            parametros.put("pdfContingencia", intPdfContingencia);
            parametros.put("idpdfContingencia", idpdfContingencia);
            
            int intemailAutorizado = 0;
            if (emailAutorizado) {
                intemailAutorizado = 1;
            }
            parametros.put("emailAutorizado", intemailAutorizado);
            parametros.put("idemailAutorizado", idemailAutorizado);
            
            int intemailContingencia = 0;
            if (emailContingencia) {
                intemailContingencia = 1;
            }
            parametros.put("emailContingencia", intemailContingencia);
            parametros.put("idemailContingencia", idemailContingencia);
            
            int intnotificarAutorizado = 0;
            if (notificarAutorizado) {
                intnotificarAutorizado = 1;
            }
            parametros.put("notificarAutorizado", intnotificarAutorizado);
            parametros.put("idnotificarAutorizado", idnotificarAutorizado);
            
            int intnotificarInconsistencia = 0;
            if (notificarInconsistencia) {
                intnotificarInconsistencia = 1;
            }
            parametros.put("notificarInconsistencia", intnotificarInconsistencia);
            parametros.put("idnotificarInconsistencia", idnotificarInconsistencia);
            
            int intnotificarConsistencia = 0;
            if (notificarConsistencia) {
                intnotificarConsistencia = 1;
            }
            parametros.put("notificarConsistencia", intnotificarConsistencia);
            parametros.put("idnotificarConsistencia", idnotificarConsistencia);
            
            int intnotificarRechazado = 0;
            if (notificarRechazado) {
                intnotificarRechazado = 1;
            }
            parametros.put("notificarRechazado", intnotificarRechazado);
            parametros.put("idnotificarRechazado", idnotificarRechazado);
            
            int intnotificarPorAutorizar = 0;
            if (notificarPorAutorizar) {
                intnotificarPorAutorizar = 1;
            }
            parametros.put("notificarPorAutorizar", intnotificarPorAutorizar);
            parametros.put("idnotificarPorAutorizar", idnotificarPorAutorizar);
            
            response = administracionConfiguracionServiceImpl.updateParametros(parametros, this.getSociedadRUC());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
