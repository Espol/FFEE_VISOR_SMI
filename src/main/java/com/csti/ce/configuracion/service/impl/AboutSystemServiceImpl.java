package com.csti.ce.configuracion.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csti.ce.configuracion.beanXML.FirmaView;
import com.csti.ce.configuracion.beanXML.SapSetting;
import com.csti.ce.configuracion.service.AboutSystemService;
import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.util.MailSetting;

@Service
public class AboutSystemServiceImpl implements AboutSystemService {
	
	@Autowired
	private SociedadDAO sociedadDAO;
	
	@Override
	public Sociedad getSociedadByRuc(String ruc) {
		return sociedadDAO.getSociedad(ruc);
	}
	
	@Override
	public void updateSociedad(Sociedad sociedad) {
		sociedadDAO.updateSociedad(sociedad);
	}

	@Override
	public MailSetting getCorreoBySociedad(String ruc) throws Exception {
		try {
			String xml  = sociedadDAO.getSociedad(ruc).getConfMail();
			JAXBContext jc = JAXBContext.newInstance(MailSetting.class);
	        Unmarshaller ums = jc.createUnmarshaller();
	        MailSetting mailSetting = (MailSetting) (ums.unmarshal(new StringReader(xml)));
			return mailSetting;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void updateCorreoBySociedad(String ruc, String host, String port, String user, String password){
		MailSetting mailSetting = new MailSetting();
		mailSetting.setHost(host);
		mailSetting.setPort(port);
		mailSetting.setUser(user);
		mailSetting.setPassword(password);
		try {
			StringWriter writer = new StringWriter();  
			JAXBContext jaxbContext = JAXBContext.newInstance(MailSetting.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);//elimina la primera línea del xml
			
			jaxbMarshaller.marshal(mailSetting, writer);
			String theXML = writer.toString();
			Sociedad sociedad = sociedadDAO.getSociedad(ruc);
			sociedad.setConfMail(theXML);
			sociedadDAO.updateSociedad(sociedad);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	@Override
	public SapSetting getConfiguracionSAPBySociedad(String ruc) throws Exception {
		try {
			String xml  = sociedadDAO.getSociedad(ruc).getConfSap();
			JAXBContext jc = JAXBContext.newInstance(SapSetting.class);
	        Unmarshaller ums = jc.createUnmarshaller();
	        SapSetting sapSetting = (SapSetting) (ums.unmarshal(new StringReader(xml)));
			return sapSetting;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void updateConfiguracionSAPBySociedad(String ruc, String mandante, String usuario, String password,
			String ipSap, String idiomaSap, String instanciaSap, String idSistema) {
		SapSetting sapSetting = new SapSetting();
		sapSetting.setContrasena(password);
		sapSetting.setIdioma(idiomaSap);
		sapSetting.setIdSistema(idSistema);
		sapSetting.setIpServidor(ipSap);
		sapSetting.setMandante(mandante);
		sapSetting.setNumeroInstancia(instanciaSap);
		sapSetting.setUsuario(usuario);
		try {
			StringWriter writer = new StringWriter();  
			JAXBContext jaxbContext = JAXBContext.newInstance(SapSetting.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);//elimina la primera línea del xml
			
			jaxbMarshaller.marshal(sapSetting, writer);
			String theXML = writer.toString();
			Sociedad sociedad = sociedadDAO.getSociedad(ruc);
			sociedad.setConfSap(theXML);
			sociedadDAO.updateSociedad(sociedad);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String getPathBySociedad(String ruc) {
		return sociedadDAO.getSociedad(ruc).getPathRoot();
	}

	@Override
	public FirmaView getConfiguracionFirma(String ruc) {
		FirmaView firma = new FirmaView();
		Sociedad sociedad = sociedadDAO.getSociedad(ruc);
		firma.setPassword(sociedad.getClaveCertificado());
		String pathFirma = sociedad.getPathCertificado();
		firma.setPathBase(pathFirma.substring(0, pathFirma.lastIndexOf("\\") +1));
		firma.setPathFirma(pathFirma);
		firma.setVencFirma(sociedad.getVencFirma());
		
		firma.setIniNotifFirma( sociedad.getIniNotifFirma() );
		firma.setIntervalNotifFirma( sociedad.getIntervalNotifFirma() );
		firma.setProcVencFirma( sociedad.getProcVencFirma() );
		
		firma.setMailNotificacion( sociedad.getMailNotificacion() );
		
		return firma;
	}

	@Override
	public void updateConfiguracionFirma(String ruc, String base, String firma, String clave, String fechaCaducidad, int iniNotifFirma, int intervalNotifFirma , String mailNotificacion) {
		
		Sociedad sociedad = sociedadDAO.getSociedad(ruc);
		sociedad.setClaveCertificado(clave);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(fechaCaducidad);
			sociedad.setVencFirma(date);
			
			sociedad.setIniNotifFirma(iniNotifFirma);
			sociedad.setIntervalNotifFirma(intervalNotifFirma);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		sociedad.setVencFirma(fechaCaducidad);
//		String pathCertificado = base  + firma.substring(firma.lastIndexOf("\\") + 1 , firma.length() );
//		sociedad.setPathCertificado(pathCertificado);
		sociedad.setPathCertificado(firma);
		sociedad.setMailNotificacion(mailNotificacion);
		
		sociedadDAO.updateSociedad(sociedad);
	}
	
	

}
