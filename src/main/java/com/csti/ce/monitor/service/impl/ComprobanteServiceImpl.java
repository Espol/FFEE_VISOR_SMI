/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.EscenarioConstant;
import com.csti.ce.integrador.dao.DocumentoDAO;
import com.csti.ce.integrador.dao.EnvioDocumentoDAO;
import com.csti.ce.integrador.domain.Documento;
import com.csti.ce.integrador.domain.EnvioDocumento;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.service.ComprobanteService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.Response;

/**
 *
 * @author Usuario
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ComprobanteServiceImpl implements ComprobanteService {

    @Autowired
    ComprobanteDAO comprobanteDAO;
    private Integer total;
    
    @Autowired
    DocumentoDAO documentoDAO;
    
    @Autowired
    EnvioDocumentoDAO envioDocumentoDAO;

    public Integer getTotal() {
        return total;
    }

    @Override
    public void getAll() {
//        comprobanteDAO.getAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Comprobante entity) {
        comprobanteDAO.save(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Comprobante entity) {
        comprobanteDAO.update(entity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Comprobante entity) {
        comprobanteDAO.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Comprobante getByID(long id, String ruc) {
        return comprobanteDAO.getByID(id, ruc);
    }

    @Override
    public List<ComprobanteView> getComprobanteAutorizado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.SUCCESS;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.AUTORIZADO);
        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }
    
    @Override
    public List<ComprobanteView> getComprobanteRechazado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.SUCCESS;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.RECHAZADO);
        
        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }

    @Override
    public List<ComprobanteView> getComprobanteContingencia(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.SUCCESS;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.CONTINGENCIA);

        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }
    
    @Override
    public List<ComprobanteView> getComprobanteAnulado(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor){
        int ultimo = AplicacionConstants.FAIL;
        int anulado=AplicacionConstants.SUCCESS;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.ANULADO);

        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc,tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc,tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }  

    @Override
    public List<ComprobanteView> getComprobantePorAutorizar(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.SUCCESS;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.POR_AUTORIZAR);

        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }

    @Override
    public List<ComprobanteView> getComprobanteInconsistente(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.FAIL;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.INCONSISTENTE);
        
        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }

    @Override
    public List<ComprobanteView> getComprobantePendiente(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            String fechaInit,
            String fechaFin,
            int initRow,
            int countRow,
            int offline,
            String codInterlocutor) {
        int ultimo = AplicacionConstants.SUCCESS;
        int anulado=AplicacionConstants.FAIL;
        List<Integer> inIds = AplicacionUtil.obtenerEscenarioByEstado(EscenarioConstant.PENDIENTE);

        Timestamp fechaDesde = null;
        Timestamp fechaHasta = null;
        
        if( fechaInit !=null && !fechaInit.isEmpty() && fechaFin != null && !fechaFin.isEmpty() ){
        	fechaDesde = AplicacionUtil.getDefaultDate(fechaInit + " " + AplicacionConstants.MIN_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);
        	fechaHasta = AplicacionUtil.getDefaultDate(fechaFin + " " + AplicacionConstants.MAX_HOUR_MIN_SEG, AplicacionConstants.DATETIME_FORMAT);        	
        }
        
        this.total = this.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, inIds, ultimo,anulado,offline,codInterlocutor);
        return this.getView(comprobanteDAO.getByEscenario(ruc, tipoDoc, nroSri, docReferencia, fechaDesde, fechaHasta, initRow, countRow, inIds, ultimo,anulado,offline,codInterlocutor));
    }

    @Override
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
            String codInterlocutor) {
        return comprobanteDAO.getCount(ruc, tipoDoc, nroSri, docReferencia, fechaInit, fechaFin, inIds, ultimo,anulado,offline,codInterlocutor);
    }

    private List<ComprobanteView> getView(List<Comprobante> list) {
        List<ComprobanteView> comprobanteViews = new ArrayList<>();
        for (Comprobante comprobante : list) {
            ComprobanteView item = new ComprobanteView();
            item.setId(comprobante.getIdComprobante());
            item.setNroSri(comprobante.getNroSri());
            item.setDocReferencia(comprobante.getDocReferencia());
            item.setFechaEmision(AplicacionUtil.transformerDate(comprobante.getFechaEmision(), AplicacionConstants.DATE_FORMAT));
            item.setFechaRegistro(AplicacionUtil.transformerDate(comprobante.getFechaRegistro(), AplicacionConstants.DATE_FORMAT));
            item.setTipoDoc(comprobante.getTipoDoc());
            item.setTipoDocReferencia(comprobante.getTipoDocReferencia());
            item.setTipoEmision(AplicacionUtil.evaluarTipoEmision(comprobante.getTipoEmision()));
            item.setNroAutorizacion(comprobante.getNroAutorizacion());
            item.setFechaAutorizacion(comprobante.getFechaAutorizacion());
            item.setContabilizado(comprobante.getContabilizado());
            item.setAnulado(comprobante.getAnulado());
            item.setUsuario(comprobante.getUsuario());
            item.setTerminal(comprobante.getTerminal());
            item.setEmail(comprobante.getEmail());
            item.setPdf(comprobante.getPdf());
            item.setNotificacion(comprobante.getNotificacion());
            item.setInterlocutor(comprobante.getInterlocutor());
            item.setRazonSocial(comprobante.getRazonSocial());
            comprobanteViews.add(item);
        }
        return comprobanteViews;
    }

    @Override
    public Comprobante getPDF(long id, String ruc) {
        return comprobanteDAO.getByID(id, ruc);
    }

    @Override
    public Comprobante getXML(long id, String ruc) {
        return comprobanteDAO.getByID(id, ruc);
    }

    @Override
    public Comprobante getLOG(long id, String ruc) {
        return comprobanteDAO.getByID(id, ruc);
    }

    @Override
    public Comprobante getEmailsDestino(long id, String ruc) {
        return comprobanteDAO.getByID(id, ruc);
    }
  
    @Override
    public Integer getNroRegistros(String identificador,String tipoDoc, String estado) {
       return comprobanteDAO.getNroRegistros(identificador,tipoDoc, estado);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response cancelarNotificacionCorreo(String nroSri, String tipoDocumento) {
		// TODO Auto-generated method stub
		Response response = new Response();
		documentoDAO.updateDocumento(nroSri, tipoDocumento, 1);
		response.setMessage("Cambio estado de Notificacion de Correo para Documento Rechazados");
		response.setSuccess(true);
		return response;
		
	}
        
   @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response autorizarRechazadoClaveAccesoRegistrado(String ruc, String nroSri, String tipoDoc) {
        Comprobante comprobante = comprobanteDAO.getComprobateRechazadoClaveAccesoRechazado(ruc, nroSri, tipoDoc);
        Response response = new Response();
        if (comprobante != null && comprobante.getMensaje().contains("CLAVE ACCESO REGISTRADA")) {
            try {
                if (verificarDestinoAutorizacion(envioDocumentoDAO.getEnvioDocumentoRechazado(ruc, nroSri, tipoDoc),true)) {
                    Documento doc = documentoDAO.getDocumento(ruc, nroSri, tipoDoc);
                    EnvioDocumento ev = new EnvioDocumento();
                    ev.setDestino("ST");
                    ev.setEstado("PE");
                    ev.setIdPeriodo(doc.getIdPeriodo());
                    ev.setIdSociedad(doc.getIdSociedad());
                    ev.setSecuencia(doc.getSecuencia());
                    documentoDAO.updateDocumento(nroSri, tipoDoc, 1);
                    documentoDAO.updateDocumentoRechazado(doc.getIdPeriodo(), doc.getIdSociedad(), doc.getSecuencia());
                    envioDocumentoDAO.save(ev);
                    response.setSuccess(true);
                    response.setMessage("El documento se cambio de estado para que proceda con su autorización");
                } else {
                    response.setMessage("Verificar que el documento este Autorizado, comunicarse con sistemas.");
                }
            } catch (Exception ex) {
                response.setMessage("Error Comuniquese con Sistema: " + ex.getMessage());
            }
        } else {
            response.setMessage("en Documento " + nroSri + " No existe o no esta rechazado");
        }
        return response;
    }
    
    private boolean verificarDestinoAutorizacion(List<EnvioDocumento> envios, boolean flag) {
        if (flag) {
            for (EnvioDocumento ev : envios) {
                if (ev.getDestino().equalsIgnoreCase("ST")) {
                    return false;
                }
            }
        } else {
            for (EnvioDocumento ev : envios) {
                if (ev.getDestino().equalsIgnoreCase("ST") && !(ev.getEstado().equalsIgnoreCase("TE") || ev.getEstado().equalsIgnoreCase("PE"))) {
                    return false;
                }
            }
        }

        return true;
    }
}
