/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.configuracion.service.VersionService;
import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.Sociedad;

/**
 *
 * @author CSTICORP
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class VersionServiceImpl implements VersionService {
    
    @Autowired
    private SociedadDAO sociedadDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Sociedad getVersionBySociedad(String ruc) {
        return sociedadDAO.getSociedad(ruc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateVesionBySociedad(String ruc, int offline) {
        sociedadDAO.updateVersionBySociedad(ruc, offline);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateURLPortalBySociedad(String ruc, String url, int portalImpl) {
		// TODO Auto-generated method stub
		sociedadDAO.updateURLPortalBySociedad(ruc, url, portalImpl);
	}
    
}
