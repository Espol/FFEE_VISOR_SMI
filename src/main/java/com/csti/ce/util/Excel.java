/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.util;

/**
 *
 * @author MARCELO
 */
public class Excel {
    
    public static final String[] ENCABEZADO = {"ESQUEMA","SOCIEDAD","RUC","NRO SRI","DOC INTERNO(DOC_REFERENCIA)",
        "DOC SUSTENTO","FECHA EMISION","HORA EMISION","DESCRIPCION (TIPO DOC)","TIPO DOC REFERENCIA",
        "CLAVE ACCESO","NRO AUTORIZACION","FECHA AUTORIZACION","ESTADO","USUARIO","EMAIL",
        "CLIENTE","RAZON SOCIAL","RUC CLIENTE","TIPO EMISION"};
    
    public static final String TITULO = "REPORTE DE COMPROBANTES";
    
    public static final String REPORTE_EXCEL_PATH = "REPORTE_EXCEL_PATH";
    public static final String OFF_LINE = "OFFLINE";
    public static final String ON_LINE = "ONLINE";
    public static final int OFFLINE = 1;
    public static final int ONLINE = 0;
    
}
