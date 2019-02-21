/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.web;

import com.csti.ce.configuracion.service.ClaveNotificacionService;
import com.csti.ce.constant.MailConstant;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.generic.GenericController;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.Response;
import com.csti.ce.util.util;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping(value = "config/*/**")
public class ClaveNotificacionController extends GenericController {

    @Autowired
    ClaveNotificacionService claveNotificacionServiceImpl;

    @RequestMapping(value = "config/clave/view")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("configuracion/claveNotificacion");
        return modelAndView;
    }

    @RequestMapping(value = "config/clave/load")
    public @ResponseBody
    Response get() {
        Response response = new Response();
        if (!this.evaluarSesion()) {
            response.setMessage(MensajeConstante.SESION_EXPIRADA);
        }
        try {
            Map<String, Object> data = new HashMap<>();
            List<Parametro> list = claveNotificacionServiceImpl.getParametro(this.getSociedadRUC());
            String PorcentajeTotal = util.getParametro(list, MailConstant.CLAVES_TOTAL).getParametroLow();
            String PorcentajeParcial = util.getParametro(list, MailConstant.CLAVES_PORCENT_NOTIFI).getParametroLow();
            double totalPorcentaje = Double.parseDouble(PorcentajeTotal);
            double parcialPorcentaje = Double.parseDouble(PorcentajeParcial);
            double valorActual = 0d;
            BigDecimal porcentajeActual = BigDecimal.ZERO;
            if (totalPorcentaje > 0) {
                try {
                    BigDecimal total = BigDecimal.valueOf(totalPorcentaje);
                    BigDecimal parcial = BigDecimal.valueOf(parcialPorcentaje);
                    BigDecimal parcialXcien = parcial.multiply(new BigDecimal(100));
                    porcentajeActual = parcialXcien.divide(total, 2, RoundingMode.HALF_UP);
                } catch (Exception e) {
                    valorActual = (parcialPorcentaje * 100) / totalPorcentaje;
                    valorActual = Math.rint(valorActual * 100) / 100;
                    porcentajeActual = BigDecimal.valueOf(valorActual);
                }
                AplicacionUtil.DosDecimales(porcentajeActual);
            }
            data.put("clave", porcentajeActual);
            response.setData(data);
            response.setMessage("Las claves se Cargarón correctamente.");
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

}
