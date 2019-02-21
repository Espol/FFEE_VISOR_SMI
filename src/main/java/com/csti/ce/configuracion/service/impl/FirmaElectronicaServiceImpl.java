/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.configuracion.service.FirmaElectronicaService;
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
public class FirmaElectronicaServiceImpl implements FirmaElectronicaService {

    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametro(String sociedad) {
        List<Parametro> list = new ArrayList<Parametro>();
        String parametroHigh = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.FIRMA_PATH_BASE));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.FIRMA_CLAVE));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.FIRMA_PATH));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.FIRMA_FECHA_EMISION));
        list.add(parametroDAOHibernate.getParametroByCampo(parametroHigh, ParametroConstants.FIRMA_FECHA_CADUCIDAD));
        return list;

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametro(Map<String, Object> parametros, String sociedad) {
//         String programa = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        Response response = new Response();
        try {
            // FIRMA PATH BASE
            int idBase = Integer.parseInt(parametros.get("id-base").toString());
            Parametro parametroBase = parametroDAOHibernate.getById(idBase);
            parametroBase.setParametroLow(parametros.get("base").toString());
            parametroDAOHibernate.update(parametroBase);

            // FIRMA PATH
            int idFirma = Integer.parseInt(parametros.get("id-firma").toString());
            Parametro parametroFirma = parametroDAOHibernate.getById(idFirma);
            parametroFirma.setParametroLow(parametros.get("firma").toString());
            parametroDAOHibernate.update(parametroFirma);

            // CLAVE DE FIRMA
            int idClave = Integer.parseInt(parametros.get("id-clave").toString());
            Parametro clave = parametroDAOHibernate.getById(idClave);
            clave.setParametroLow(parametros.get("clave").toString());
            parametroDAOHibernate.update(clave);
            
            int idFirmaEmision = Integer.parseInt(parametros.get("idFechaEmision").toString());
            Parametro firmaEmision = parametroDAOHibernate.getById(idFirmaEmision);
            firmaEmision.setParametroLow(parametros.get("fechaEmision").toString());
            parametroDAOHibernate.update(firmaEmision);
            
            int idFechaCaducidad = Integer.parseInt(parametros.get("idFechaCaducidad").toString());
            Parametro fechaCaducidad = parametroDAOHibernate.getById(idFechaCaducidad);
            fechaCaducidad.setParametroLow(parametros.get("fechaCaducidad").toString());
            parametroDAOHibernate.update(fechaCaducidad);
            
            
            response.setMessage(MensajeConstante.FIRMA_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.FIRMA_ERROR);
            response.setSuccess(false);
            return response;
        }
    }
}
