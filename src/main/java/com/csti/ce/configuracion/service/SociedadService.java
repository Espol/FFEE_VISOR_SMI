package com.csti.ce.configuracion.service;

import com.csti.ce.integrador.domain.Sociedad;

public interface SociedadService {
	
	public void updateSociedad(Sociedad sociedad);
	
	public Sociedad getSociedadByRuc(String ruc);
	
	
	
}
