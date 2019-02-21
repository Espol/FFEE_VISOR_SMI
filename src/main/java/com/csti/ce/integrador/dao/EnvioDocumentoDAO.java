/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao;

import com.csti.ce.integrador.domain.EnvioDocumento;
import java.util.List;

/**
 *
 * @author MARCELO
 */
public interface EnvioDocumentoDAO {
    
    public EnvioDocumento getEnviodocumento(String ruc, String nroSri, String tipoDoc) throws Exception;
    
    public void updateEstado(int id) throws Exception;
    
    public List<EnvioDocumento> getEnvioDocumentoRechazado(String ruc, String nroSri, String tipoDoc) throws Exception;
    
    public void save(EnvioDocumento entity);
    
}
