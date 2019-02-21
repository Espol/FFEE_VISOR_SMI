/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.monitor.service;

/**
 *
 * @author MARCELO
 */
public interface EnvioDocumentoService {
    
    public void procesarPendientes(String ruc, String nroSri, String tipoDoc) throws Exception;
    
}
