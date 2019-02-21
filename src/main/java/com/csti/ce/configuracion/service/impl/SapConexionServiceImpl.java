/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service.impl;

import com.csti.ce.configuracion.service.SapConexionService;
import com.csti.ce.constant.ConexionSAPConstants;
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
 * @author Usuario
 */

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class SapConexionServiceImpl implements SapConexionService {

    @Autowired
    ParametroDAO parametroDAOHibernate;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Parametro> getParametros(String sociedad) {
        List<Parametro> list = new ArrayList<>();
        String programa = parametroDAOHibernate.getSociedad(sociedad).getParametroHigh();
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_MANDANTE));
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_USUARIO));
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_PASSWORD));
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_IP_CONFIGURACION));
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_NUMERO_INSTANCIA));
        list.add(parametroDAOHibernate.getParametroByCampo(programa, ConexionSAPConstants.SAP_IDIOMA));
        return list;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response updateParametros(Map<String, Object> parametros, String sociedad) {
        Response response = new Response();
        try {
            // MANDANTE
            int idMandante = Integer.parseInt(parametros.get("idMandante").toString());
            Parametro mandante = parametroDAOHibernate.getById(idMandante);
            mandante.setParametroLow(parametros.get("mandante").toString());
            parametroDAOHibernate.update(mandante);

            // USUARIO SAP
            int idUsuarioSap = Integer.parseInt(parametros.get("idUsuarioSap").toString());
            Parametro usuarioSap = parametroDAOHibernate.getById(idUsuarioSap);
            usuarioSap.setParametroLow(parametros.get("usuarioSap").toString());
            parametroDAOHibernate.update(usuarioSap);

            // PASSWORD SAP
            int idPasswordSap = Integer.parseInt(parametros.get("idPasswordSap").toString());
            Parametro passwordSap = parametroDAOHibernate.getById(idPasswordSap);
            passwordSap.setParametroLow(parametros.get("passwordSap").toString());
            parametroDAOHibernate.update(passwordSap);

            // IP DE SAP
            int idIpSap = Integer.parseInt(parametros.get("idIpSap").toString());
            Parametro ipSap = parametroDAOHibernate.getById(idIpSap);
            ipSap.setParametroLow(parametros.get("ipSap").toString());
            parametroDAOHibernate.update(ipSap);

            // IDIOMA DE SAP
            int idIdiomaSap = Integer.parseInt(parametros.get("idIdiomaSap").toString());
            Parametro idiomaSap = parametroDAOHibernate.getById(idIdiomaSap);
            idiomaSap.setParametroLow(parametros.get("idiomaSap").toString());
            parametroDAOHibernate.update(idiomaSap);
            
            // IDIOMA DE SAP
            int idInstanciaSap = Integer.parseInt(parametros.get("idInstanciaSap").toString());
            Parametro instanciaSap = parametroDAOHibernate.getById(idInstanciaSap);
            instanciaSap.setParametroLow(parametros.get("instanciaSap").toString());
            parametroDAOHibernate.update(instanciaSap);

            response.setMessage(MensajeConstante.SAP_CONEXION_UPDATE);
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setMessage(MensajeConstante.SAP_CONEXION_ERROR);
            response.setSuccess(false);
            return response;
        }
    }
}
