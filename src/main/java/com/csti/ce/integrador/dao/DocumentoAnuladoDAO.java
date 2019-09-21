/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao;

import com.csti.ce.integrador.domain.DocumentoAnulado;

/**
 *
 * @author MARCELO
 */
public interface DocumentoAnuladoDAO {
    
    public DocumentoAnulado getDocumento(String ruc, String nroSri, String tipoDoc);
    
}
