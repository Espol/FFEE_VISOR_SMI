/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service;

import com.csti.ce.util.Response;

/**
 *
 * @author Usuario
 */
public interface GeneracionReporteService {

    public void setRuc(String ruc);

    public void setAmbiente(String ambiente);

    public void setIdentificador(long identificador);
 
    public Response getRespuesta();

    public void generarPDF();

    public void enviar();

    public void sendEmail(int enviar);
}
