/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.monitor.service.impl;

import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.monitor.service.ReporteExcelService;
import com.csti.ce.monitor.view.ComprobanteView;
import com.csti.ce.util.AplicacionUtil;
import com.csti.ce.util.Excel;
import com.csti.ce.util.ReporteExcel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MARCELO
 */
@Service
public class ReporteExcelServiceImpl implements ReporteExcelService {
    
    @Autowired
    SociedadDAO sociedadDAO;

    private List<ComprobanteView> comprobantes;
    private String rucSociedad;
//    private Parametro ParametroSociedad;
    private String fileExcel;
    private int index = 1;
    private int esquema;

    private ReporteExcel reporteExcel;

    @Override
    public void setComprobantes(List<ComprobanteView> comprobantes) {
        this.comprobantes = null;
        this.comprobantes = comprobantes;
    }

    @Override
    public List<ComprobanteView> getComprobantes() {
        return this.comprobantes;
    }

    @Override
    public void generarReporte() throws Exception {
        try {
            reporteExcel.setIndex(this.getIndex());
            reporteExcel.setComprobantes(comprobantes);
            reporteExcel.generaExcel();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String getRucSociedad() {
        return rucSociedad;
    }

    @Override
    public void setRucSociedad(String rucSociedad) {
        this.rucSociedad = rucSociedad;
    }

    @Override
    public String getFileExcel() {
        return this.formarPathExcel() + ".xls";
    }

    private void getFileNameExcel() {
        fileExcel = "\\ReporteComprobante-"+this.getEsquema()+"-" + AplicacionUtil.obtenerDateTimeActualByExcel();
    }

    private String getPathFileExcel() {
        return sociedadDAO.getSociedad(rucSociedad).getPathRoot();
    }

    private String formarPathExcel() {
        return this.getPathFileExcel() + fileExcel;
    }
    
    private String getEsquema(){
        if(this.esquema == 0){
            return Excel.ON_LINE;
        }
        return Excel.OFF_LINE;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void generarCabecera() {
        this.reporteExcel = new ReporteExcel();
        this.getFileNameExcel();
        reporteExcel.setEsquema(this.getEsquema());
        reporteExcel.setFileName(this.formarPathExcel() + ".xls");
        reporteExcel.generarCabecera();
    }

    @Override
    public void crearFileExcel() {
        reporteExcel.crearFileExcel();
    }

    @Override
    public void setEsquema(int esquema) {
        this.esquema = esquema;
    }
    
}
