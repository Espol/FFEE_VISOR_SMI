/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.EscenarioConstant;
import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.monitor.service.EnvioEmailService;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.EmailUtil;
import com.csti.ce.util.Response;

/**
 *
 * @author Usuario
 */
@Service
@Transactional
public class EnvioEmailSerivceImpl implements EnvioEmailService {

    protected final Logger log = Logger.getLogger(EnvioEmailSerivceImpl.class);
    private Comprobante comprobante;
    private long identificador;
    private String ambiente;
    @Autowired
    private ParametroDAO parametroDAO;
    private String mensaje;
    private String ruc;
    private Response response;
    private String emailDestino;
    @Autowired
    private ComprobanteDAO comprobanteDAO;

    @Autowired
    private SociedadDAO sociedadDAO;
//    private boolean success = false;

    @Override
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    @Override
    public void setEmailsDestino(String emails) {
        this.emailDestino = emails;
    }

    @Override
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public void envioEmailAutorizado() {
        EmailUtil email = new EmailUtil();
        this.response = new Response();
        try {
            comprobante = this.comprobanteDAO.getByID(this.identificador, this.ruc);

            Sociedad sociedad = this.sociedadDAO.getSociedad(this.ruc);
            
            
            this.validar();
            if (!emailDestino.isEmpty()) {
                comprobante.setEmailDestino(emailDestino);
            }
            email.setAmbiente(this.ambiente);
            email.setComprobante(comprobante);
            email.setMensaje(this.mensaje);
            email.setParametroDAO(this.parametroDAO);
            email.setParametroSociedad(this.getParametroSociedad());
            email.envioEmailAutorizado( sociedad );
            comprobante.setEmail(AplicacionConstants.SUCCESS);
            this.response.setSuccess(true);
            this.response.setMessage("Se envio email con éxito.");
        } catch (Exception e) {
            this.response.setMessage(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update() {
        comprobanteDAO.update(comprobante);
    }

    @Override
    public Response getRespuesta() {
        return this.response;
    }

    private Parametro getParametroSociedad() {
        return this.parametroDAO.getSociedad(this.ruc);
    }

    private void validar() throws Exception {
        if (this.ambiente == null) {
            throw new Exception("No se  reconocio el Ambiente.");
        }
        if (this.comprobante == null) {
            throw new Exception("Comprobante no válido.");
        }
        if (this.comprobante.getPdf() == AplicacionConstants.FAIL) {
            throw new Exception("No hay un archivo PDF relacionado.");
        }
        if (!AplicacionUtil.evaluarEscenario(this.comprobante.getEscenario(), EscenarioConstant.AUTORIZADO)) {
            throw new Exception("El documento debe estar Autorizado");
        }
    }

    @Override
    public void envioEmailNotificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
