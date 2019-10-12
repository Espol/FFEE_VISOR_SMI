/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.monitor.service.GeneracionReporteService;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.EmailUtil;
import com.csti.ce.util.ReporteUtil;
import com.csti.ce.util.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Usuario
 */
@Service
@Transactional
public class GeneracionReporteServiceImpl implements GeneracionReporteService {
    
    @Autowired
    ParametroDAO parametroDAO;
    @Autowired
    ComprobanteDAO comprobanteroDAO;
    
    private long identificador;
    private String ruc;
    private Response response = new Response();
    protected final Logger log = Logger.getLogger(GeneracionReporteServiceImpl.class);
    private Comprobante comprobante;
    private boolean sendEmail = false;
    private String ambiente;
    
    @Override
    public void generarPDF() {
        try {
            ReporteUtil reporteUtil = new ReporteUtil();
            reporteUtil.setSociedad(this.getSociedad());
            reporteUtil.setDocumento(this.getComprobante());
            reporteUtil.setParametroDAO(parametroDAO);
            reporteUtil.setNombreDocumento(this.getNombreDocumento());
            reporteUtil.generarPDF();
            response.setSuccess(true);
            response.setMessage("Se genero PDF correctamente.");
            this.enviar();
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("identificador", comprobante.getIdComprobante());
            maps.put("nroSRI", comprobante.getNroSri());
            this.response.setData(maps);
            this.update();
        } catch (ParametroReaderException e) {
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
    }
    
    @Override
    public void enviar() {
        if (this.sendEmail) {
            try {
                EmailUtil email = new EmailUtil();
                email.setAmbiente(ambiente);
                email.setComprobante(this.comprobante);
                email.setParametroDAO(parametroDAO);
                email.setMensaje(this.comprobante.getMensaje());
                email.envioEmailAutorizado(null);
                response.setMessage("No se envio E-mail.");
            } catch (Exception ex) {
                response.setMessage("Se envió E-mail.");
            }
        }
    }
    
    @Override
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
    
    private String getCodDocumento() {
        return this.comprobante.getTipoDoc();
    }
    
    private String getTipoDocReferencia() {
        return this.comprobante.getTipoDocReferencia();
    }
    
    private String getDocReferencia() {
        return this.comprobante.getDocReferencia();
    }
    
    private String getNroSRI() {
        return this.comprobante.getNroSri();
    }
    
    protected String getNombreDocumento() {
        return comprobante.getNombreDocumento();
    }
    
    public String getNombreTipoDocumento() {
        return AplicacionUtil.obtenerComprobanteByCodigo(this.getCodDocumento());
    }
    
    public Comprobante getComprobante() {
        this.comprobante = comprobanteroDAO.getByID(this.identificador, this.ruc);
        return comprobante;
    }
    
    public Parametro getSociedad() {
        return this.parametroDAO.getSociedad(this.ruc);
    }
    
    @Override
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
    
    @Override
    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }
    
    @Override
    public Response getRespuesta() {
        return this.response;
    }
    
    @Override
    public void sendEmail(int enviar) {
        this.sendEmail = (enviar == AplicacionConstants.SUCCESS);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void update() {
        comprobanteroDAO.update(this.getComprobante());
    }
}
