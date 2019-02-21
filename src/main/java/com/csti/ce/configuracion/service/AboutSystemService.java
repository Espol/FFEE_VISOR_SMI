package com.csti.ce.configuracion.service;

import org.springframework.web.bind.annotation.RequestParam;

import com.csti.ce.configuracion.beanXML.FirmaView;
import com.csti.ce.configuracion.beanXML.SapSetting;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.util.MailSetting;

public interface AboutSystemService {
	
	public void updateSociedad(Sociedad sociedad);
	
	public Sociedad getSociedadByRuc(String ruc);
	
	public MailSetting getCorreoBySociedad(String ruc) throws Exception;
	
	public void updateCorreoBySociedad(String ruc, String host, String port, String user, String password);
	
	public SapSetting getConfiguracionSAPBySociedad(String ruc) throws Exception ;
	
	public void updateConfiguracionSAPBySociedad(String ruc, String mandante, String usuario, String password,  String ipSap,
            String idiomaSap,
            String instanciaSap,
            String idSistema);
	
	public String getPathBySociedad(String ruc);
	
	public FirmaView getConfiguracionFirma(String ruc);
	
	public void updateConfiguracionFirma(String ruc, String base, String firma, String clave, String fechaCaducidad, int iniNotifFirma, int intervalNotifFirma, String mailNotificacion );	
}
