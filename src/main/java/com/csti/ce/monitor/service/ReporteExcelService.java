/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.monitor.service;

import com.csti.ce.monitor.view.ComprobanteView;
import java.util.List;

/**
 *
 * @author MARCELO
 */
public interface ReporteExcelService {
    
    public void setComprobantes(List<ComprobanteView> comprobantes);
    public List<ComprobanteView> getComprobantes();
    public void generarCabecera();
    public void generarReporte() throws Exception ;
    public void setRucSociedad(String sociedad);
    public String getRucSociedad();
    public String getFileExcel();
    public void setIndex(int index);
    public int getIndex();
    public void crearFileExcel();
    
    public void setEsquema(int esquema);
    
}
