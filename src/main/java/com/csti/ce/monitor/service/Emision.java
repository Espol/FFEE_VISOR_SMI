/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service;

import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.pojos.comprobante.webservices.Respuesta;

/**
 *
 * @author Usuario
 */
public interface Emision {

    public void setIdentificador(String identificador);

    public void setTipoDoc(String tipoDoc);

    public Parametro getSociedad();

    public void setUsuario(String usuario);

    public Comprobante getDocumento();

    public void setRuc(String ruc);

    public void setAmbiente(String ambiente);

    public Respuesta getRespuesta();

    public void emitir();
    
    public String anularComprobante();
}
