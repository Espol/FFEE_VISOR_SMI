/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao;

import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.ComprobanteExcel;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface ComprobanteDAO {

//    public void setAmbiente(String ambiente);
    public void save(Comprobante entity);

    public void update(Comprobante entity);

    public void delete(Comprobante entity);

    public Comprobante getByID(long id, String ruc);

    public Comprobante getByUltimo(long id, String ruc);
    
    public Comprobante getByUltimo(String id,String tipoDoc, String ruc);
    
    public Integer getNroRegistros(String identificador,String tipoDoc,String estado);

    public List<Comprobante> getByEscenario(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaDesde,
            Timestamp fechaHasta,
            int initRow,
            int countRow,
            List<Integer> escenarios,
            int ultimo,
            int anulado,
            int offline,
            String codInterlocutor);

    public Integer getCount(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaDesde,
            Timestamp fechaHasta,
            List<Integer> escenarios,
            int ultimo,
            int anulado,
            int offline,
            String codInterlocutor);
    
    public Comprobante getComprobateRechazadoClaveAccesoRechazado(String ruc, String nroSri, String tipoDoc);
    
    public List<Comprobante> getByReporteByExcel(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaDesde,
            Timestamp fechaHasta,
            String interlocutor,
            List<Integer> escenarios,
            int ultimo,
            int anulado,
            int offline);
}
