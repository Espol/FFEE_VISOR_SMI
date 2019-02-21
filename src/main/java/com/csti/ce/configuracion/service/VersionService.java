/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.service;

import com.csti.ce.integrador.domain.Sociedad;

/**
 *
 * @author CSTICORP
 */
public interface VersionService {
    
    public Sociedad getVersionBySociedad(String ruc);
    public void updateVesionBySociedad(String ruc, int offline);
    public void updateURLPortalBySociedad(String ruc, String url,int portalImpl);
    
}
