<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.constant.EscenarioConstant;
import static com.csti.ce.constant.EscenarioConstant.*;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.constant.SapConstant;
import com.csti.ce.constant.TipoEmisionConstants;
import com.csti.ce.monitor.service.Emision;
import com.csti.ce.exception.AutorizacionSRIException;
import com.csti.ce.exception.ClaveAccesoException;
import com.csti.ce.exception.FirmaException;
import com.csti.ce.exception.MailException;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.exception.RecepcionSRIException;
import com.csti.ce.exception.ReporteException;
import com.csti.ce.exception.SinRespuestaSRIAutorizacionException;
import com.csti.ce.exception.SinRespuestaSRIRecepcionException;
import com.csti.ce.exception.ValidacionXSDException;
import com.csti.ce.exception.domain.Mensaje;
import com.csti.ce.integrador.dao.DocumentoAnuladoDAO;
import com.csti.ce.integrador.dao.ImpuestoDAO;
import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.DocumentoAnulado;
import com.csti.ce.integrador.domain.Impuesto;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.pojos.comprobante.webservices.Respuesta;
import com.csti.ce.sri.services.AutorizarXMLaSRI;
import com.csti.ce.sri.services.EnviarXMLaSRI;
import com.csti.ce.sri.ssl.CertificadosSSL;
import com.csti.ce.updatesap.UpdateSapException;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.FileUtil;
import com.csti.ce.util.LogManager;
import com.csti.ce.util.EmailUtil;
import com.csti.ce.util.ReporteUtil;
import com.csti.ce.util.UpdateSAP;
import com.csti.ce.util.util;
import com.sap.ws.qas.service.SapServiceImpl;
//import com.smi.sap.ws.qas.service.SapServiceImpl;
import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.pdf.services.PdfFactory;
import ec.incloud.ce.pdf.services.PdfServices;
import ec.incloud.ce.xml.exception.XmlException;
import ec.incloud.ce.xml.services.XmlFactory;
import ec.incloud.ce.xml.services.XmlServices;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
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
public class EmisionImpl implements Emision {

    String identificador;
    String tipoDoc;
    @Autowired
    ParametroDAO parametroDAO;
    @Autowired
    ComprobanteDAO comprobanteDAO;
    Parametro sociedad;
    protected final Logger log = Logger.getLogger(EmisionImpl.class);
    protected LogManager logManager = new LogManager();
    Comprobante comprobanteUltimo;
    Comprobante comprobanteCurrent;
    Respuesta respuesta;
    String ruc;
    String ambiente;
    String usuario;

    private Impuesto impuesto = null;

    @Autowired
    DocumentoAnuladoDAO documentoDAO;

    @Autowired
    SociedadDAO sociedadDAO;
    
    @Autowired
    ImpuestoDAO impuestoDAO;

    @Override
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public Parametro getSociedad() {
        return sociedad;
    }

    @Override
    public Comprobante getDocumento() {
        return comprobanteCurrent;
    }

    @Override
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getAmbiente() {
        return ambiente;
    }

    @Override
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    private String getRuc() {
        return this.comprobanteCurrent.getRuc();
    }

    // Inicio del flujo del comprobante con solucion al inconveniente por autorizar
    // Modificacion método iniciar: Ammiel
    //-----------------------------------------------------------------------------------
    public void iniciar() throws Exception {
        comprobanteCurrent = new Comprobante();
        this.respuesta = new Respuesta();
        sociedad = this.parametroDAO.getSociedad(this.ruc);
        comprobanteUltimo = comprobanteDAO.getByUltimo(identificador, tipoDoc, ruc);
        if (comprobanteUltimo != null && comprobanteUltimo.getEstado().equals(POR_AUTORIZAR)) {
            int de = comprobanteDAO.getNroRegistros(identificador, tipoDoc, POR_AUTORIZAR);
            if (de >= 3 && comprobanteUltimo.getPathXml().contains("\\porAutorizar")) {
                //     comprobanteUltimo.setTipoEmision(TipoEmisionConstants.EN_CONTINGENCIA);
                comprobanteUltimo.setRecepcionado(AplicacionConstants.FAIL);
                String prueba = comprobanteUltimo.getPathXml();
                String prueba1 = prueba.replace("\\porAutorizar", "");
                comprobanteUltimo.setPathXml(prueba1);
                comprobanteDAO.update(comprobanteUltimo);
            } else if (de >= 3 && comprobanteUltimo.getPathXml().contains("\\contingencia")) {
                comprobanteUltimo.setRecepcionado(AplicacionConstants.FAIL);
                comprobanteDAO.update(comprobanteUltimo);
            }
        }
        if (comprobanteUltimo == null) {
            throw new Exception("Este Documento no está apto para el Proceso.");
        }
        this.configuracionComprobante();
        try {
            this.setPathLog(this.getPARAMETER_LOG_PATH_TEXT() + this.getNombreDocumento() + ".log");
        } catch (ParametroReaderException ex) {
            this.setPathLog("c://log/" + this.getNombreDocumento() + ".log");
            log.info("[INICIO] Nose asigno un Log personalizado, se asigno un directorio por defecto.");
        }
        logManager.init(this.getPathLog());
    }

    //------------------------------------------------------------------------------------------------------
    //  Método para anular el comprobante
    //  By: Ammiel
    @Override
    public String anularComprobante() {
        log.info("[BD] Anulando comprobante.");
//        sociedad = this.parametroDAO.getSociedad(this.ruc);
        
        comprobanteUltimo = comprobanteDAO.getByUltimo(identificador, tipoDoc, ruc);
        SapServiceImpl sap = new SapServiceImpl();
        String fechaAndHora[] = comprobanteUltimo.getFechaAutorizacion().split(" ");
        
        Sociedad s = sociedadDAO.getSociedad(this.ruc);
        s.getConfSap();
        String user = util.getCadenaTextoEntre(s.getConfSap(), "<usuario>", "</usuario>");
        String pass = util.getCadenaTextoEntre(s.getConfSap(), "<contrasena>", "</contrasena>");
        
        String sri[] = comprobanteUltimo.getNroSri().split("-");
        String nroSri = sri[0]+"-"+sri[1]+"-"+sri[2].substring(1);
        
        Map<Object, String> map = sap.anularDocumentoSap(user, pass, util.ChangeFormatDate(fechaAndHora[0], "dd/MM/yyyy","yyyy-MM-dd"), comprobanteUltimo.getUsuario(), fechaAndHora[1], nroSri, comprobanteUltimo.getDocReferencia(), this.ruc, comprobanteUltimo.getTipoDoc());
//        Map<Object, String> map = new HashMap<Object, String>();
//        map.put("mensaje", "mensaje");
//        map.put("valor", "0");
        if(map.get("valor").equals("0")){
            if (comprobanteUltimo != null) {
                comprobanteUltimo.setAnulado(AplicacionConstants.SUCCESS);
                comprobanteUltimo.setUltimo(AplicacionConstants.FAIL);
                comprobanteUltimo.setEstado(AplicacionUtil.obtenerEstadoByEscenario(EscenarioConstant.AUTORIZADO_ANULADO));
                comprobanteUltimo.setEscenario(EscenarioConstant.AUTORIZADO_ANULADO);
                generacionPDFAnulado(s);

                comprobanteDAO.update(comprobanteUltimo);
            }
            log.info("[DB] Anulación correcta");
            return "Comprobante Anulado:" + identificador;
        } else {
            return map.get("mensaje");
        }
    }

    private void generacionPDFAnulado(Sociedad s ) {
        DocumentoAnulado doc = documentoDAO.getDocumento(this.ruc, comprobanteUltimo.getNroSri(), tipoDoc);
        
        
        comprobanteUltimo.setPathPdf(FilenameUtils.removeExtension(doc.getPathXML()) + ".pdf");

        XmlServices xmlServices = this.getXmlServices(doc);
        PdfServices pdfServices = this.getPdfServices(doc);

        if (xmlServices != null && pdfServices != null) {
            
            this.getImpuestoIvaByDocumento(doc, xmlServices);//{+ MML

            String[] sociedad = new String[5];
            sociedad[0] = ruc;
            sociedad[1] = s.getUrl();
            sociedad[2] = s.getTextoRide();
            sociedad[3] = "";
            sociedad[4] = "";

            String[] documentoParam = {""};
            try {
                pdfServices.generarPdfAnulado(xmlServices.getComprobanteDePathArchivo(doc.getPathXML()),
                        FilenameUtils.removeExtension(doc.getPathXML()) + ".pdf",
                        comprobanteUltimo.getNroAutorizacion(),
                        comprobanteUltimo.getFechaAutorizacion(),
                        sociedad,
                        documentoParam,
                        this.getIvaDinamico()
                );
                
                
            } catch (XmlException ex) {
                log.error("Error al generar PDF", ex);
            }
        }
    }

    private String getIvaDinamico() {
        if (impuesto != null) {
            return "" + impuesto.getPorcentaje();
        }
        return "";
    }

    private void getImpuestoIvaByDocumento(DocumentoAnulado documento, XmlServices xmlServices) {

        Object comprobante;

        String codigo = "";
        String codPorcentaje = "";

        try {
            comprobante = xmlServices.getComprobanteDePathArchivo(documento.getPathXML());
            switch (documento.getTipoDocumento()) {
                case "01":
                    if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                        Factura factura = (Factura) comprobante;
                        for (TotalImpuesto imp : factura.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    } else if (documento.getSubTipoDoc() == 2) {
                        FacturaExportacion exportacion = (FacturaExportacion) comprobante;

                        for (TotalImpuesto imp : exportacion.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    } else if (documento.getSubTipoDoc() == 3) {
                        FacturaReembolso reembolso = (FacturaReembolso) comprobante;

                        for (TotalImpuesto imp : reembolso.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    }
                    break;
                case "04":
                    NotaCredito credito = (NotaCredito) comprobante;
                    for (TotalImpuesto imp : credito.getInfoNotaCredito().getTotalConImpuestos()) {
                        if (imp.getCodigo().equalsIgnoreCase("2")) {
                            codigo = imp.getCodigo();
                            codPorcentaje = imp.getCodigoPorcentaje();
                        }
                    }
                    getImpuesto(codigo, codPorcentaje);
                    break;
                case "05":
                    NotaDebito debito = (NotaDebito) comprobante;
                    for (ec.incloud.ce.bean.common.Impuesto imp : debito.getInfoNotaDebito().getImpuestos()) {
                        if (imp.getCodigo().equalsIgnoreCase("2")) {
                            codigo = imp.getCodigo();
                            codPorcentaje = imp.getCodigoPorcentaje();
                        }
                    }
                    getImpuesto(codigo, codPorcentaje);
                    break;
                default:
                    break;
            }
        } catch (XmlException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getImpuesto(String codigo, String codPorcentaje) {
        impuesto = impuestoDAO.getImpuesto(Integer.parseInt(codigo), Integer.parseInt(codPorcentaje));
    }

    private XmlServices getXmlServices(DocumentoAnulado documento) {
        XmlServices xmlServices = null;

        switch (documento.getTipoDocumento()) {
            case "01":
                if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                    xmlServices = XmlFactory.getFacturaXmlServices();
                } else if (documento.getSubTipoDoc() == 2) {
                    xmlServices = XmlFactory.getFacturaExportacionXmlServices();
                } else if (documento.getSubTipoDoc() == 3) {
                    xmlServices = XmlFactory.getFacturaReembolsoXmlServices();
                }
                break;
            case "04":
                xmlServices = XmlFactory.getNotaCreditoXmlServices();
                break;
            case "05":
                xmlServices = XmlFactory.getNotaDebitoXmlServices();
                break;
            case "06":
                xmlServices = XmlFactory.getGuiaRemisionXmlServices();
                break;
            case "07":
                xmlServices = XmlFactory.getRetencionServices();
                break;
            default:
                break;
        }
        return xmlServices;
    }

    private PdfServices getPdfServices(DocumentoAnulado documento) {
        PdfServices pdfServices = null;

        switch (documento.getTipoDocumento()) {
            case "01":
                if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                    pdfServices = PdfFactory.createFacturaPdfService();
                } else if (documento.getSubTipoDoc() == 2) {
                    pdfServices = PdfFactory.creatFacturaExportacionPdfService();
                } else if (documento.getSubTipoDoc() == 3) {
                    pdfServices = PdfFactory.createPdfFacturaReembolsoServices();
                }
                break;
            case "04":
                pdfServices = PdfFactory.createPdfNotaCreditoServices();
                break;
            case "05":
                pdfServices = PdfFactory.createPdfNotaDebitoServices();
                break;
            case "06":
                pdfServices = PdfFactory.createPdfGuiaRemisionServices();
                break;
            case "07":
                pdfServices = PdfFactory.createPdfComprobanteRetencionServices();
                break;
            default:
                break;
        }
        return pdfServices;
    }

    //----------------------------------------------------------------------------------------------------
    private void validarComprobante() throws Exception {
        if (this.ruc == null && this.ruc.isEmpty()) {
            throw new Exception("ruc no Válido");
        }
        if (this.getDocumento().getContabilizado() != AplicacionConstants.SUCCESS) {
            throw new Exception("Este documento no esta Contabilizado.");
        }
    }

    private void configuracionComprobante() {
        comprobanteCurrent.setNroSri(comprobanteUltimo.getNroSri());
        comprobanteCurrent.setDocReferencia(comprobanteUltimo.getDocReferencia());
        comprobanteCurrent.setFechaEmision(comprobanteUltimo.getFechaEmision());
        comprobanteCurrent.setTipoDoc(comprobanteUltimo.getTipoDoc());
        comprobanteCurrent.setTipoDocReferencia(comprobanteUltimo.getTipoDocReferencia());
        comprobanteCurrent.setTipoEmision(comprobanteUltimo.getTipoEmision());
        comprobanteCurrent.setRuc(comprobanteUltimo.getRuc());
        comprobanteCurrent.setClaveAcceso(comprobanteUltimo.getClaveAcceso());
        comprobanteCurrent.setAnulado(comprobanteUltimo.getAnulado());
        comprobanteCurrent.setContabilizado(comprobanteUltimo.getContabilizado());
        comprobanteCurrent.setXml(comprobanteUltimo.getXml());
        comprobanteCurrent.setDocumentXML(comprobanteUltimo.getDocumentXML());
        comprobanteCurrent.setPathXml(comprobanteUltimo.getPathXml());
        comprobanteCurrent.setRecepcionado(comprobanteUltimo.getRecepcionado());
        comprobanteCurrent.setAutorizado(comprobanteUltimo.getAutorizado());
        comprobanteCurrent.setUsuario((usuario == null) ? comprobanteUltimo.getUsuario() : usuario);
        comprobanteCurrent.setTerminal(comprobanteUltimo.getTerminal());
        comprobanteCurrent.setEmailDestino(comprobanteUltimo.getEmailDestino());
        comprobanteCurrent.setUltimo(AplicacionConstants.SUCCESS);
        comprobanteCurrent.setNombreDocumento(comprobanteUltimo.getNombreDocumento());
    }

    /**
     * Asigna los Mensajes de Respuestas
     *
     * @param mensajes
     */
    public void setMensajes(List<Mensaje> mensajes) {
        log.info("Registrando mensajes de Respuesta");
        if (this.respuesta != null) {
            if (this.respuesta.getMensajes() != null) {
                if (this.respuesta.getMensajes().size() > 0) {
                    for (Mensaje mensaje : mensajes) {
                        this.respuesta.getMensajes().add(mensaje);
                    }
                } else {
                    this.respuesta.setMensajes(mensajes);
                }
            } else {
                this.respuesta.setMensajes(mensajes);
            }
        }
    }

    /**
     * Devuelve el Respuesta de Flujo.
     *
     * @return
     */
    @Override
    public Respuesta getRespuesta() {
        this.respuesta.setEscenario(this.getEscenario());
        this.respuesta.setEstado(this.getEstado());
        this.respuesta.setNroAutorizacion(this.getNroAutorizacion());
        this.respuesta.setClaveAcceso(this.getClaveAcceso());
        this.respuesta.setNroSri(this.getNroSRI());
        this.respuesta.setPdf(this.getPdf());
        this.respuesta.setEmail(this.getEmail());
        this.respuesta.setContabilizar(this.getContabilizar());
        if (this.respuesta.getMensajes() != null) {
            for (Mensaje mensaje : this.respuesta.getMensajes()) {
                log.info("[RESPUESTA] [" + mensaje.getTipo() + "] "
                        + mensaje.getIdentificador() + "-"
                        + mensaje.getDescripcion() + "");
                log.info("\t Adicional: " + mensaje.getAdicional());
            }
        }
        log.info("Escenario:" + this.respuesta.getEscenario() + "\t ESTADO: " + respuesta.getEstado());
//        
        return this.respuesta;
    }

    public void setEmail(int email) {
        this.comprobanteCurrent.setEmail(email);
    }

    public int getEmail() {
        return this.comprobanteCurrent.getEmail();
    }

    public int getPdf() {
        return this.comprobanteCurrent.getPdf();
    }

    private void setContabilizar(int contabilizar) {
        this.comprobanteCurrent.setContabilizado(contabilizar);
    }

    public int getContabilizar() {
        return this.comprobanteCurrent.getContabilizado();
    }

    public void setEscenario(int escenario) {
        this.comprobanteCurrent.setEscenario(escenario);
        this.comprobanteCurrent.setEstado(AplicacionUtil.obtenerEstadoByEscenario(escenario));
    }

    public int getEscenario() {
        return comprobanteCurrent.getEscenario();
    }

    public String getEstado() {
        return this.comprobanteCurrent.getEstado();
    }

    private boolean esNormal() {
        return (this.comprobanteUltimo.getTipoEmision() == TipoEmisionConstants.NORMAL);
    }

    private void setPathLog(String pathLog) {
        this.comprobanteCurrent.setLog(pathLog);
    }

    private String getPathLog() {
        return this.comprobanteCurrent.getLog();
    }

    private String getCodDocumento() {
        return this.comprobanteCurrent.getTipoDoc();
    }

    private String getTipoDocReferencia() {
        return this.comprobanteCurrent.getTipoDocReferencia();
    }

    private String getDocReferencia() {
        return this.comprobanteCurrent.getDocReferencia();
    }

    private String getNroSRI() {
        return this.comprobanteCurrent.getNroSri();
    }

    private void setRecepcion(int recepcion) {
        this.comprobanteCurrent.setRecepcionado(recepcion);
    }

    private void setAutorizacion(int autorizacion) {
        this.comprobanteCurrent.setAutorizado(autorizacion);
    }

    private void setXML(String xml) {
        this.comprobanteCurrent.setXml(xml);
    }

    private String getXML() {
        return this.comprobanteCurrent.getXml();
    }

    private void setPathXML(String pathXML) {
        this.comprobanteCurrent.setPathXml(pathXML);
    }

    private String getPathXML() {
        return this.comprobanteCurrent.getPathXml();
    }

    private String getClaveAcceso() {
        return this.comprobanteCurrent.getClaveAcceso();
    }

    private void setNroAutorizacion(String nroAutorizacion) {
        this.comprobanteCurrent.setNroAutorizacion(nroAutorizacion);
    }

    private String getNroAutorizacion() {
        return this.comprobanteCurrent.getNroAutorizacion();
    }

    private void setFechaAutorizacion(String fechaAutorizacion) {
        this.comprobanteCurrent.setFechaAutorizacion(fechaAutorizacion);
    }

    private boolean esComprobanteRecepcionado() {
        return (comprobanteUltimo.getRecepcionado() == 1);
    }

    private boolean esComprobanteAutorizado() {
        return (comprobanteUltimo.getAutorizado() == 1);
    }

    protected void enviarEmailDocumentoAutorizado() throws MailException, Exception {
        EmailUtil email = new EmailUtil();
        email.setAmbiente(ambiente);
        email.setComprobante(comprobanteCurrent);
        email.setParametroDAO(parametroDAO);
        email.setMensaje(this.getMensaje());
        email.envioEmailAutorizado(null);
    }

    private String getMensaje() throws Exception {
        String str = "";
        try {
            if (this.respuesta != null) {
                if (this.respuesta.getMensajes() != null) {
                    for (Mensaje mensaje : this.respuesta.getMensajes()) {
                        str += "[" + mensaje.getTipo() + "][" + mensaje.getIdentificador() + "]";
                        if (mensaje.getDescripcion() != null) {
                            str += " " + mensaje.getDescripcion() + "\n";
                        }
                        if (mensaje.getAdicional() != null) {
                            str += "\t-" + mensaje.getAdicional() + "\n\n";
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Ocurrió un error al extraer mensaje.");
        }

        return str;
    }

    protected void enviarEmailNotificacion() throws MailException, ParametroReaderException, Exception {
        EmailUtil email = new EmailUtil();
        email.setAmbiente(ambiente);
        email.setComprobante(comprobanteCurrent);
        email.setParametroDAO(parametroDAO);
        email.setMensaje(this.getMensaje());
        email.envioEmailNotificacion();
    }

    protected void generarPDF() throws ReporteException, Exception {
        ReporteUtil reporteUtil = new ReporteUtil();
        reporteUtil.setSociedad(sociedad);
        reporteUtil.setParametroDAO(parametroDAO);
        reporteUtil.setNombreDocumento(this.getNombreDocumento());
        reporteUtil.setDocumento(comprobanteCurrent);
        reporteUtil.generarPDF();
    }

    /**
     * Se procede al envio del comprobante al SRI.
     *
     * @throws RecepcionSRIException Excepción por XML rechazado en recepción. Este tipo de excepción incluye una lista de mensajes de errores que indican el motivo de su rechazo.
     * @throws SinRespuestaSRIRecepcionException Se produce esta excepción cuando no hay comunicación con el SRI. El estado del comprobante actual es CONTINGENCIA, lo cual indica que NO se emitirá un comprobante en contingencia por esta excepción.
     */
    private void enviarSRI() throws RecepcionSRIException,
            SinRespuestaSRIRecepcionException,
            Exception {
        EnviarXMLaSRI enviar = new EnviarXMLaSRI();
        try {
            log.info("Enviando Documento SRI");
            String pathXML = this.getPathXML();
            String wsdl = this.getPARAMETER_WSDL_RECEPCION();
            int intentos = this.getPARAMETER_WSDL_RECEPCION_NRO_INTENTOS();
            int tiempoEspera = this.getPARAMETER_WSDL_RECEPCION_TIEMPO_ESPERA();
            enviar.setPathXML(pathXML);
            enviar.setWSDL(wsdl);
            enviar.setIntentos(intentos);
            enviar.setTiempoEspera(tiempoEspera);
            enviar.enviarXML();
            this.setRecepcion(AplicacionConstants.SUCCESS);
        } catch (RecepcionSRIException ex) {
            log.info("Documento Rechazado - nro Autorización");
            for (Mensaje mensaje : ex.getListMensajes()) {
                if (mensaje.getIdentificador().equals("43")) {
                    this.setRecepcion(AplicacionConstants.SUCCESS);
                    return;
                }
            }
            String pathXml = this.getPARAMETER_PATH_RECHAZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
//            setXML(enviar.getRespuestaXML())       
            this.writeFile(this.getPathXML());
            throw ex;
        }
    }

    private void writeFile(String pathXml) throws Exception {
        FileUtil fileUtil = new FileUtil();
        fileUtil.writeFileText(pathXml, this.getXML());
    }

    /**
     * Se procede a la autorización del comprobante al SRI mediante la clave de acceso.
     *
     * @throws AutorizacionSRIException Se produce esta excepcion cuando el comprobante fue rechazado por el SRI. Este tipo de excepción incluye una lista de mensajes de errores que indican el motivo de su rechazo.
     * @throws SinRespuestaSRIAutorizacionException Excepción al no tener conexión con el SRI en la autorización del comprobante.
     */
    protected void autorizarSRI() throws AutorizacionSRIException,
            SinRespuestaSRIAutorizacionException,
            Exception {
        log.info("Autorizando Documento");
        AutorizarXMLaSRI autorizar = new AutorizarXMLaSRI();
        String claveAcceso = this.getClaveAcceso();
        String wsdl = this.getPARAMETER_WSDL_AUTORIZACION();
        int intentos = this.getPARAMETER_WSDL_AUTORIZACION_NRO_INTENTOS();
        int tiempoEspera = this.getPARAMETER_WSDL_AUTORIZACION_TIEMPO_ESPERA();
        try {
            autorizar.setClaveAcceso(claveAcceso);
            autorizar.setWSDL(wsdl);
            autorizar.setIntentos(intentos);
            autorizar.setTiempoEspera(tiempoEspera);
            autorizar.autorizarXML();
            this.setAutorizacion(AplicacionConstants.SUCCESS);
            this.setRecepcion(AplicacionConstants.SUCCESS);
            this.setNroAutorizacion(autorizar.getNroAutorizacion());
            this.setFechaAutorizacion(autorizar.getFechaAutorizacion());
            log.info("Documento Autorizado - nro Autorización : " + this.getNroAutorizacion());
            String pathXml = this.getPARAMETER_PATH_AUTORIZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
            setXML(autorizar.getRespuestaXML());
            this.writeFile(this.getPathXML());
        } catch (AutorizacionSRIException e) {
            this.setNroAutorizacion(autorizar.getNroAutorizacion());
            this.setFechaAutorizacion(autorizar.getFechaAutorizacion());
            log.info("Documento Rechazado - nro Autorización");
            String pathXml = this.getPARAMETER_PATH_RECHAZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
            setXML(autorizar.getRespuestaXML());
            this.writeFile(this.getPathXML());
            throw e;
        }
    }

    public void emitir() {
        try {
            this.iniciar();
            this.validarComprobante();
            CertificadosSSL ssl = new CertificadosSSL();
            ssl.instalarCertificados();
            if (esNormal()) {
                AutorizarNormal();
            } else {
                autorizarContingencia();
            }
        } catch (ParametroReaderException ex) {
//            this.setEscenario(NUEVO_INCONSISTENTE);
            log.error("[PARAMETROS] Error al cargar los Parámetros.", ex);
            this.setMensajes(ex.getMensajes());
        } catch (FirmaException ex) {
            this.setEscenario(NUEVO_INCONSISTENTE);
            this.setMensajes(ex.getMensajes());
            log.error("[FIRMA ELECTRONICA] Error al firmar XML ", ex);
        } catch (MailException ex) {
//            this.setEscenario((this.getEscenario() != 0 ? this.getEscenario() : NUEVO_INCONSISTENTE));
            log.error("[ERROR] MailException: ", ex);

            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", ex.getMessage()));
            this.setMensajes(mensajes);
            ex.printStackTrace();
        } catch (Exception ex) {
            this.setEscenario((this.getEscenario() == 0) ? NUEVO_INCONSISTENTE : this.getEscenario());
            log.error("[ERROR] ", ex);

            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "Ocurrió un Error: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        }
        try {
            this.registrarComprobante();
//          this.actualizarSAP();
        } catch (UpdateSapException ex) {
            log.info("[UPDATE-SAP] No se actualizó en documento en SAP: " + ex.getMessage());
            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "No se actualizó en documento en SAP: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        } catch (Exception ex) {
            log.info("[REGISTRO] Ocurrió un error en el proceso de registro:", ex);
            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "Ocurrió un Error: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        }
    }

    private void actualizarSAP() throws UpdateSapException {
        int esc = this.comprobanteCurrent.getEscenario();
        if (AplicacionUtil.evaluarEscenario(esc, AUTORIZADO)
                || AplicacionUtil.evaluarEscenario(esc, RECHAZADO)) {
            log.info("[LEGACY] preparando para enviar a Legacy. ");
            UpdateSAP updateSAP = new UpdateSAP();
            int codEstado = SapConstant.COD_ESTADO_FAIL;
            if (AplicacionUtil.evaluarEscenario(this.getEscenario(), EscenarioConstant.AUTORIZADO)) {
                codEstado = SapConstant.COD_ESTADO_AUTORIZADO;
            } else if (AplicacionUtil.evaluarEscenario(this.getEscenario(), EscenarioConstant.RECHAZADO)) {
                codEstado = SapConstant.COD_ESTADO_RECHAZADO;
            }
            if (codEstado != SapConstant.COD_ESTADO_FAIL) {
                updateSAP.setSociedad(sociedad);
                updateSAP.setParametroDAO(parametroDAO);
                updateSAP.actualizar(this.getNroSRI(), this.getDocReferencia(),
                        this.getClaveAcceso(), this.getCodDocumento(),
                        this.getNroAutorizacion(), this.getRuc(),
                        this.getDocumento().getUsuario(), codEstado);
            }
            log.info("[LEGACY] Se envió correctamente. ");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    protected void registrarComprobante() throws ParametroReaderException, Exception {
        log.info("[BD] Registrando Documento.");
        if (comprobanteCurrent != null) {
            comprobanteCurrent.setFechaRegistro(new Date());
            comprobanteCurrent.setMensaje(this.getMensaje());
            if (AplicacionUtil.evaluarEscenario(comprobanteCurrent.getEscenario(), EscenarioConstant.INCONSISTENTE)
                    || AplicacionUtil.evaluarEscenario(comprobanteCurrent.getEscenario(), EscenarioConstant.POR_ENVIAR)) {
                comprobanteCurrent.setUltimo(AplicacionConstants.FAIL);
                comprobanteDAO.save(comprobanteCurrent);
            } else {
                comprobanteCurrent.setUltimo(AplicacionConstants.SUCCESS);
                comprobanteDAO.save(comprobanteCurrent);
                comprobanteUltimo.setUltimo(AplicacionConstants.FAIL);
                comprobanteDAO.update(comprobanteUltimo);
            }
            log.info("[DB] Registro correcto");
        }

    }

    public void AutorizarNormal() throws ParametroReaderException, Exception {
        if (esComprobanteRecepcionado()) {
            if (esComprobanteAutorizado()) {
                //Mensaje de Error: el comprobante ya fue autorizado
                this.setEscenario(AUTORIZAR_AUTORIZAR);
                log.error("Clave de acceso registrada, el comprobante ya fue autorizado");
                List<Mensaje> mensajes = new ArrayList<>();
                mensajes.add(new Mensaje("ERROR", "Clave de acceso registrada, el comprobante ya fue autorizado"));
                this.setMensajes(mensajes);
                this.traspasarComprobante();
                this.generarPDF();
                this.enviarEmailDocumentoAutorizado();
                this.enviarEmailNotificacion();
            } else {
                //Comprobante POR AUTORIZAR;
                FlujoPorAutorizar();
            }
        } else {
            FlujoAutorizarContingencia();
            //No aplica: El comprobante Normal  para su Recepcion y Autorizacion 
        }
    }

    public void autorizarContingencia() throws ParametroReaderException, Exception {
        //Para autorizar Documento en Contingencia
        if (esComprobanteRecepcionado()) {
            if (esComprobanteAutorizado()) {
                //Mensaje de Error: el comprobante ya fue autorizado
                this.setEscenario(AUTORIZAR_AUTORIZAR);
                log.error("Clave de acceso registrada, el comprobante ya fue autorizado");
                List<Mensaje> mensajes = new ArrayList<>();
                mensajes.add(new Mensaje("ERROR", "Clave de acceso registrada, el comprobante ya fue autorizado"));
                this.setMensajes(mensajes);
                this.traspasarComprobante();
                this.generarPDF();
                this.enviarEmailDocumentoAutorizado();
                this.enviarEmailNotificacion();
            } else {
                //Comprobante POR AUTORIZAR;
                FlujoPorAutorizar();
            }
        } else {
            FlujoAutorizarContingencia();
            //Comprobante PARA Recepcionar y Autorizar
        }
    }

    public void FlujoPorAutorizar() throws ParametroReaderException, Exception {
        try {
            log.info("[FLUJO POR AUTORIZAR] Inicio del proceso");
//            this.porAutorizar();
            this.autorizarSRI();

            this.setEscenario(POR_AUTORIZAR_AUTORIZADO);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            log.info("[FLUJO POR AUTORIZAR] Documento Autorizado");
            this.generarPDF();
            this.enviarEmailDocumentoAutorizado();
            this.enviarEmailNotificacion();
        } catch (ClaveAccesoException ex) {
            this.setEscenario(POR_AUTORIZAR_INCONSISTENTE);
            log.info("[FLUJO POR AUTORIZAR] Error al comparar la Clave de Acceso.");
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (ReporteException ex) {
            log.info("[FLUJO POR AUTORIZAR] Error al Generar el PDF");
            this.setMensajes(ex.getMensajes());
        } catch (MailException ex) {
            log.info("[FLUJO POR AUTORIZAR] Error al Enviar el EMail");
            this.setMensajes(ex.getMensajes());
        } catch (AutorizacionSRIException ex) {
            log.info("[FLUJO POR AUTORIZAR] Rechazado por el SRI, al Autorizar el documento");
            this.setEscenario(POR_AUTORIZAR_RECHAZADO);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIAutorizacionException ex) {
            log.info("[FLUJO POR AUTORIZAR] Sin respuesta en autorizacion");
            this.setEscenario(POR_AUTORIZAR_POR_AUTORIZAR);
            this.setNroAutorizacion(AplicacionConstants.NRO_AUTORIZACION_POR_AUTORIZAR);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        }
    }

    public void FlujoAutorizarContingencia() throws ParametroReaderException, Exception {
        try {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] iniciando Proceso de Autorización.");
//            this.autorizarContingencia();
            this.enviarSRI();
            this.autorizarSRI();
            this.setEscenario(CONTINGENCIA_AUTORIZADO);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Documento Autorizado.");
            //Crear PDF, Enviar Email.
            this.generarPDF();
            this.enviarEmailDocumentoAutorizado();
            this.enviarEmailNotificacion();
        } catch (ReporteException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Error al Generar el PDF");
            this.setMensajes(ex.getMensajes());
        } catch (MailException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Error al Enviar el EMail");
            this.setMensajes(ex.getMensajes());
        } catch (ValidacionXSDException ex) {
            log.error("[FLUJO AUTORIZAR CONTINGENCIA] XML no cumple con la estructura ", ex);
            this.setEscenario(CONTINGENCIA_INCONSISTENTE);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (RecepcionSRIException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Rechazado por el SRI, al Recepcionar el documento.");
            this.setEscenario(CONTINGENCIA_RECHAZADO);
            this.setMensajes(ex.getListMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIRecepcionException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Sin respuesta en recepcion");
            this.setEscenario(CONTINGENCIA_POR_ENVIAR);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (AutorizacionSRIException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Rechazado por el SRI,al Autorizar el documento.");
            this.setEscenario(CONTINGENCIA_RECHAZADO);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIAutorizacionException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Sin respuesta en autorizacion");
            this.setEscenario(CONTINGENCIA_POR_AUTORIZAR);
            this.setNroAutorizacion(AplicacionConstants.NRO_AUTORIZACION_POR_AUTORIZAR);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        }

    }

    private void traspasarComprobante() {
        log.info("Traspasando valores del documento." + this.comprobanteUltimo.getNroSri());
        this.comprobanteCurrent.setClaveAcceso(this.comprobanteUltimo.getClaveAcceso());
        this.comprobanteCurrent.setNroAutorizacion(this.comprobanteUltimo.getNroAutorizacion());
        this.comprobanteCurrent.setFechaAutorizacion(this.comprobanteUltimo.getFechaAutorizacion());
//        this.documentObject.setContabilizado(comprobante.getContabilizado());
        this.comprobanteCurrent.setXml(this.comprobanteUltimo.getXml());
        this.comprobanteCurrent.setDocumentXML(this.comprobanteUltimo.getDocumentXML());
        this.comprobanteCurrent.setPathXml(this.comprobanteUltimo.getPathXml());
        this.comprobanteCurrent.setPathPdf(this.comprobanteUltimo.getPathPdf());
        this.comprobanteCurrent.setPdf(this.comprobanteUltimo.getPdf());
        this.comprobanteCurrent.setEmail(this.comprobanteUltimo.getEmail());
        this.comprobanteCurrent.setRecepcionado(this.comprobanteUltimo.getRecepcionado());
        this.comprobanteCurrent.setAutorizado(this.comprobanteUltimo.getAutorizado());
    }

    /**
     * Genera el nombre del documento Ingresado, el nombre esta compuesto por: - Nombre del tipo de documento - Tipo de documento de referencia - Documento de referencia - Número de SRI - Fecha y hora
     *
     * @return
     */
    protected String getNombreDocumento() {
        String nombre = "";
        if (comprobanteCurrent.getNombreDocumento() == null
                || comprobanteCurrent.getNombreDocumento().isEmpty()) {
            nombre = this.getNombreTipoDocumento() + "-"
                    + this.getTipoDocReferencia() + "-" + this.getDocReferencia() + "-" + this.getNroSRI();

        } else {
            nombre = comprobanteCurrent.getNombreDocumento();
        }
        return nombre;
    }

    public String getNombreTipoDocumento() {
        return AplicacionUtil.obtenerComprobanteByCodigo(this.getCodDocumento());
    }

    public String getPARAMETER_LOG_PATH_TEXT() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.LOG_PATH_TEXT);
    }

    public String getPARAMETER_WSDL_RECEPCION() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.RECEPCION_WSDL);
    }

    public int getPARAMETER_WSDL_RECEPCION_NRO_INTENTOS() throws ParametroReaderException {
        int intentos = Integer.parseInt(this.getParametroValue(ParametroConstants.RECEPCION_INTENTOS));
        return intentos;
    }

    public int getPARAMETER_WSDL_RECEPCION_TIEMPO_ESPERA() throws ParametroReaderException {
        int tiempo = Integer.parseInt(this.getParametroValue(ParametroConstants.RECEPCION_ESPERA));
        return tiempo;
    }

    public String getPARAMETER_WSDL_AUTORIZACION() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.AUTORIZACION_WSDL);
    }

    public int getPARAMETER_WSDL_AUTORIZACION_NRO_INTENTOS() throws ParametroReaderException {
        int intentos = Integer.parseInt(this.getParametroValue(ParametroConstants.AUTORIZACION_INTENTOS));
        return intentos;
    }

    public int getPARAMETER_WSDL_AUTORIZACION_TIEMPO_ESPERA() throws ParametroReaderException {
        int tiempo = Integer.parseInt(this.getParametroValue(ParametroConstants.AUTORIZACION_ESPERA));
        return tiempo;
    }

    public String getPARAMETER_PATH_RECHAZADO() throws ParametroReaderException {
        String pathRechazado = null;
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                pathRechazado = this.getParametroValue(ParametroConstants.FACTURA_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                pathRechazado = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                pathRechazado = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                pathRechazado = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_RETENCION:
                pathRechazado = this.getParametroValue(ParametroConstants.RETENCION_PATH_RECHAZADO);
                break;
        }
        if (pathRechazado == null) {
            throw new ParametroReaderException("No se ha encontrado un Directorio.");
        }
        return pathRechazado;
    }

    public String getPARAMETER_PATH_AUTORIZADO() throws ParametroReaderException {
        String pathAutorizado = null;
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                pathAutorizado = this.getParametroValue(ParametroConstants.FACTURA_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                pathAutorizado = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                pathAutorizado = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                pathAutorizado = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_RETENCION:
                pathAutorizado = this.getParametroValue(ParametroConstants.RETENCION_PATH_AUTORIZACION);
                break;
        }
        if (pathAutorizado == null) {
            throw new ParametroReaderException("No se ha encontrado un Directorio.");
        }
        return pathAutorizado;
    }

    private int getPARAMETER_CONTABILIZAR_FOR_STATE() throws ParametroReaderException {
        int contabilizar = 0;
        if (this.getEstado().equalsIgnoreCase(AUTORIZADO) || this.getEstado().equalsIgnoreCase(ESTA_AUTORIZADO)) {
            contabilizar = AplicacionConstants.SUCCESS;
        } else if (this.getEstado().equalsIgnoreCase(POR_AUTORIZAR)) {
            contabilizar = Integer.parseInt(this.getParametroValue(ParametroConstants.CONTABILIZAR_POR_AUTORIZAR));
        } else if (this.getEstado().equalsIgnoreCase(CONTINGENCIA)) {
            contabilizar = Integer.parseInt(this.getParametroValue(ParametroConstants.CONTABILIZAR_CONTINGENCIA));
        }
        return contabilizar;
    }

    private String getParametroValue(String Campo) throws ParametroReaderException {
        return this.parametroDAO.getParametroByCampo(this.sociedad.getParametroHigh(), Campo).getParametroLow();
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.service.impl;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.constant.EscenarioConstant;
import static com.csti.ce.constant.EscenarioConstant.*;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.constant.SapConstant;
import com.csti.ce.constant.TipoEmisionConstants;
import com.csti.ce.monitor.service.Emision;
import com.csti.ce.exception.AutorizacionSRIException;
import com.csti.ce.exception.ClaveAccesoException;
import com.csti.ce.exception.FirmaException;
import com.csti.ce.exception.MailException;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.exception.RecepcionSRIException;
import com.csti.ce.exception.ReporteException;
import com.csti.ce.exception.SinRespuestaSRIAutorizacionException;
import com.csti.ce.exception.SinRespuestaSRIRecepcionException;
import com.csti.ce.exception.ValidacionXSDException;
import com.csti.ce.exception.domain.Mensaje;
import com.csti.ce.integrador.dao.DocumentoAnuladoDAO;
import com.csti.ce.integrador.dao.ImpuestoDAO;
import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.DocumentoAnulado;
import com.csti.ce.integrador.domain.Impuesto;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.pojos.comprobante.webservices.Respuesta;
import com.csti.ce.sri.services.AutorizarXMLaSRI;
import com.csti.ce.sri.services.EnviarXMLaSRI;
import com.csti.ce.sri.ssl.CertificadosSSL;
import com.csti.ce.updatesap.UpdateSapException;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.FileUtil;
import com.csti.ce.util.LogManager;
import com.csti.ce.util.EmailUtil;
import com.csti.ce.util.ReporteUtil;
import com.csti.ce.util.UpdateSAP;
import com.csti.ce.util.util;
import com.ws.sap.prd.service.SapImpl;
//import com.sap.ws.qas.service.SapServiceImpl;
import ec.incloud.ce.bean.common.TotalImpuesto;
import ec.incloud.ce.bean.credito.NotaCredito;
import ec.incloud.ce.bean.debito.NotaDebito;
import ec.incloud.ce.bean.factura.Factura;
import ec.incloud.ce.bean.facturaExportacion.FacturaExportacion;
import ec.incloud.ce.bean.facturaReembolso.FacturaReembolso;
import ec.incloud.ce.pdf.services.PdfFactory;
import ec.incloud.ce.pdf.services.PdfServices;
import ec.incloud.ce.xml.exception.XmlException;
import ec.incloud.ce.xml.services.XmlFactory;
import ec.incloud.ce.xml.services.XmlServices;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
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
public class EmisionImpl implements Emision {

    String identificador;
    String tipoDoc;
    @Autowired
    ParametroDAO parametroDAO;
    @Autowired
    ComprobanteDAO comprobanteDAO;
    Parametro sociedad;
    protected final Logger log = Logger.getLogger(EmisionImpl.class);
    protected LogManager logManager = new LogManager();
    Comprobante comprobanteUltimo;
    Comprobante comprobanteCurrent;
    Respuesta respuesta;
    String ruc;
    String ambiente;
    String usuario;

    private Impuesto impuesto = null;

    @Autowired
    DocumentoAnuladoDAO documentoDAO;

    @Autowired
    SociedadDAO sociedadDAO;

    @Autowired
    ImpuestoDAO impuestoDAO;

    @Override
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public Parametro getSociedad() {
        return sociedad;
    }

    @Override
    public Comprobante getDocumento() {
        return comprobanteCurrent;
    }

    @Override
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getAmbiente() {
        return ambiente;
    }

    @Override
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    private String getRuc() {
        return this.comprobanteCurrent.getRuc();
    }

    // Inicio del flujo del comprobante con solucion al inconveniente por autorizar
    // Modificacion método iniciar: Ammiel
    //-----------------------------------------------------------------------------------
    public void iniciar() throws Exception {
        comprobanteCurrent = new Comprobante();
        this.respuesta = new Respuesta();
        sociedad = this.parametroDAO.getSociedad(this.ruc);
        comprobanteUltimo = comprobanteDAO.getByUltimo(identificador, tipoDoc, ruc);
        if (comprobanteUltimo != null && comprobanteUltimo.getEstado().equals(POR_AUTORIZAR)) {
            int de = comprobanteDAO.getNroRegistros(identificador, tipoDoc, POR_AUTORIZAR);
            if (de >= 3 && comprobanteUltimo.getPathXml().contains("\\porAutorizar")) {
                //     comprobanteUltimo.setTipoEmision(TipoEmisionConstants.EN_CONTINGENCIA);
                comprobanteUltimo.setRecepcionado(AplicacionConstants.FAIL);
                String prueba = comprobanteUltimo.getPathXml();
                String prueba1 = prueba.replace("\\porAutorizar", "");
                comprobanteUltimo.setPathXml(prueba1);
                comprobanteDAO.update(comprobanteUltimo);
            } else if (de >= 3 && comprobanteUltimo.getPathXml().contains("\\contingencia")) {
                comprobanteUltimo.setRecepcionado(AplicacionConstants.FAIL);
                comprobanteDAO.update(comprobanteUltimo);
            }
        }
        if (comprobanteUltimo == null) {
            throw new Exception("Este Documento no está apto para el Proceso.");
        }
        this.configuracionComprobante();
        try {
            this.setPathLog(this.getPARAMETER_LOG_PATH_TEXT() + this.getNombreDocumento() + ".log");
        } catch (ParametroReaderException ex) {
            this.setPathLog("c://log/" + this.getNombreDocumento() + ".log");
            log.info("[INICIO] Nose asigno un Log personalizado, se asigno un directorio por defecto.");
        }
        logManager.init(this.getPathLog());
    }

    //------------------------------------------------------------------------------------------------------
    //  Método para anular el comprobante
    //  By: Ammiel
    @Override
    public String anularComprobante() {
        log.info("[BD] Anulando comprobante.");
        sociedad = this.parametroDAO.getSociedad(this.ruc);

        comprobanteUltimo = comprobanteDAO.getByUltimo(identificador, tipoDoc, ruc);
        SapImpl sap = new SapImpl();

        Date date = new Date();
        String fecha = util.changeFormatDate(date, "yyyy-MM-dd");
        String hora = util.changeFormatDate(date, "HHmmss");

        Sociedad s = sociedadDAO.getSociedad(this.ruc);
        s.getConfSap();
        String user = util.getCadenaTextoEntre(s.getConfSap(), "<usuario>", "</usuario>");
        String pass = util.getCadenaTextoEntre(s.getConfSap(), "<contrasena>", "</contrasena>");

        String sri[] = comprobanteUltimo.getNroSri().split("-");
        String nroSri = sri[0] + "-" + sri[1] + "-" + sri[2].substring(1);
        Map<Object, String> map = sap.anularDocumentoSap(user, pass, fecha, comprobanteUltimo.getUsuario(), hora, nroSri, comprobanteUltimo.getDocReferencia(), this.ruc, comprobanteUltimo.getTipoDoc());

        if (map != null) {
            if (map.containsKey("valor") && map.containsKey("mensaje")) {
                if (map.get("valor").equals("0")) {
                    if (comprobanteUltimo != null) {
                        comprobanteUltimo.setAnulado(AplicacionConstants.SUCCESS);
                        comprobanteUltimo.setUltimo(AplicacionConstants.FAIL);
                        comprobanteUltimo.setEstado(AplicacionUtil.obtenerEstadoByEscenario(EscenarioConstant.AUTORIZADO_ANULADO));
                        comprobanteUltimo.setEscenario(EscenarioConstant.AUTORIZADO_ANULADO);
                        generacionPDFAnulado(s);

                        comprobanteDAO.update(comprobanteUltimo);
                    }
                    log.info("[DB] Anulación correcta");
                    return "Comprobante Anulado:" + identificador;
                } else {
                    System.err.println("RESPUESTA DE SAP: " + map.get("mensaje"));
                    return map.get("mensaje");
                }
            } else {
                System.err.println("Error al obtener respuestas");
                return "Error al obtener respuestas";
            }
        } else {
            System.err.println("Error de conexion de servicio web.");
            return "Error de conexion de servicio web.";
        }
    }

    private void generacionPDFAnulado(Sociedad s) {
        DocumentoAnulado doc = documentoDAO.getDocumento(this.ruc, comprobanteUltimo.getNroSri(), tipoDoc);

        comprobanteUltimo.setPathPdf(FilenameUtils.removeExtension(doc.getPathXML()) + ".pdf");

        XmlServices xmlServices = this.getXmlServices(doc);
        PdfServices pdfServices = this.getPdfServices(doc);

        if (xmlServices != null && pdfServices != null) {

            this.getImpuestoIvaByDocumento(doc, xmlServices);//{+ MML

            String[] sociedad = new String[5];
            sociedad[0] = ruc;
            sociedad[1] = s.getUrl();
            sociedad[2] = s.getTextoRide();
            sociedad[3] = "";
            sociedad[4] = "";

            String[] documentoParam = {""};
            try {
                pdfServices.generarPdfAnulado(xmlServices.getComprobanteDePathArchivo(doc.getPathXML()),
                        FilenameUtils.removeExtension(doc.getPathXML()) + ".pdf",
                        comprobanteUltimo.getNroAutorizacion(),
                        comprobanteUltimo.getFechaAutorizacion(),
                        sociedad,
                        documentoParam,
                        this.getIvaDinamico()
                );

            } catch (XmlException ex) {
                log.error("Error al generar PDF", ex);
            }
        }
    }

    private String getIvaDinamico() {
        if (impuesto != null) {
            return "" + impuesto.getPorcentaje();
        }
        return "";
    }

    private void getImpuestoIvaByDocumento(DocumentoAnulado documento, XmlServices xmlServices) {

        Object comprobante;

        String codigo = "";
        String codPorcentaje = "";

        try {
            comprobante = xmlServices.getComprobanteDePathArchivo(documento.getPathXML());
            switch (documento.getTipoDocumento()) {
                case "01":
                    if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                        Factura factura = (Factura) comprobante;
                        for (TotalImpuesto imp : factura.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    } else if (documento.getSubTipoDoc() == 2) {
                        FacturaExportacion exportacion = (FacturaExportacion) comprobante;

                        for (TotalImpuesto imp : exportacion.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    } else if (documento.getSubTipoDoc() == 3) {
                        FacturaReembolso reembolso = (FacturaReembolso) comprobante;

                        for (TotalImpuesto imp : reembolso.getInfoFactura().getTotalConImpuestos()) {
                            if (imp.getCodigo().equalsIgnoreCase("2")) {
                                codigo = imp.getCodigo();
                                codPorcentaje = imp.getCodigoPorcentaje();
                            }
                        }

                        getImpuesto(codigo, codPorcentaje);
                    }
                    break;
                case "04":
                    NotaCredito credito = (NotaCredito) comprobante;
                    for (TotalImpuesto imp : credito.getInfoNotaCredito().getTotalConImpuestos()) {
                        if (imp.getCodigo().equalsIgnoreCase("2")) {
                            codigo = imp.getCodigo();
                            codPorcentaje = imp.getCodigoPorcentaje();
                        }
                    }
                    getImpuesto(codigo, codPorcentaje);
                    break;
                case "05":
                    NotaDebito debito = (NotaDebito) comprobante;
                    for (ec.incloud.ce.bean.common.Impuesto imp : debito.getInfoNotaDebito().getImpuestos()) {
                        if (imp.getCodigo().equalsIgnoreCase("2")) {
                            codigo = imp.getCodigo();
                            codPorcentaje = imp.getCodigoPorcentaje();
                        }
                    }
                    getImpuesto(codigo, codPorcentaje);
                    break;
                default:
                    break;
            }
        } catch (XmlException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getImpuesto(String codigo, String codPorcentaje) {
        impuesto = impuestoDAO.getImpuesto(Integer.parseInt(codigo), Integer.parseInt(codPorcentaje));
    }

    private XmlServices getXmlServices(DocumentoAnulado documento) {
        XmlServices xmlServices = null;

        switch (documento.getTipoDocumento()) {
            case "01":
                if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                    xmlServices = XmlFactory.getFacturaXmlServices();
                } else if (documento.getSubTipoDoc() == 2) {
                    xmlServices = XmlFactory.getFacturaExportacionXmlServices();
                } else if (documento.getSubTipoDoc() == 3) {
                    xmlServices = XmlFactory.getFacturaReembolsoXmlServices();
                }
                break;
            case "04":
                xmlServices = XmlFactory.getNotaCreditoXmlServices();
                break;
            case "05":
                xmlServices = XmlFactory.getNotaDebitoXmlServices();
                break;
            case "06":
                xmlServices = XmlFactory.getGuiaRemisionXmlServices();
                break;
            case "07":
                xmlServices = XmlFactory.getRetencionServices();
                break;
            default:
                break;
        }
        return xmlServices;
    }

    private PdfServices getPdfServices(DocumentoAnulado documento) {
        PdfServices pdfServices = null;

        switch (documento.getTipoDocumento()) {
            case "01":
                if (documento.getSubTipoDoc() != 2 && documento.getSubTipoDoc() != 3) {
                    pdfServices = PdfFactory.createFacturaPdfService();
                } else if (documento.getSubTipoDoc() == 2) {
                    pdfServices = PdfFactory.creatFacturaExportacionPdfService();
                } else if (documento.getSubTipoDoc() == 3) {
                    pdfServices = PdfFactory.createPdfFacturaReembolsoServices();
                }
                break;
            case "04":
                pdfServices = PdfFactory.createPdfNotaCreditoServices();
                break;
            case "05":
                pdfServices = PdfFactory.createPdfNotaDebitoServices();
                break;
            case "06":
                pdfServices = PdfFactory.createPdfGuiaRemisionServices();
                break;
            case "07":
                pdfServices = PdfFactory.createPdfComprobanteRetencionServices();
                break;
            default:
                break;
        }
        return pdfServices;
    }

    //----------------------------------------------------------------------------------------------------
    private void validarComprobante() throws Exception {
        if (this.ruc == null && this.ruc.isEmpty()) {
            throw new Exception("ruc no Válido");
        }
        if (this.getDocumento().getContabilizado() != AplicacionConstants.SUCCESS) {
            throw new Exception("Este documento no esta Contabilizado.");
        }
    }

    private void configuracionComprobante() {
        comprobanteCurrent.setNroSri(comprobanteUltimo.getNroSri());
        comprobanteCurrent.setDocReferencia(comprobanteUltimo.getDocReferencia());
        comprobanteCurrent.setFechaEmision(comprobanteUltimo.getFechaEmision());
        comprobanteCurrent.setTipoDoc(comprobanteUltimo.getTipoDoc());
        comprobanteCurrent.setTipoDocReferencia(comprobanteUltimo.getTipoDocReferencia());
        comprobanteCurrent.setTipoEmision(comprobanteUltimo.getTipoEmision());
        comprobanteCurrent.setRuc(comprobanteUltimo.getRuc());
        comprobanteCurrent.setClaveAcceso(comprobanteUltimo.getClaveAcceso());
        comprobanteCurrent.setAnulado(comprobanteUltimo.getAnulado());
        comprobanteCurrent.setContabilizado(comprobanteUltimo.getContabilizado());
        comprobanteCurrent.setXml(comprobanteUltimo.getXml());
        comprobanteCurrent.setDocumentXML(comprobanteUltimo.getDocumentXML());
        comprobanteCurrent.setPathXml(comprobanteUltimo.getPathXml());
        comprobanteCurrent.setRecepcionado(comprobanteUltimo.getRecepcionado());
        comprobanteCurrent.setAutorizado(comprobanteUltimo.getAutorizado());
        comprobanteCurrent.setUsuario((usuario == null) ? comprobanteUltimo.getUsuario() : usuario);
        comprobanteCurrent.setTerminal(comprobanteUltimo.getTerminal());
        comprobanteCurrent.setEmailDestino(comprobanteUltimo.getEmailDestino());
        comprobanteCurrent.setUltimo(AplicacionConstants.SUCCESS);
        comprobanteCurrent.setNombreDocumento(comprobanteUltimo.getNombreDocumento());
    }

    /**
     * Asigna los Mensajes de Respuestas
     *
     * @param mensajes
     */
    public void setMensajes(List<Mensaje> mensajes) {
        log.info("Registrando mensajes de Respuesta");
        if (this.respuesta != null) {
            if (this.respuesta.getMensajes() != null) {
                if (this.respuesta.getMensajes().size() > 0) {
                    for (Mensaje mensaje : mensajes) {
                        this.respuesta.getMensajes().add(mensaje);
                    }
                } else {
                    this.respuesta.setMensajes(mensajes);
                }
            } else {
                this.respuesta.setMensajes(mensajes);
            }
        }
    }

    /**
     * Devuelve el Respuesta de Flujo.
     *
     * @return
     */
    @Override
    public Respuesta getRespuesta() {
        this.respuesta.setEscenario(this.getEscenario());
        this.respuesta.setEstado(this.getEstado());
        this.respuesta.setNroAutorizacion(this.getNroAutorizacion());
        this.respuesta.setClaveAcceso(this.getClaveAcceso());
        this.respuesta.setNroSri(this.getNroSRI());
        this.respuesta.setPdf(this.getPdf());
        this.respuesta.setEmail(this.getEmail());
        this.respuesta.setContabilizar(this.getContabilizar());
        if (this.respuesta.getMensajes() != null) {
            for (Mensaje mensaje : this.respuesta.getMensajes()) {
                log.info("[RESPUESTA] [" + mensaje.getTipo() + "] "
                        + mensaje.getIdentificador() + "-"
                        + mensaje.getDescripcion() + "");
                log.info("\t Adicional: " + mensaje.getAdicional());
            }
        }
        log.info("Escenario:" + this.respuesta.getEscenario() + "\t ESTADO: " + respuesta.getEstado());
//        
        return this.respuesta;
    }

    public void setEmail(int email) {
        this.comprobanteCurrent.setEmail(email);
    }

    public int getEmail() {
        return this.comprobanteCurrent.getEmail();
    }

    public int getPdf() {
        return this.comprobanteCurrent.getPdf();
    }

    private void setContabilizar(int contabilizar) {
        this.comprobanteCurrent.setContabilizado(contabilizar);
    }

    public int getContabilizar() {
        return this.comprobanteCurrent.getContabilizado();
    }

    public void setEscenario(int escenario) {
        this.comprobanteCurrent.setEscenario(escenario);
        this.comprobanteCurrent.setEstado(AplicacionUtil.obtenerEstadoByEscenario(escenario));
    }

    public int getEscenario() {
        return comprobanteCurrent.getEscenario();
    }

    public String getEstado() {
        return this.comprobanteCurrent.getEstado();
    }

    private boolean esNormal() {
        return (this.comprobanteUltimo.getTipoEmision() == TipoEmisionConstants.NORMAL);
    }

    private void setPathLog(String pathLog) {
        this.comprobanteCurrent.setLog(pathLog);
    }

    private String getPathLog() {
        return this.comprobanteCurrent.getLog();
    }

    private String getCodDocumento() {
        return this.comprobanteCurrent.getTipoDoc();
    }

    private String getTipoDocReferencia() {
        return this.comprobanteCurrent.getTipoDocReferencia();
    }

    private String getDocReferencia() {
        return this.comprobanteCurrent.getDocReferencia();
    }

    private String getNroSRI() {
        return this.comprobanteCurrent.getNroSri();
    }

    private void setRecepcion(int recepcion) {
        this.comprobanteCurrent.setRecepcionado(recepcion);
    }

    private void setAutorizacion(int autorizacion) {
        this.comprobanteCurrent.setAutorizado(autorizacion);
    }

    private void setXML(String xml) {
        this.comprobanteCurrent.setXml(xml);
    }

    private String getXML() {
        return this.comprobanteCurrent.getXml();
    }

    private void setPathXML(String pathXML) {
        this.comprobanteCurrent.setPathXml(pathXML);
    }

    private String getPathXML() {
        return this.comprobanteCurrent.getPathXml();
    }

    private String getClaveAcceso() {
        return this.comprobanteCurrent.getClaveAcceso();
    }

    private void setNroAutorizacion(String nroAutorizacion) {
        this.comprobanteCurrent.setNroAutorizacion(nroAutorizacion);
    }

    private String getNroAutorizacion() {
        return this.comprobanteCurrent.getNroAutorizacion();
    }

    private void setFechaAutorizacion(String fechaAutorizacion) {
        this.comprobanteCurrent.setFechaAutorizacion(fechaAutorizacion);
    }

    private boolean esComprobanteRecepcionado() {
        return (comprobanteUltimo.getRecepcionado() == 1);
    }

    private boolean esComprobanteAutorizado() {
        return (comprobanteUltimo.getAutorizado() == 1);
    }

    protected void enviarEmailDocumentoAutorizado() throws MailException, Exception {
        EmailUtil email = new EmailUtil();
        email.setAmbiente(ambiente);
        email.setComprobante(comprobanteCurrent);
        email.setParametroSociedad(sociedad);
        email.setParametroDAO(parametroDAO);
        email.setMensaje(this.getMensaje());
        email.envioEmailAutorizado(null);
    }

    private String getMensaje() throws Exception {
        String str = "";
        try {
            if (this.respuesta != null) {
                if (this.respuesta.getMensajes() != null) {
                    for (Mensaje mensaje : this.respuesta.getMensajes()) {
                        str += "[" + mensaje.getTipo() + "][" + mensaje.getIdentificador() + "]";
                        if (mensaje.getDescripcion() != null) {
                            str += " " + mensaje.getDescripcion() + "\n";
                        }
                        if (mensaje.getAdicional() != null) {
                            str += "\t-" + mensaje.getAdicional() + "\n\n";
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Ocurrió un error al extraer mensaje.");
        }

        return str;
    }

    protected void enviarEmailNotificacion() throws MailException, ParametroReaderException, Exception {
        EmailUtil email = new EmailUtil();
        email.setAmbiente(ambiente);
        email.setComprobante(comprobanteCurrent);
        email.setParametroSociedad(sociedad);
        email.setParametroDAO(parametroDAO);
        email.setMensaje(this.getMensaje());
        email.envioEmailNotificacion();
    }

    protected void generarPDF() throws ReporteException, Exception {
        ReporteUtil reporteUtil = new ReporteUtil();
        reporteUtil.setSociedad(sociedad);
        reporteUtil.setParametroDAO(parametroDAO);
        reporteUtil.setNombreDocumento(this.getNombreDocumento());
        reporteUtil.setDocumento(comprobanteCurrent);
        reporteUtil.generarPDF();
    }

    /**
     * Se procede al envio del comprobante al SRI.
     *
     * @throws RecepcionSRIException Excepción por XML rechazado en recepción.
     * Este tipo de excepción incluye una lista de mensajes de errores que
     * indican el motivo de su rechazo.
     * @throws SinRespuestaSRIRecepcionException Se produce esta excepción
     * cuando no hay comunicación con el SRI. El estado del comprobante actual
     * es CONTINGENCIA, lo cual indica que NO se emitirá un comprobante en
     * contingencia por esta excepción.
     */
    private void enviarSRI() throws RecepcionSRIException,
            SinRespuestaSRIRecepcionException,
            Exception {
        EnviarXMLaSRI enviar = new EnviarXMLaSRI();
        try {
            log.info("Enviando Documento SRI");
            String pathXML = this.getPathXML();
            String wsdl = this.getPARAMETER_WSDL_RECEPCION();
            int intentos = this.getPARAMETER_WSDL_RECEPCION_NRO_INTENTOS();
            int tiempoEspera = this.getPARAMETER_WSDL_RECEPCION_TIEMPO_ESPERA();
            enviar.setPathXML(pathXML);
            enviar.setWSDL(wsdl);
            enviar.setIntentos(intentos);
            enviar.setTiempoEspera(tiempoEspera);
            enviar.enviarXML();
            this.setRecepcion(AplicacionConstants.SUCCESS);
        } catch (RecepcionSRIException ex) {
            log.info("Documento Rechazado - nro Autorización");
            for (Mensaje mensaje : ex.getListMensajes()) {
                if (mensaje.getIdentificador().equals("43")) {
                    this.setRecepcion(AplicacionConstants.SUCCESS);
                    return;
                }
            }
            String pathXml = this.getPARAMETER_PATH_RECHAZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
//            setXML(enviar.getRespuestaXML())       
            this.writeFile(this.getPathXML());
            throw ex;
        }
    }

    private void writeFile(String pathXml) throws Exception {
        FileUtil fileUtil = new FileUtil();
        fileUtil.writeFileText(pathXml, this.getXML());
    }

    /**
     * Se procede a la autorización del comprobante al SRI mediante la clave de
     * acceso.
     *
     * @throws AutorizacionSRIException Se produce esta excepcion cuando el
     * comprobante fue rechazado por el SRI. Este tipo de excepción incluye una
     * lista de mensajes de errores que indican el motivo de su rechazo.
     * @throws SinRespuestaSRIAutorizacionException Excepción al no tener
     * conexión con el SRI en la autorización del comprobante.
     */
    protected void autorizarSRI() throws AutorizacionSRIException,
            SinRespuestaSRIAutorizacionException,
            Exception {
        log.info("Autorizando Documento");
        AutorizarXMLaSRI autorizar = new AutorizarXMLaSRI();
        String claveAcceso = this.getClaveAcceso();
        String wsdl = this.getPARAMETER_WSDL_AUTORIZACION();
        int intentos = this.getPARAMETER_WSDL_AUTORIZACION_NRO_INTENTOS();
        int tiempoEspera = this.getPARAMETER_WSDL_AUTORIZACION_TIEMPO_ESPERA();
        try {
            autorizar.setClaveAcceso(claveAcceso);
            autorizar.setWSDL(wsdl);
            autorizar.setIntentos(intentos);
            autorizar.setTiempoEspera(tiempoEspera);
            autorizar.autorizarXML();
            this.setAutorizacion(AplicacionConstants.SUCCESS);
            this.setRecepcion(AplicacionConstants.SUCCESS);
            this.setNroAutorizacion(autorizar.getNroAutorizacion());
            this.setFechaAutorizacion(autorizar.getFechaAutorizacion());
            log.info("Documento Autorizado - nro Autorización : " + this.getNroAutorizacion());
            String pathXml = this.getPARAMETER_PATH_AUTORIZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
            setXML(autorizar.getRespuestaXML());
            this.writeFile(this.getPathXML());
        } catch (AutorizacionSRIException e) {
            this.setNroAutorizacion(autorizar.getNroAutorizacion());
            this.setFechaAutorizacion(autorizar.getFechaAutorizacion());
            log.info("Documento Rechazado - nro Autorización");
            String pathXml = this.getPARAMETER_PATH_RECHAZADO() + this.getNombreDocumento() + ".xml";
            this.setPathXML(pathXml);
            setXML(autorizar.getRespuestaXML());
            this.writeFile(this.getPathXML());
            throw e;
        }
    }

    public void emitir() {
        try {
            this.iniciar();
            this.validarComprobante();
            CertificadosSSL ssl = new CertificadosSSL();
            ssl.instalarCertificados();
            if (esNormal()) {
                AutorizarNormal();
            } else {
                autorizarContingencia();
            }
        } catch (ParametroReaderException ex) {
//            this.setEscenario(NUEVO_INCONSISTENTE);
            log.error("[PARAMETROS] Error al cargar los Parámetros.", ex);
            this.setMensajes(ex.getMensajes());
        } catch (FirmaException ex) {
            this.setEscenario(NUEVO_INCONSISTENTE);
            this.setMensajes(ex.getMensajes());
            log.error("[FIRMA ELECTRONICA] Error al firmar XML ", ex);
        } catch (MailException ex) {
//            this.setEscenario((this.getEscenario() != 0 ? this.getEscenario() : NUEVO_INCONSISTENTE));
            log.error("[ERROR] MailException: ", ex);

            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", ex.getMessage()));
            this.setMensajes(mensajes);
            ex.printStackTrace();
        } catch (Exception ex) {
            this.setEscenario((this.getEscenario() == 0) ? NUEVO_INCONSISTENTE : this.getEscenario());
            log.error("[ERROR] ", ex);

            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "Ocurrió un Error: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        }
        try {
            this.registrarComprobante();
//          this.actualizarSAP();
        } catch (UpdateSapException ex) {
            log.info("[UPDATE-SAP] No se actualizó en documento en SAP: " + ex.getMessage());
            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "No se actualizó en documento en SAP: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        } catch (Exception ex) {
            log.info("[REGISTRO] Ocurrió un error en el proceso de registro:", ex);
            List<Mensaje> mensajes = new ArrayList<Mensaje>();
            mensajes.add(new Mensaje("ERROR", "Ocurrió un Error: " + ex.getMessage() + ", verifique el LOG para más información."));
            this.setMensajes(mensajes);
        }
    }

    private void actualizarSAP() throws UpdateSapException {
        int esc = this.comprobanteCurrent.getEscenario();
        if (AplicacionUtil.evaluarEscenario(esc, AUTORIZADO)
                || AplicacionUtil.evaluarEscenario(esc, RECHAZADO)) {
            log.info("[LEGACY] preparando para enviar a Legacy. ");
            UpdateSAP updateSAP = new UpdateSAP();
            int codEstado = SapConstant.COD_ESTADO_FAIL;
            if (AplicacionUtil.evaluarEscenario(this.getEscenario(), EscenarioConstant.AUTORIZADO)) {
                codEstado = SapConstant.COD_ESTADO_AUTORIZADO;
            } else if (AplicacionUtil.evaluarEscenario(this.getEscenario(), EscenarioConstant.RECHAZADO)) {
                codEstado = SapConstant.COD_ESTADO_RECHAZADO;
            }
            if (codEstado != SapConstant.COD_ESTADO_FAIL) {
                updateSAP.setSociedad(sociedad);
                updateSAP.setParametroDAO(parametroDAO);
                updateSAP.actualizar(this.getNroSRI(), this.getDocReferencia(),
                        this.getClaveAcceso(), this.getCodDocumento(),
                        this.getNroAutorizacion(), this.getRuc(),
                        this.getDocumento().getUsuario(), codEstado);
            }
            log.info("[LEGACY] Se envió correctamente. ");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    protected void registrarComprobante() throws ParametroReaderException, Exception {
        log.info("[BD] Registrando Documento.");
        if (comprobanteCurrent != null) {
            comprobanteCurrent.setFechaRegistro(new Date());
            comprobanteCurrent.setMensaje(this.getMensaje());
            if (AplicacionUtil.evaluarEscenario(comprobanteCurrent.getEscenario(), EscenarioConstant.INCONSISTENTE)
                    || AplicacionUtil.evaluarEscenario(comprobanteCurrent.getEscenario(), EscenarioConstant.POR_ENVIAR)) {
                comprobanteCurrent.setUltimo(AplicacionConstants.FAIL);
                comprobanteDAO.save(comprobanteCurrent);
            } else {
                comprobanteCurrent.setUltimo(AplicacionConstants.SUCCESS);
                comprobanteDAO.save(comprobanteCurrent);
                comprobanteUltimo.setUltimo(AplicacionConstants.FAIL);
                comprobanteDAO.update(comprobanteUltimo);
            }
            log.info("[DB] Registro correcto");
        }

    }

    public void AutorizarNormal() throws ParametroReaderException, Exception {
        if (esComprobanteRecepcionado()) {
            if (esComprobanteAutorizado()) {
                //Mensaje de Error: el comprobante ya fue autorizado
                this.setEscenario(AUTORIZAR_AUTORIZAR);
                log.error("Clave de acceso registrada, el comprobante ya fue autorizado");
                List<Mensaje> mensajes = new ArrayList<>();
                mensajes.add(new Mensaje("ERROR", "Clave de acceso registrada, el comprobante ya fue autorizado"));
                this.setMensajes(mensajes);
                this.traspasarComprobante();
                this.generarPDF();
                this.enviarEmailDocumentoAutorizado();
                this.enviarEmailNotificacion();
            } else {
                //Comprobante POR AUTORIZAR;
                FlujoPorAutorizar();
            }
        } else {
            FlujoAutorizarContingencia();
            //No aplica: El comprobante Normal  para su Recepcion y Autorizacion 
        }
    }

    public void autorizarContingencia() throws ParametroReaderException, Exception {
        //Para autorizar Documento en Contingencia
        if (esComprobanteRecepcionado()) {
            if (esComprobanteAutorizado()) {
                //Mensaje de Error: el comprobante ya fue autorizado
                this.setEscenario(AUTORIZAR_AUTORIZAR);
                log.error("Clave de acceso registrada, el comprobante ya fue autorizado");
                List<Mensaje> mensajes = new ArrayList<>();
                mensajes.add(new Mensaje("ERROR", "Clave de acceso registrada, el comprobante ya fue autorizado"));
                this.setMensajes(mensajes);
                this.traspasarComprobante();
                this.generarPDF();
                this.enviarEmailDocumentoAutorizado();
                this.enviarEmailNotificacion();
            } else {
                //Comprobante POR AUTORIZAR;
                FlujoPorAutorizar();
            }
        } else {
            FlujoAutorizarContingencia();
            //Comprobante PARA Recepcionar y Autorizar
        }
    }

    public void FlujoPorAutorizar() throws ParametroReaderException, Exception {
        try {
            log.info("[FLUJO POR AUTORIZAR] Inicio del proceso");
//            this.porAutorizar();
            this.autorizarSRI();

            this.setEscenario(POR_AUTORIZAR_AUTORIZADO);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            log.info("[FLUJO POR AUTORIZAR] Documento Autorizado");
            this.generarPDF();
            this.enviarEmailDocumentoAutorizado();
            this.enviarEmailNotificacion();
        } catch (ClaveAccesoException ex) {
            this.setEscenario(POR_AUTORIZAR_INCONSISTENTE);
            log.info("[FLUJO POR AUTORIZAR] Error al comparar la Clave de Acceso.");
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (ReporteException ex) {
            log.info("[FLUJO POR AUTORIZAR] Error al Generar el PDF");
            this.setMensajes(ex.getMensajes());
        } catch (MailException ex) {
            log.info("[FLUJO POR AUTORIZAR] Error al Enviar el EMail");
            this.setMensajes(ex.getMensajes());
        } catch (AutorizacionSRIException ex) {
            log.info("[FLUJO POR AUTORIZAR] Rechazado por el SRI, al Autorizar el documento");
            this.setEscenario(POR_AUTORIZAR_RECHAZADO);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIAutorizacionException ex) {
            log.info("[FLUJO POR AUTORIZAR] Sin respuesta en autorizacion");
            this.setEscenario(POR_AUTORIZAR_POR_AUTORIZAR);
            this.setNroAutorizacion(AplicacionConstants.NRO_AUTORIZACION_POR_AUTORIZAR);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        }
    }

    public void FlujoAutorizarContingencia() throws ParametroReaderException, Exception {
        try {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] iniciando Proceso de Autorización.");
//            this.autorizarContingencia();
            this.enviarSRI();
            this.autorizarSRI();
            this.setEscenario(CONTINGENCIA_AUTORIZADO);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Documento Autorizado.");
            //Crear PDF, Enviar Email.
            this.generarPDF();
            this.enviarEmailDocumentoAutorizado();
            this.enviarEmailNotificacion();
        } catch (ReporteException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Error al Generar el PDF");
            this.setMensajes(ex.getMensajes());
        } catch (MailException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Error al Enviar el EMail");
            this.setMensajes(ex.getMensajes());
        } catch (ValidacionXSDException ex) {
            log.error("[FLUJO AUTORIZAR CONTINGENCIA] XML no cumple con la estructura ", ex);
            this.setEscenario(CONTINGENCIA_INCONSISTENTE);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (RecepcionSRIException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Rechazado por el SRI, al Recepcionar el documento.");
            this.setEscenario(CONTINGENCIA_RECHAZADO);
            this.setMensajes(ex.getListMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIRecepcionException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Sin respuesta en recepcion");
            this.setEscenario(CONTINGENCIA_POR_ENVIAR);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (AutorizacionSRIException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Rechazado por el SRI,al Autorizar el documento.");
            this.setEscenario(CONTINGENCIA_RECHAZADO);
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        } catch (SinRespuestaSRIAutorizacionException ex) {
            log.info("[FLUJO AUTORIZAR CONTINGENCIA] Sin respuesta en autorizacion");
            this.setEscenario(CONTINGENCIA_POR_AUTORIZAR);
            this.setNroAutorizacion(AplicacionConstants.NRO_AUTORIZACION_POR_AUTORIZAR);
            this.setContabilizar(this.getPARAMETER_CONTABILIZAR_FOR_STATE());
            this.setMensajes(ex.getMensajes());
            this.enviarEmailNotificacion();
        }

    }

    private void traspasarComprobante() {
        log.info("Traspasando valores del documento." + this.comprobanteUltimo.getNroSri());
        this.comprobanteCurrent.setClaveAcceso(this.comprobanteUltimo.getClaveAcceso());
        this.comprobanteCurrent.setNroAutorizacion(this.comprobanteUltimo.getNroAutorizacion());
        this.comprobanteCurrent.setFechaAutorizacion(this.comprobanteUltimo.getFechaAutorizacion());
//        this.documentObject.setContabilizado(comprobante.getContabilizado());
        this.comprobanteCurrent.setXml(this.comprobanteUltimo.getXml());
        this.comprobanteCurrent.setDocumentXML(this.comprobanteUltimo.getDocumentXML());
        this.comprobanteCurrent.setPathXml(this.comprobanteUltimo.getPathXml());
        this.comprobanteCurrent.setPathPdf(this.comprobanteUltimo.getPathPdf());
        this.comprobanteCurrent.setPdf(this.comprobanteUltimo.getPdf());
        this.comprobanteCurrent.setEmail(this.comprobanteUltimo.getEmail());
        this.comprobanteCurrent.setRecepcionado(this.comprobanteUltimo.getRecepcionado());
        this.comprobanteCurrent.setAutorizado(this.comprobanteUltimo.getAutorizado());
    }

    /**
     * Genera el nombre del documento Ingresado, el nombre esta compuesto por: -
     * Nombre del tipo de documento - Tipo de documento de referencia -
     * Documento de referencia - Número de SRI - Fecha y hora
     *
     * @return
     */
    protected String getNombreDocumento() {
        String nombre = "";
        if (comprobanteCurrent.getNombreDocumento() == null
                || comprobanteCurrent.getNombreDocumento().isEmpty()) {
            nombre = this.getNombreTipoDocumento() + "-"
                    + this.getTipoDocReferencia() + "-" + this.getDocReferencia() + "-" + this.getNroSRI();

        } else {
            nombre = comprobanteCurrent.getNombreDocumento();
        }
        return nombre;
    }

    public String getNombreTipoDocumento() {
        return AplicacionUtil.obtenerComprobanteByCodigo(this.getCodDocumento());
    }

    public String getPARAMETER_LOG_PATH_TEXT() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.LOG_PATH_TEXT);
    }

    public String getPARAMETER_WSDL_RECEPCION() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.RECEPCION_WSDL);
    }

    public int getPARAMETER_WSDL_RECEPCION_NRO_INTENTOS() throws ParametroReaderException {
        int intentos = Integer.parseInt(this.getParametroValue(ParametroConstants.RECEPCION_INTENTOS));
        return intentos;
    }

    public int getPARAMETER_WSDL_RECEPCION_TIEMPO_ESPERA() throws ParametroReaderException {
        int tiempo = Integer.parseInt(this.getParametroValue(ParametroConstants.RECEPCION_ESPERA));
        return tiempo;
    }

    public String getPARAMETER_WSDL_AUTORIZACION() throws ParametroReaderException {
        return this.getParametroValue(ParametroConstants.AUTORIZACION_WSDL);
    }

    public int getPARAMETER_WSDL_AUTORIZACION_NRO_INTENTOS() throws ParametroReaderException {
        int intentos = Integer.parseInt(this.getParametroValue(ParametroConstants.AUTORIZACION_INTENTOS));
        return intentos;
    }

    public int getPARAMETER_WSDL_AUTORIZACION_TIEMPO_ESPERA() throws ParametroReaderException {
        int tiempo = Integer.parseInt(this.getParametroValue(ParametroConstants.AUTORIZACION_ESPERA));
        return tiempo;
    }

    public String getPARAMETER_PATH_RECHAZADO() throws ParametroReaderException {
        String pathRechazado = null;
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                pathRechazado = this.getParametroValue(ParametroConstants.FACTURA_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                pathRechazado = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                pathRechazado = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                pathRechazado = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_RECHAZADO);
                break;
            case ComprobanteConstants.COD_RETENCION:
                pathRechazado = this.getParametroValue(ParametroConstants.RETENCION_PATH_RECHAZADO);
                break;
        }
        if (pathRechazado == null) {
            throw new ParametroReaderException("No se ha encontrado un Directorio.");
        }
        return pathRechazado;
    }

    public String getPARAMETER_PATH_AUTORIZADO() throws ParametroReaderException {
        String pathAutorizado = null;
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                pathAutorizado = this.getParametroValue(ParametroConstants.FACTURA_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                pathAutorizado = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                pathAutorizado = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                pathAutorizado = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_AUTORIZACION);
                break;
            case ComprobanteConstants.COD_RETENCION:
                pathAutorizado = this.getParametroValue(ParametroConstants.RETENCION_PATH_AUTORIZACION);
                break;
        }
        if (pathAutorizado == null) {
            throw new ParametroReaderException("No se ha encontrado un Directorio.");
        }
        return pathAutorizado;
    }

    private int getPARAMETER_CONTABILIZAR_FOR_STATE() throws ParametroReaderException {
        int contabilizar = 0;
        if (this.getEstado().equalsIgnoreCase(AUTORIZADO) || this.getEstado().equalsIgnoreCase(ESTA_AUTORIZADO)) {
            contabilizar = AplicacionConstants.SUCCESS;
        } else if (this.getEstado().equalsIgnoreCase(POR_AUTORIZAR)) {
            contabilizar = Integer.parseInt(this.getParametroValue(ParametroConstants.CONTABILIZAR_POR_AUTORIZAR));
        } else if (this.getEstado().equalsIgnoreCase(CONTINGENCIA)) {
            contabilizar = Integer.parseInt(this.getParametroValue(ParametroConstants.CONTABILIZAR_CONTINGENCIA));
        }
        return contabilizar;
    }

    private String getParametroValue(String Campo) throws ParametroReaderException {
        return this.parametroDAO.getParametroByCampo(this.sociedad.getParametroHigh(), Campo).getParametroLow();
    }
}
>>>>>>> 5e6246a6cd358c11f1c4bfd199971719538d7d1e
