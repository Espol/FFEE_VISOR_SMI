/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.configuracion.service.RetencionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class RetencionServiceImpl implements RetencionService {

    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {

        Response response = new Response();
        try {

            int idAutorizacion = Integer.parseInt(parametros.get("id-autorizacion").toString());
            Parametro parametroAutorizado = parametroDAOHibernate.getById(idAutorizacion);
            parametroAutorizado.setParametroLow(parametros.get("autorizacion").toString());
            parametroDAOHibernate.update(parametroAutorizado);

            // RECHAZADO
            int idRechazado = Integer.parseInt(parametros.get("id-rechazado").toString());
            Parametro rechazado = parametroDAOHibernate.getById(idRechazado);
            rechazado.setParametroLow(parametros.get("rechazado").toString());
            parametroDAOHibernate.update(rechazado);

            //CONTINGENCIA
            int idContingencia = Integer.parseInt(parametros.get("id-contingencia").toString());
            Parametro contingencia = parametroDAOHibernate.getById(idContingencia);
            contingencia.setParametroLow(parametros.get("contingencia").toString());
            parametroDAOHibernate.update(contingencia);

            //INCONSISTENTE
            int idInconsistente = Integer.parseInt(parametros.get("id-inconsistente").toString());
            Parametro inconsistente = parametroDAOHibernate.getById(idInconsistente);
            inconsistente.setParametroLow(parametros.get("inconsistente").toString());
            parametroDAOHibernate.update(inconsistente);

            //REPORTE
            int idReporte = Integer.parseInt(parametros.get("id-reporte").toString());
            Parametro reporte = parametroDAOHibernate.getById(idReporte);
            reporte.setParametroLow(parametros.get("reporte").toString());
            parametroDAOHibernate.update(reporte);

            //XML
            int idXml = Integer.parseInt(parametros.get("id-xml").toString());
            Parametro xml = parametroDAOHibernate.getById(idXml);
            xml.setParametroLow(parametros.get("xml").toString());
            parametroDAOHibernate.update(xml);

            //PDF
            int idPdf = Integer.parseInt(parametros.get("id-pdf").toString());
            Parametro pdf = parametroDAOHibernate.getById(idPdf);
            pdf.setParametroLow(parametros.get("pdf").toString());
            parametroDAOHibernate.update(pdf);

            //XSD100
            int idXsd100 = Integer.parseInt(parametros.get("id-xsd100").toString());
            Parametro xsd100 = parametroDAOHibernate.getById(idXsd100);
            xsd100.setParametroLow(parametros.get("xsd100").toString());
            parametroDAOHibernate.update(xsd100);

            //XSD110
            int idXsd110 = Integer.parseInt(parametros.get("id-xsd110").toString());
            Parametro xsd110 = parametroDAOHibernate.getById(idXsd110);
            xsd110.setParametroLow(parametros.get("xsd110").toString());
            parametroDAOHibernate.update(xsd110);

            response.setMessage(MensajeConstante.RETENCION_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.RETENCION_ERROR);
            response.setSuccess(false);
            return response; 
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<Parametro>();
        String parametroHigh = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();//        
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_AUTORIZACION));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_RECHAZADO));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_CONTINGENCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_INCONSISTENTE));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_REPORTE));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_PDF));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_XML));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_XSD_100));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.RETENCION_PATH_XSD_110));

        return list;
    }
}
