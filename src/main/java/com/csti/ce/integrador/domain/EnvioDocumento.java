/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.domain;

/**
 *
 * @author MARCELO
 */
public class EnvioDocumento {
    
    private int idEnvio;
    private int idPeriodo;
    private int idSociedad;
    private int secuencia;
    private String destino;
    private String estado;
    private int estadoNotifRechazado;

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdSociedad() {
        return idSociedad;
    }

    public void setIdSociedad(int idSociedad) {
        this.idSociedad = idSociedad;
    }

    public int getEstadoNotifRechazado() {
        return estadoNotifRechazado;
    }

    public void setEstadoNotifRechazado(int estadoNotifRechazado) {
        this.estadoNotifRechazado = estadoNotifRechazado;
    }
}
