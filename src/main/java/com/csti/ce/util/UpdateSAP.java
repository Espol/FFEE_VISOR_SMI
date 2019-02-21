/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.util;

import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.constant.ConexionSAPConstants;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.updatesap.AccesoSAP;
import com.csti.ce.updatesap.ActualizarComprobante;
import com.csti.ce.updatesap.UpdateSapException;

/**
 *
 * @author Usuario
 */
public class UpdateSAP implements AccesoSAP {

    ParametroDAO parametroDAO;
    Parametro sociedad;

    public void setSociedad(Parametro sociedad) {
        this.sociedad = sociedad;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    private String getParametroValue(String Campo) throws ParametroReaderException {
        return this.parametroDAO.getParametroByCampo(this.sociedad.getParametroHigh(), Campo).getParametroLow();
    }

    @Override
    public String getMandante() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_MANDANTE);
    }

    @Override
    public String getUsuario() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_USUARIO);
    }

    @Override
    public String getContrasena() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_PASSWORD);
    }

    @Override
    public String getIdioma() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_IDIOMA);
    }

    @Override
    public String getIPServidor() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_IP_CONFIGURACION);
    }

    @Override
    public String getNumeroInstancia() throws Exception {
        return this.getParametroValue(ConexionSAPConstants.SAP_NUMERO_INSTANCIA);
    }

    public void actualizar(String nroSri,
            String docReferencia,
            String claveAcceso,
            String codigoDocumento,
            String numeroAutorizacion,
            String ruc,
            String usuario, int codEstado) throws UpdateSapException {
        nroSri = AplicacionUtil.formatNroSriSAP(nroSri, codigoDocumento);
        ActualizarComprobante actualizarComprobante = new ActualizarComprobante(this);
        actualizarComprobante.comprobanteEnSAP(nroSri, docReferencia,
                claveAcceso, codigoDocumento, numeroAutorizacion,
                ruc, usuario, codEstado);
    }

    
}
