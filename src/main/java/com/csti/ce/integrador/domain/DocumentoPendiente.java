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
public class DocumentoPendiente {

    private int secuencia;
    private int idSociedad;
    private String tipoDocumento;
    private String serieCorrelativo;
    private String claveAcceso;

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public int getIdSociedad() {
        return idSociedad;
    }

    public void setIdSociedad(int idSociedad) {
        this.idSociedad = idSociedad;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getSerieCorrelativo() {
        return serieCorrelativo;
    }

    public void setSerieCorrelativo(String serieCorrelativo) {
        this.serieCorrelativo = serieCorrelativo;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

}
