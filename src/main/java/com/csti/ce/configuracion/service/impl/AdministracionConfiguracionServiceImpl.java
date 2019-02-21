/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.service.impl;

import com.csti.ce.configuracion.service.AdministracionConfiguracionService;
import com.csti.ce.constant.MensajeConstante;
import com.csti.ce.constant.ParametroConstants;
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
public class AdministracionConfiguracionServiceImpl implements AdministracionConfiguracionService {
    
    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<>();
        String progama = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.CONTABILIZAR_CONTINGENCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.CONTABILIZAR_POR_AUTORIZAR));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.PDF_AUTORIZADO));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.PDF_CONTINGENCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.SEND_EMAIL_AUTORIZADO));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.SEND_EMAIL_CONTINGENCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.NOTIFICAR_AUTORIZADO));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.NOTIFICAR_CONTINGENCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.NOTIFICAR_INCONSISTENTE));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.NOTIFICAR_RECHAZADO));
        list.add(parametroDAOHibernate.getParametroByCampo(progama, ParametroConstants.NOTIFICAR_POR_AUTORIZAR));
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {
        Response response = new Response();
        
        try {
            System.out.println("idcontabilizarPorAutorizar");
            int idcontabilizarPorAutorizar = Integer.parseInt(parametros.get("idcontabilizarPorAutorizar").toString());
            Parametro contabilizarPorAutorizar = parametroDAOHibernate.getById(idcontabilizarPorAutorizar);
            contabilizarPorAutorizar.setParametroLow(parametros.get("contabilizarPorAutorizar").toString());
            parametroDAOHibernate.update(contabilizarPorAutorizar);
            
            System.out.println("idcontabilizarContingencia");
            int idcontabilizarContingencia = Integer.parseInt(parametros.get("idcontabilizarContingencia").toString());
            Parametro contabilizarContingencia = parametroDAOHibernate.getById(idcontabilizarContingencia);
            contabilizarContingencia.setParametroLow(parametros.get("contabilizarContingencia").toString());
            parametroDAOHibernate.update(contabilizarContingencia);

            System.out.println("idpdfAutorizado");
            int idpdfAutorizado = Integer.parseInt(parametros.get("idpdfAutorizado").toString());
            Parametro pdfAutorizado = parametroDAOHibernate.getById(idpdfAutorizado);
            pdfAutorizado.setParametroLow(parametros.get("pdfAutorizado").toString());
            parametroDAOHibernate.update(pdfAutorizado);

            System.out.println("idpdfContingencia");
            int idpdfContingencia = Integer.parseInt(parametros.get("idpdfContingencia").toString());
            Parametro pdfContingencia = parametroDAOHibernate.getById(idpdfContingencia);
            pdfContingencia.setParametroLow(parametros.get("pdfContingencia").toString());
            parametroDAOHibernate.update(pdfContingencia);

            System.out.println("idemailAutorizado");
            int idemailAutorizado = Integer.parseInt(parametros.get("idemailAutorizado").toString());
            Parametro emailAutorizado = parametroDAOHibernate.getById(idemailAutorizado);
            emailAutorizado.setParametroLow(parametros.get("emailAutorizado").toString());
            parametroDAOHibernate.update(emailAutorizado);

            System.out.println("idemailContingencia");
            int idemailContingencia = Integer.parseInt(parametros.get("idemailContingencia").toString());
            Parametro emailContingencia = parametroDAOHibernate.getById(idemailContingencia);
            emailContingencia.setParametroLow(parametros.get("emailContingencia").toString());
            parametroDAOHibernate.update(emailContingencia); 

            int idnotificarAutorizado = Integer.parseInt(parametros.get("idnotificarAutorizado").toString());
            System.out.println("idnotificarAutorizado: " + idnotificarAutorizado);
            Parametro notificarAutorizado = parametroDAOHibernate.getById(idnotificarAutorizado);
            notificarAutorizado.setParametroLow(parametros.get("notificarAutorizado").toString());
            parametroDAOHibernate.update(notificarAutorizado);

            
            int idnotificarInconsistencia = Integer.parseInt(parametros.get("idnotificarInconsistencia").toString());
            System.out.println("idnotificarInconsistencia: " +idnotificarInconsistencia);
            Parametro notificarInconsistencia = parametroDAOHibernate.getById(idnotificarInconsistencia);
            notificarInconsistencia.setParametroLow(parametros.get("notificarInconsistencia").toString());
            parametroDAOHibernate.update(notificarInconsistencia);

            
            int idnotificarConsistencia = Integer.parseInt(parametros.get("idnotificarConsistencia").toString());
            System.out.println("idnotificarConsistencia: " + idnotificarConsistencia);
            Parametro notificarConsistencia = parametroDAOHibernate.getById(idnotificarConsistencia);
            notificarConsistencia.setParametroLow(parametros.get("notificarConsistencia").toString());
            parametroDAOHibernate.update(notificarConsistencia);
            
            
            int idnotificarRechazado = Integer.parseInt(parametros.get("idnotificarRechazado").toString());
            System.out.println("idnotificarRechazado: " + idnotificarRechazado);
            Parametro notificarRechazado = parametroDAOHibernate.getById(idnotificarRechazado);
            notificarRechazado.setParametroLow(parametros.get("notificarRechazado").toString());
            parametroDAOHibernate.update(notificarRechazado);
            
            
            int idnotificarPorAutorizar = Integer.parseInt(parametros.get("idnotificarPorAutorizar").toString());
            System.out.println("idnotificarPorAutorizar: " + idnotificarPorAutorizar);
            Parametro notificarPorAutorizar = parametroDAOHibernate.getById(idnotificarPorAutorizar);
            notificarPorAutorizar.setParametroLow(parametros.get("notificarPorAutorizar").toString());
            parametroDAOHibernate.update(notificarPorAutorizar);
            
            response.setMessage(MensajeConstante.ADMIN_CONFIG_UPDATE);
            response.setSuccess(true);
            
        } catch (Exception e) {
            response.setMessage(MensajeConstante.ADMIN_CONFIG_ERROR);
            response.setSuccess(false);
        }
        
        return response;
    }
    
}
