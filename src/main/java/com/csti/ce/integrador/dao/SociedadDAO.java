/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao;

import com.csti.ce.integrador.domain.Sociedad;

/**
 *
 * @author CSTICORP
 */
public interface SociedadDAO {
    
    public Sociedad getSociedad(String ruc);
    public void updateVersionBySociedad(String ruc, int offline);
    public void updateURLPortalBySociedad(String ruc, String url, int portalImpl);
    
    public void updateSociedad(Sociedad sociedad);
    
}
