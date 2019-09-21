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
public class DocumentoAnulado {
    
    private int secuencia;
	private int idPeriodo;
	private int idSociedad;
	private String tipoDocumento;
        private int subTipoDoc;
	private String establecimiento;
	private String puntoEmision;
	private String numero;
	private String serieCorrelativo;
        private String pathXML;//XML SIN FIRMAR

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getSubTipoDoc() {
        return subTipoDoc;
    }

    public void setSubTipoDoc(int subTipoDoc) {
        this.subTipoDoc = subTipoDoc;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerieCorrelativo() {
        return serieCorrelativo;
    }

    public void setSerieCorrelativo(String serieCorrelativo) {
        this.serieCorrelativo = serieCorrelativo;
    }

    public String getPathXML() {
        return pathXML;
    }

    public void setPathXML(String pathXML) {
        this.pathXML = pathXML;
    }
    
}
