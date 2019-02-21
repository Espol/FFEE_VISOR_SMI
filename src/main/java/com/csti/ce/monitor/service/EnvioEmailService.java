/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service;

import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.util.Response;

/**
 *
 * @author Usuario
 */
public interface EnvioEmailService {

    public void setRuc(String ruc);

    public void setMensaje(String mensaje);

    public void setEmailsDestino(String emails);

    public void setIdentificador(long identificador);

    public void setAmbiente(String ambiente);

    public void envioEmailAutorizado();

    public void envioEmailNotificacion();

    public void update();

    public Response getRespuesta();
//    public boolean getsuccess();
}
