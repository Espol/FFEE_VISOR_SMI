/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.monitor.service.impl;

import com.csti.ce.integrador.dao.EnvioDocumentoDAO;
import com.csti.ce.integrador.domain.EnvioDocumento;
import com.csti.ce.monitor.service.EnvioDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MARCELO
 */
@Service
@Transactional
public class EnvioDocumentoServiceImpl implements EnvioDocumentoService {
    
    @Autowired
    EnvioDocumentoDAO envioDocumentoDAO;

    @Override
    public void procesarPendientes(String ruc, String nroSri, String tipoDoc) throws Exception {
        EnvioDocumento envio = envioDocumentoDAO.getEnviodocumento(ruc, nroSri, tipoDoc);
        if(envio != null){
            envioDocumentoDAO.updateEstado(envio.getIdEnvio());
        } else {
            throw new Exception("No Exite el documento en Pendiente");
        }
    }
    
}
