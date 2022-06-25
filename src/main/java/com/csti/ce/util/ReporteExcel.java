/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.util;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.constant.ComprobanteConstants;
import com.csti.ce.monitor.view.ComprobanteView;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author MARCELO
 */
public class ReporteExcel {
    
    private List<ComprobanteView> comprobantes;
    private String fileName;
    private int index;
    private String esquema;
    
    private HSSFWorkbook libro;
    private HSSFSheet hoja;

    public List<ComprobanteView> getComprobantes() {
        return comprobantes;
    }
    
    private void clearMemoria(){
        this.libro = null;
        this.hoja = null;
        this.comprobantes = null;
    }

    public void setComprobantes(List<ComprobanteView> comprobantes) {
        this.comprobantes = null;
        this.comprobantes = comprobantes;
    }
    
    public void generarCabecera(){
        this.clearMemoria();
        this.libro = new HSSFWorkbook();
        this.hoja = libro.createSheet("Reporte-" + this.esquema);
        HSSFRow titulo = hoja.createRow(1);
        HSSFCell celdaTitulo;
        CellStyle estiloTitulo;
        estiloTitulo = getEstiloTitulo(libro);
        //crear Titlulo
        celdaTitulo = titulo.createCell(1);
        celdaTitulo.setCellValue(Excel.TITULO);
        celdaTitulo.setCellStyle(estiloTitulo);
        //Crear Encabezado
        HSSFRow head = hoja.createRow(3);
        for (int i = 0; i < Excel.ENCABEZADO.length; i++) {
            HSSFCell celdaHead = head.createCell(i + 1);
            celdaHead.setCellValue(Excel.ENCABEZADO[i]);
            celdaHead.setCellStyle(estiloTitulo);
        }
    }

    public void generaExcel() {
        //Crear Cuerpo
        int i = index + 4;
        for(ComprobanteView comprobante : comprobantes) {
            HSSFRow body = this.hoja.createRow(i);
            
            //ESQUEMA
            HSSFCell celdaBody = body.createCell(1);
            celdaBody.setCellValue(this.esquema);
            //sociedad
            celdaBody = body.createCell(2);
            celdaBody.setCellValue(this.getSociedad(comprobante.getRuc()));
            //ruc
            celdaBody = body.createCell(3);
            celdaBody.setCellValue(comprobante.getRuc());
            //nro sri
            celdaBody = body.createCell(4);
            celdaBody.setCellValue(comprobante.getNroSri());
            //DOC REFERENCIA
            celdaBody = body.createCell(5);
            celdaBody.setCellValue(comprobante.getDocReferencia());
            //DOC SUSTENTO
            celdaBody = body.createCell(6);
            celdaBody.setCellValue(comprobante.getDocSustendo());
            //Fecha emision
            celdaBody = body.createCell(7);
            String[] emision = comprobante.getFechaEmision().toString().split(" ");
            celdaBody.setCellValue(emision[0]);
            //Hora Emision
            celdaBody = body.createCell(8);
            celdaBody.setCellValue(emision[1]);
            // tipo Doc
            celdaBody = body.createCell(9);
            celdaBody.setCellValue(this.getNombreDocumento(comprobante.getTipoDoc()));
            // tipo Doc referencia
            celdaBody = body.createCell(10);
            celdaBody.setCellValue(comprobante.getTipoDocReferencia());
            // clave de Acceso
            celdaBody = body.createCell(11);
            celdaBody.setCellValue(comprobante.getClaveAcceso());
            // nro autorizacion
            celdaBody = body.createCell(12);
            celdaBody.setCellValue(comprobante.getNroAutorizacion());
            // Fecha Autorizacion
            celdaBody = body.createCell(13);
            celdaBody.setCellValue(comprobante.getFechaAutorizacion());
            // estado
            celdaBody = body.createCell(14);
            celdaBody.setCellValue(comprobante.getEstado());
            // usuario
            celdaBody = body.createCell(15);
            celdaBody.setCellValue(comprobante.getUsuario());
            // email destino
            celdaBody = body.createCell(16);
            celdaBody.setCellValue(comprobante.getEmailDestino());
            // Interlocutor
            celdaBody = body.createCell(17);
            celdaBody.setCellValue(comprobante.getInterlocutor());
            // razon social
            celdaBody = body.createCell(18);
            celdaBody.setCellValue(comprobante.getRazonSocial());
            // ruc cliente
            celdaBody = body.createCell(19);
            celdaBody.setCellValue(comprobante.getRucCliente());
            // tipo de Emision
            celdaBody = body.createCell(20);
            celdaBody.setCellValue(comprobante.getTipoEmision());
            i++;
        }
    }
    
    public void crearFileExcel(){
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            libro.write(out);
            out.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private CellStyle getEstiloTitulo(HSSFWorkbook libro) {
        final CellStyle cellStyle = libro.createCellStyle();
        final Font cellFont = libro.createFont();
        cellFont.setColor(IndexedColors.WHITE.getIndex());
        cellFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(cellFont);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }
    
    private String getSociedad(String ruc) {
        String sociedad ;
        if(AplicacionConstants.SOCIEDAD[0].equalsIgnoreCase(ruc)){
            sociedad = "1000";
        } else if(AplicacionConstants.SOCIEDAD[1].equalsIgnoreCase(ruc)) {
            sociedad = "4000";
        } else {
            sociedad = "5000";
        }
        return sociedad;
    }
    
    private String getNombreDocumento(String tipoDoc) {
        String respuesta = "";
        if (tipoDoc != null) {
            switch (tipoDoc) {
                case ComprobanteConstants.COD_FACTURA:
                    respuesta = ComprobanteConstants.FACTURA;
                    break;
                case ComprobanteConstants.COD_GUIA_REMISION:
                    respuesta = ComprobanteConstants.GUIA_DE_REMISION;
                    break;
                case ComprobanteConstants.COD_RETENCION:
                    respuesta = ComprobanteConstants.COMPROBANTE_RETENCION;
                    break;
                case ComprobanteConstants.COD_NOTA_CREDITO:
                    respuesta = ComprobanteConstants.NOTA_CREDITO;
                    break;
                case ComprobanteConstants.COD_NOTA_DEBITO:
                    respuesta = ComprobanteConstants.NOTA_DEBITO;
                    break;
                default:
                    respuesta = "comprobante";
            }
        }
        return respuesta;
    }

    public static String formatearNumeroDosDigitos(Object value) {
        double valor = 0d;
        try {
            valor = Double.parseDouble(value.toString());
            valor = Math.round(valor * 1000) / 1000.0d;
        } catch (NumberFormatException e) {
            valor = 0d;
        }
        return String.format("%.2f", valor).replaceAll(",", ".");
    }

    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }
    
}
