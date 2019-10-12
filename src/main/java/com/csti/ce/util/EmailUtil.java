/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.util;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import com.csti.ce.conectoredocs.util.ConectorUtil;
import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.exception.MailException;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import ec.incloud.ce.notificacion.EnviarNotificacion;
import ec.incloud.ce.notificacion.MailProperties;

/**
 *
 * @author Usuario
 */
public class EmailUtil {

    ParametroDAO parametroDAO;
    protected final Logger log = Logger.getLogger(EmailUtil.class);
    private Comprobante comprobante;
    private String ambiente;
    private String Mensaje;
                       
    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    private String getTipoDocumento() {
        return comprobante.getTipoDoc();
    }

    private int getPdf() {
        return comprobante.getPdf();
    }

    private String getEmailsDestino() {
        return comprobante.getEmailDestino();
    }

    private String getNroSRI() {
        return comprobante.getNroSri();
    }

    private void setEmail(int email) {
        comprobante.setEmail(email);
    }

    private String getPathXML() {
        return comprobante.getPathXml();
    }

    private String getPathPDF() {
        return comprobante.getPathPdf();
    }  

    public void envioEmailAutorizado(Sociedad sociedad) throws MailException, Exception {
        try {
            if ( this.getPdf() == AplicacionConstants.SUCCESS ) {
                log.info("[EMAIL] Enviando de Email a: " + this.getEmailsDestino());
                
//                ReenvioNotificacion notificacion = new ReenvioNotificacion();
                EnviarNotificacion notificacion = EnviarNotificacion.AUTORIZADO;
                String soc[] = {sociedad.getRuc(),sociedad.getRazonSocial(),"","","none"};
                
                MailSetting ms = MailSettingUtil.getInstance().toObject( sociedad.getConfMail() );
       
		        List<String> adjunto = new ArrayList();
		        adjunto.add( this.getPathXML() );
		        adjunto.add( this.getPathPDF() );
                
                boolean enviado = notificacion.enviarCorreo(soc, this.getMailProperties(ms), 
					AplicacionUtil.obtenerComprobanteByCodigo( this.getTipoDocumento() ).toUpperCase(), 
                                        this.getNroSRI(), 
                                        this.getMensaje(), 
                                        this.getEmailsDestino(), 
                                        null, 
                                        null, 
                                        adjunto);
                System.out.println("enviado " + enviado);
               
                this.setEmail(AplicacionConstants.SUCCESS);
                log.info("[EMAIL] Se envió el Email correctamente.");
            } else {
                log.info("[EMAIL] No se envio Email por permisos de Configuración: ");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new MailException("[EMAIL] Ocurrió un error al enviar el EMAIL. " + e.getMessage());
        }
    }

    public void envioEmailNotificacion() {
         
    }
    
    private MailProperties getMailProperties(final MailSetting ms) {
        return new MailProperties() {

            @Override
            public String getHost() {
                return ms.getHost();
            }

            @Override
            public String getPort() {
                return ms.getPort();
            }

            @Override
            public String getUsuario() {
                return ms.getUser();
            }

            @Override
            public String getPassword() {
                return ms.getPassword();
            }
        };
    }
}
