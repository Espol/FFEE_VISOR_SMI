/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.util;

import static com.csti.ce.constant.EscenarioConstant.AUTORIZADO;
import static com.csti.ce.constant.EscenarioConstant.CONTINGENCIA;
import static com.csti.ce.constant.EscenarioConstant.ESTA_AUTORIZADO;
import static com.csti.ce.constant.EscenarioConstant.INCONSISTENTE;
import static com.csti.ce.constant.EscenarioConstant.POR_AUTORIZAR;
import static com.csti.ce.constant.EscenarioConstant.RECHAZADO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import com.csti.ce.conectoredocs.util.ConectorUtil;
import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.constant.MailConstant;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.exception.MailException;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;

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
    private Parametro parametroSociedad;
    private String ambiente;
    private String Mensaje;
    
    private Sociedad sociedad;
                       
    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setParametroSociedad(Parametro parametroSociedad) {
        this.parametroSociedad = parametroSociedad;
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

    private String getAmbiente() {
        return ambiente;
    }

    private String getEstado() {
        return comprobante.getEstado();
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

    private String getRuc() {
        return comprobante.getRuc();
    }

    private String getNroSRI() {
        return comprobante.getNroSri();
    }

    private void setEmail(int email) {
        comprobante.setEmail(email);
    }
    private void setNotificacion(int notificacion) {
        comprobante.setNotificacion(notificacion);
    }

    private String getPathXML() {
        return comprobante.getPathXml();
    }

    private String getPathPDF() {
        return comprobante.getPathPdf();
    }   

    private String getNombreTipoDocumento() {
        return AplicacionUtil.obtenerComprobanteByCodigo(comprobante.getTipoDoc());
    }

    public void envioEmailAutorizado(Sociedad sociedad) throws MailException, Exception {
        try {
            if ( this.getPdf() == AplicacionConstants.SUCCESS ) {
                log.info("[EMAIL] Enviando de Email a: " + this.getEmailsDestino());
                
                EnviarNotificacion notificacion = EnviarNotificacion.AUTORIZADO;
                String sociedadArgs[] = {sociedad.getRuc(),sociedad.getRazonSocial(),"","","none"};
                
                MailSetting ms = MailSettingUtil.getInstance().toObject( sociedad.getConfMail() );
       
		        List<String> adjunto = new ArrayList();
		        adjunto.add( this.getPathXML() );
		        adjunto.add( this.getPathPDF() );
                
                boolean enviado = notificacion.enviarCorreo(
										sociedadArgs,
										this.getMailProperties(ms), 
										AplicacionUtil.obtenerComprobanteByCodigo( this.getTipoDocumento() ).toUpperCase(), 
                                        this.getNroSRI(), 
                                        this.getMensaje(), 
                                        this.getEmailsDestino(), 
                                        null, 
                                        null, 
                                        adjunto);
                System.out.println("enviado " + enviado);
                
                
              
//            String result = ConectorUtil.mailAutorizado(strRuc, strAmbiente, strDestino, tipoComprobante, nroSri, strPathXml, strPathPdf);
//                ANULACION_OFFLINE
//                String result = ConectorUtil.mailAutorizado(this.getRuc(), 
//                        this.getEstado(), 
//                        this.getAmbiente(), 
//                        this.getEmailsDestino(), 
//                        null, 
//                        null, 
//                        this.getNombreTipoDocumento(), 
//                        this.getNroSRI(), 
//                        this.getPathXML(),                         
//                        this.getPathPDF(),
//                        AplicacionConstants.ESTACIO_NULL,
//                        AplicacionConstants.ESTACIO_NULL,
//                        AplicacionConstants.ESTACIO_NULL,
//                        this.getMensaje());
//                if (!result.equalsIgnoreCase("ENVIADO")) {
//                    log.error(result);
//                    throw new MailException("No se Envió el EMAIL: " + this.getNroSRI());
//                }
                this.setEmail(AplicacionConstants.SUCCESS);
                log.info("[EMAIL] Se envió el Email correctamente.");
            } else {
                log.info("[EMAIL] No se envio Email por permisos de Configuración: ");
            }
        } catch (Exception e) {
            throw new MailException("[EMAIL] Ocurrió un error al enviar el EMAIL. " + e.getMessage());
        }
    }

    public void envioEmailNotificacion() throws MailException, Exception {
        try {
            if (this.getPARAMETER_NOTIFICAR_POR_STATE() == AplicacionConstants.SUCCESS) {
                log.info("[EMAIL] Enviando EMAIL de notificacion.");
                String emailsDestino = this.getPARAMETER_NOTIFICAR_EMAILS();
                String estado = (this.getEstado() == ESTA_AUTORIZADO) ? AUTORIZADO : this.getEstado();
//                ANULACION_OFFLINE
//                String result = ConectorUtil.mailNotificacion(this.getRuc(), 
//                        this.getAmbiente(), 
//                        estado, 
//                        emailsDestino, 
//                        null, 
//                        null, 
//                        this.getNombreTipoDocumento(), 
//                        this.getNroSRI(), 
//                        this.getPathXML(), 
//                        this.getMensaje());
//                if (!result.equalsIgnoreCase("ENVIADO")) {
//                    log.error(result);
//                    throw new MailException("No se Envió el Notificación: " + this.getNroSRI());
//                }
                this.setNotificacion(AplicacionConstants.SUCCESS);
                log.info("[EMAIL] Se envió el Notificación correctamente.");
            } else {
                log.info("[EMAIL] No se envió la Notificación por permisos de Configuración");
            }
        } catch (Exception e) {
            throw new MailException("[EMAIL] Ocurrió un error al enviar la NOTIFICACION. " + e.getMessage());
        }
    }

    private int getPARAMETER_SEND_EMAIL_POR_STATE() throws ParametroReaderException {
        int sendEmail = 0;
        if (this.getEstado().equalsIgnoreCase(AUTORIZADO) || this.getEstado().equalsIgnoreCase(ESTA_AUTORIZADO)) {
            sendEmail = Integer.parseInt(this.getParametroValue(ParametroConstants.SEND_EMAIL_AUTORIZADO_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(CONTINGENCIA)) {
            sendEmail = Integer.parseInt(this.getParametroValue(ParametroConstants.SEND_EMAIL_CONTINGENCIA_MONITOR));
        }
        return sendEmail;
    }

    private int getPARAMETER_NOTIFICAR_POR_STATE() throws ParametroReaderException {
        int notificar = 0;
        if (this.getEstado().equalsIgnoreCase(AUTORIZADO) || this.getEstado().equalsIgnoreCase(ESTA_AUTORIZADO)) {
            notificar = Integer.parseInt(this.getParametroValue(ParametroConstants.NOTIFICAR_AUTORIZADO_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(CONTINGENCIA)) {
            notificar = Integer.parseInt(this.getParametroValue(ParametroConstants.NOTIFICAR_CONTINGENCIA_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(POR_AUTORIZAR)) {
            notificar = Integer.parseInt(this.getParametroValue(ParametroConstants.NOTIFICAR_POR_AUTORIZAR_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(RECHAZADO)) {
            notificar = Integer.parseInt(this.getParametroValue(ParametroConstants.NOTIFICAR_RECHAZADO_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(INCONSISTENTE)) {
            notificar = Integer.parseInt(this.getParametroValue(ParametroConstants.NOTIFICAR_INCONSISTENTE_MONITOR));
        }
        return notificar;
    }
    
    private String getPARAMETER_NOTIFICAR_EMAILS() throws ParametroReaderException {
        String email = "";
        switch (this.getTipoDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                email = this.getParametroValue(MailConstant.MAIL_NOTIFICACION_FACTURA);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                email = this.getParametroValue(MailConstant.MAIL_NOTIFICACION_GUIA_REMISION);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                email = this.getParametroValue(MailConstant.MAIL_NOTIFICACION_NOTA_CREDITO);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                email = this.getParametroValue(MailConstant.MAIL_NOTIFICACION_NOTA_DEBITO);
                break;
            case ComprobanteConstants.COD_RETENCION:
                email = this.getParametroValue(MailConstant.MAIL_NOTIFICACION_RETENCION);
                break;
        }
        return email;
    }

    private String getParametroValue(String Campo) throws ParametroReaderException {
        return this.parametroDAO.getParametroByCampo(this.parametroSociedad.getParametroHigh(), Campo).getParametroLow();
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
