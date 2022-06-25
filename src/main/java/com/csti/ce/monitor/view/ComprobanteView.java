/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.view;

/**
 *
 * @author Usuario
 */
public class ComprobanteView {

    private long id;
    private String ruc;
    private String nroSri;
    private String docReferencia;
    private String fechaEmision;
    private String fechaRegistro;
    private String tipoDoc;
    private String tipoDocReferencia;
    private String tipoEmision;
    private String claveAcceso;
    private String nroAutorizacion;
    private String fechaAutorizacion;
    private int contabilizado;
    private int anulado;
    private String usuario;
    private String terminal;
    private int email;
    private int notificacion;
    private int pdf;
    private String estado;
    private String razonSocial;
    private String interlocutor;
    private String docSustendo;
    private String motivo;
    private String emailDestino;
    private String montoTotal;
    private String rucCliente;
    private String nroPila;

    public ComprobanteView() {
    }

    public ComprobanteView(long id, String nroSri, String docReferencia, String fechaEmision, String fechaRegistro, String tipoDoc, String tipoDocReferencia, String nroAutorizacion, String fechaAutorizacion, int contabilizado, int anulado, String usuario, String terminal, int email, int notificacion, int pdf, String estado) {
        this.id = id;
        this.nroSri = nroSri;
        this.docReferencia = docReferencia;
        this.fechaEmision = fechaEmision;
        this.fechaRegistro = fechaRegistro;
        this.tipoDoc = tipoDoc;
        this.tipoDocReferencia = tipoDocReferencia;
        this.nroAutorizacion = nroAutorizacion;
        this.fechaAutorizacion = fechaAutorizacion;
        this.contabilizado = contabilizado;
        this.anulado = anulado;
        this.usuario = usuario;
        this.terminal = terminal;
        this.email = email;
        this.notificacion = notificacion;
        this.pdf = pdf;
        this.estado = estado;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the nroSri
     */
    public String getNroSri() {
        return nroSri;
    }

    /**
     * @param nroSri the nroSri to set
     */
    public void setNroSri(String nroSri) {
        this.nroSri = nroSri;
    }

    /**
     * @return the docReferencia
     */
    public String getDocReferencia() {
        return docReferencia;
    }

    /**
     * @param docReferencia the docReferencia to set
     */
    public void setDocReferencia(String docReferencia) {
        this.docReferencia = docReferencia;
    }

    /**
     * @return the fechaEmision
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     *
     * @return
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the tipoDoc
     */
    public String getTipoDoc() {
        return tipoDoc;
    }

    /**
     * @param tipoDoc the tipoDoc to set
     */
    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    /**
     * @return the tipoDocReferencia
     */
    public String getTipoDocReferencia() {
        return tipoDocReferencia;
    }

    /**
     * @param tipoDocReferencia the tipoDocReferencia to set
     */
    public void setTipoDocReferencia(String tipoDocReferencia) {
        this.tipoDocReferencia = tipoDocReferencia;
    }

    /**
     * @return the nroAutorizacion
     */
    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    /**
     * @param nroAutorizacion the nroAutorizacion to set
     */
    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    /**
     * @return the fechaAutorizacion
     */
    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    /**
     * @param fechaAutorizacion the fechaAutorizacion to set
     */
    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    /**
     * @return the contabilizado
     */
    public int getContabilizado() {
        return contabilizado;
    }

    /**
     * @param contabilizado the contabilizado to set
     */
    public void setContabilizado(int contabilizado) {
        this.contabilizado = contabilizado;
    }

    /**
     * @return the anulado
     */
    public int getAnulado() {
        return anulado;
    }

    /**
     * @param anulado the anulado to set
     */
    public void setAnulado(int anulado) {
        this.anulado = anulado;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the terminal
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * @param terminal the terminal to set
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * @return the email
     */
    public int getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(int email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public int getNotificacion() {
        return notificacion;
    }

    /**
     *
     * @param notificacion
     */
    public void setNotificacion(int notificacion) {
        this.notificacion = notificacion;
    }

    /**
     * @return the pdf
     */
    public int getPdf() {
        return pdf;
    }

    /**
     * @param pdf the pdf to set
     */
    public void setPdf(int pdf) {
        this.pdf = pdf;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the tipoEmision
     */
    public String getTipoEmision() {
        return tipoEmision;
    }

    /**
     * @param tipoEmision the tipoEmision to set
     */
    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getDocSustendo() {
        return docSustendo;
    }

    public void setDocSustendo(String docSustendo) {
        this.docSustendo = docSustendo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

}
