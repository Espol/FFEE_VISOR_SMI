/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.monitor.domain;

import java.util.Date;

/**
 *
 * @author MARCELO
 */
public class ComprobanteExcel implements java.io.Serializable {

    private long idComprobante;
    private String ruc;
    private String nroSri;
    private String docReferencia;
    private Date fechaEmision;
    private String tipoDoc;
    private String tipoDocReferencia;
    private int tipoEmision;
    private String claveAcceso;
    private String nroAutorizacion;
    private String fechaAutorizacion;
    private String estado;
    private String usuario;
    private String emailDestino;
    private String montoTotal;
    private String interlocutor;
    private String sociedad;
    private String razonSocial;
    private String rucCliente;
    private String nroPila;
    private String observacion;
    private String docSustento;
    private int escenario;
    private int ultimo;
    private int offline;

    public long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNroSri() {
        return nroSri;
    }

    public void setNroSri(String nroSri) {
        this.nroSri = nroSri;
    }

    public String getDocReferencia() {
        return docReferencia;
    }

    public void setDocReferencia(String docReferencia) {
        this.docReferencia = docReferencia;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getTipoDocReferencia() {
        return tipoDocReferencia;
    }

    public void setTipoDocReferencia(String tipoDocReferencia) {
        this.tipoDocReferencia = tipoDocReferencia;
    }

    public int getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(int tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRucCliente() {
        return rucCliente;
    }

    public void setRucCliente(String rucCliente) {
        this.rucCliente = rucCliente;
    }

    public String getNroPila() {
        return nroPila;
    }

    public void setNroPila(String nroPila) {
        this.nroPila = nroPila;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDocSustento() {
        return docSustento;
    }

    public void setDocSustento(String docSustento) {
        this.docSustento = docSustento;
    }

    public int getEscenario() {
        return escenario;
    }

    public void setEscenario(int escenario) {
        this.escenario = escenario;
    }

    public int getUltimo() {
        return ultimo;
    }

    public void setUltimo(int ultimo) {
        this.ultimo = ultimo;
    }

    public int getOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }
    
}
