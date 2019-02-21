/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service;

import java.sql.Timestamp;
import java.util.List;

import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.Response;

/**
 *
 * @author Usuario
 */
public interface ComprobanteService {

    public Integer getTotal();

    public void getAll();

    public void save(Comprobante entity);

    public void update(Comprobante entity);

    public void delete(Comprobante entity);

    public Comprobante getByID(long id, String ruc);
    
    public List<ComprobanteView> getComprobantePendiente(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);

    public List<ComprobanteView> getComprobanteAutorizado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);

    public List<ComprobanteView> getComprobanteRechazado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);

    public List<ComprobanteView> getComprobanteContingencia(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);

    public List<ComprobanteView> getComprobantePorAutorizar(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);
    
    public List<ComprobanteView> getComprobanteAnulado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);
    
    public List<ComprobanteView> getComprobanteInconsistente(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor);

    public Integer getCount(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaInit,
            Timestamp fechaFin,
            List<Integer> inIds, 
            int ultimo,
            int anulado,
            int offline,
            String codInterlocutor);
    
     public Integer getNroRegistros(String identificador,String tipoDoc,String estado);

    public Comprobante getPDF(long id, String ruc);

    public Comprobante getXML(long id, String ruc);

    public Comprobante getLOG(long id, String ruc);

    public Comprobante getEmailsDestino(long id, String ruc);
    
    public Response cancelarNotificacionCorreo(String nroSri, String tipoDocumento);
    
    public Response autorizarRechazadoClaveAccesoRegistrado(String ruc, String nroSri, String tipoDoc);
    
}
