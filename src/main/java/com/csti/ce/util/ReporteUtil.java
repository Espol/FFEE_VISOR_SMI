/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.util;

import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.constant.EscenarioConstant;
import static com.csti.ce.constant.EscenarioConstant.AUTORIZADO;
import static com.csti.ce.constant.EscenarioConstant.CONTINGENCIA;
import com.csti.ce.constant.ParametroConstants;
import com.csti.ce.exception.ParametroReaderException;
import com.csti.ce.exception.ReporteException;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Comprobante;
import com.csti.ce.monitor.domain.Parametro;
import org.apache.log4j.Logger;

/**
 *
 * @author Usuario
 */
public class ReporteUtil {

//    @Autowired
    ParametroDAO parametroDAO;
    protected final Logger log = Logger.getLogger(ReporteUtil.class);
//    private EmitirReporteService emitirReporteService;
    private String nombreDocumento;
    private Comprobante comprobante;
    private Parametro sociedad;

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public void setDocumento(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public void setSociedad(Parametro sociedad) {
        this.sociedad = sociedad;
    }

    public String getNroAutorizacion() {
        return comprobante.getNroAutorizacion();
    }

    private String getDocumento() throws Exception {
        if (comprobante.getDocumentXML() == null) {
            throw new Exception("No se encontro el documento, para la generación de reporte");
        }
        return comprobante.getDocumentXML();
    }

    public String getFechaAutorizacion() {
        return comprobante.getFechaAutorizacion();
    }

    public String getNombreTipoDocumento() {
        return AplicacionUtil.obtenerComprobanteByCodigo(this.getCodDocumento());
    }

    private String getCodDocumento() {
        return comprobante.getTipoDoc();
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    private String getNombreDocumento() {
        if (this.nombreDocumento == null || this.nombreDocumento.isEmpty()) {
            nombreDocumento  = this.getNombreTipoDocumento() + "-"
                    + this.comprobante.getTipoDocReferencia() + "-"
                    + this.comprobante.getDocReferencia() + "-"
                    + this.comprobante.getNroSri() + "-"
                    + AplicacionUtil.obtenerDateTimeActual();
            this.comprobante.setNombreDocumento(nombreDocumento);
        }
        return nombreDocumento;
    }

    private void setPdf(int pdf) {
        comprobante.setPdf(pdf);
    }

    private void setPathPDF(String pathPDF) {
        comprobante.setPathPdf(pathPDF);
    }

    public String getPathPDF() {
        return comprobante.getPathPdf();
    }

    private String getEstado() {
        return comprobante.getEstado();
    }

    public void generarPDF() throws ParametroReaderException, ReporteException, Exception {
//        log.info("[REPORTE] Verificando Configuracion para la generacion del PDF");
//        if (this.getPARAMETER_PDF_FOR_STATE() == AplicacionConstants.SUCCESS) {
//            log.info("[REPORTE] Preparando reporte.");
//            emitirReporteService = new EmitirReporteService();
//            emitirReporteService.setFECHA_AUT(this.getFechaAutorizacion());
//            emitirReporteService.setNUM_AUT(this.getNroAutorizacion());
//            emitirReporteService.setPATH_SUBREPORT(this.getPARAMETER_PATH_REPORT());
//            emitirReporteService.setPATH_JASPER(this.getPARAMETER_PATH_REPORT_JASPER());
//            emitirReporteService.setPATH_JRXML(this.getPARAMETER_PATH_REPORT_JRXML());
//            emitirReporteService.setPATH_LOGO(this.getPARAMETER_PATH_LOGO());
//            emitirReporteService.setPATH_MARCA_AGUA(this.getPARAMETER_PATH_BACKGROUND_AGUA());
//            String pathPDF = this.getPARAMETER_PATH_REPORT_PDF();
//            emitirReporteService.setPATH_PDF(pathPDF);
////            emitirReporteService.emitirFactura(this.getComprobante());
//            Serializacion serializacion = new Serializacion();
//            switch (this.getCodDocumento()) {
//                case ComprobanteConstants.COD_FACTURA:
//                    Factura factura = serializacion.convertFactura(this.getDocumento());
//                    emitirReporteService.emitirFactura(factura);
//                    break;
//                case ComprobanteConstants.COD_NOTA_CREDITO:
//                    NotaCredito notaCredito = serializacion.convertNotaCredito(this.getDocumento());
//                    emitirReporteService.emitirNotaCredito(notaCredito);
//                    break;
//                case ComprobanteConstants.COD_NOTA_DEBITO:
//                    NotaDebito notaDebito = serializacion.convertNotaDebito(this.getDocumento());
//                    emitirReporteService.emitirNotaDebito(notaDebito);
//                    break;
//                case ComprobanteConstants.COD_GUIA_REMISION:
//                    GuiaRemision guiaRemision = serializacion.convertGuiaRemision(this.getDocumento());
//                    emitirReporteService.emitirGuiaRemision(guiaRemision);
//                    break;
//                case ComprobanteConstants.COD_RETENCION:
//                    ComprobanteRetencion comprobanteRetencion = serializacion.convertComprobanteRetencion(this.getDocumento());
//                    emitirReporteService.emitirCompRetencion(comprobanteRetencion);
//                    break;
//            }
//            this.setPdf(AplicacionConstants.SUCCESS);
//            this.setPathPDF(pathPDF);
//            log.info("[REPORTE] El reporte ah sido generado: " + pathPDF);
//        } else {
//            log.info("[REPORTE] No se generó el reporte por permisos de Configuración.");
//        }
    }

    protected int getPARAMETER_PDF_FOR_STATE() throws ParametroReaderException {
        int pdf = 0;
        if (this.getEstado().equalsIgnoreCase(AUTORIZADO) || this.getEstado().equalsIgnoreCase(EscenarioConstant.ESTA_AUTORIZADO)) {
            pdf = Integer.parseInt(this.getParametroValue(ParametroConstants.PDF_AUTORIZADO_MONITOR));
        } else if (this.getEstado().equalsIgnoreCase(CONTINGENCIA)) {
            pdf = Integer.parseInt(this.getParametroValue(ParametroConstants.PDF_CONTINGENCIA_MONITOR));
        }
        return pdf;
    }

    protected String getPARAMETER_PATH_REPORT_JASPER() throws ParametroReaderException {
        return this.getPARAMETER_PATH_REPORT() + this.getNombreTipoDocumento() + ".jasper";
    }

    protected String getPARAMETER_PATH_REPORT() throws ParametroReaderException {
        String pathReport = "";
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                pathReport = this.getParametroValue(ParametroConstants.FACTURA_PATH_REPORT);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                pathReport = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_REPORTE);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                pathReport = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_REPORT);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                pathReport = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_REPORT);
                break;
            case ComprobanteConstants.COD_RETENCION:
                pathReport = this.getParametroValue(ParametroConstants.RETENCION_PATH_REPORTE);
                break;
        }
        return pathReport;
    }

    protected String getPARAMETER_PATH_REPORT_PDF() throws ParametroReaderException {
        String reporte_pdf = "";
        switch (this.getCodDocumento()) {
            case ComprobanteConstants.COD_FACTURA:
                reporte_pdf = this.getParametroValue(ParametroConstants.FACTURA_PATH_PDF);
                break;
            case ComprobanteConstants.COD_NOTA_CREDITO:
                reporte_pdf = this.getParametroValue(ParametroConstants.NOTACREDITO_PATH_PDF);
                break;
            case ComprobanteConstants.COD_NOTA_DEBITO:
                reporte_pdf = this.getParametroValue(ParametroConstants.NOTADEBITO_PATH_PDF);
                break;
            case ComprobanteConstants.COD_GUIA_REMISION:
                reporte_pdf = this.getParametroValue(ParametroConstants.GUIAREMISION_PATH_PDF);
                break;
            case ComprobanteConstants.COD_RETENCION:
                reporte_pdf = this.getParametroValue(ParametroConstants.RETENCION_PATH_PDF);
                break;
        }
        return reporte_pdf + this.getNombreDocumento() + ".pdf";
    }

    protected String getPARAMETER_PATH_REPORT_JRXML() throws ParametroReaderException {
        return this.getPARAMETER_PATH_REPORT() + this.getNombreTipoDocumento() + ".jrxml";
    }

    protected String getPARAMETER_PATH_LOGO() throws ParametroReaderException {
        return getParametroValue(ParametroConstants.PATH_LOGO);
    }

    protected String getPARAMETER_PATH_BACKGROUND_AGUA() throws ParametroReaderException {
        return getParametroValue(ParametroConstants.PATH_BACKGROUND_AGUA);
    }

    private String getParametroValue(String Campo) throws ParametroReaderException {
        return this.parametroDAO.getParametroByCampo(this.sociedad.getParametroHigh(), Campo).getParametroLow();
    }
}
