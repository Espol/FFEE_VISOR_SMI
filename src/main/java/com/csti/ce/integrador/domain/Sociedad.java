/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.domain;

import java.util.Date;

/**
 *
 * @author CSTICORP
 */
public class Sociedad {
    
    private int idSociedad;
    private String ruc;
    private String razonSocial;
    private String pathRoot;
    private String pathCertificado;
    private String claveCertificado;
    private String confSap;
    private String confMail;
    private String mailNotificacion;
    private int offline;
    
    private Date vencFirma;
    
    private String mailFactura;
    private String mailRetencion;
    private String mailCredito;
    private String mailDebito;
    private String mailGuia;
    

    private int intervalNotifFirma;
    private int iniNotifFirma;
    private int procVencFirma;
    

    private int iniTimeAvailableCorrecion;
    private int intervalNotifDocRechazado;
    
    private String url;
    
    private int portalImpl;

    public int getIdSociedad() {
        return idSociedad;
    }

    public void setIdSociedad(int idSociedad) {
        this.idSociedad = idSociedad;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getPathRoot() {
        return pathRoot;
    }

    public void setPathRoot(String pathRoot) {
        this.pathRoot = pathRoot;
    }

    public String getPathCertificado() {
        return pathCertificado;
    }

    public void setPathCertificado(String pathCertificado) {
        this.pathCertificado = pathCertificado;
    }

    public String getClaveCertificado() {
        return claveCertificado;
    }

    public void setClaveCertificado(String claveCertificado) {
        this.claveCertificado = claveCertificado;
    }

    public String getConfSap() {
        return confSap;
    }

    public void setConfSap(String ConfSap) {
        this.confSap = ConfSap;
    }

    public String getConfMail() {
        return confMail;
    }

    public void setConfMail(String confMail) {
        this.confMail = confMail;
    }

    public String getMailNotificacion() {
        return mailNotificacion;
    }

    public void setMailNotificacion(String mailNotificacion) {
        this.mailNotificacion = mailNotificacion;
    }

    public int getOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

	public Date getVencFirma() {
		return vencFirma;
	}

	public void setVencFirma(Date vencFirma) {
		this.vencFirma = vencFirma;
	}

	public String getMailFactura() {
		return mailFactura;
	}

	public void setMailFactura(String mailFactura) {
		this.mailFactura = mailFactura;
	}

	public String getMailRetencion() {
		return mailRetencion;
	}

	public void setMailRetencion(String mailRetencion) {
		this.mailRetencion = mailRetencion;
	}

	public String getMailCredito() {
		return mailCredito;
	}

	public void setMailCredito(String mailCredito) {
		this.mailCredito = mailCredito;
	}

	public String getMailDebito() {
		return mailDebito;
	}

	public void setMailDebito(String mailDebito) {
		this.mailDebito = mailDebito;
	}

	public String getMailGuia() {
		return mailGuia;
	}

	public void setMailGuia(String mailGuia) {
		this.mailGuia = mailGuia;
	}

	public int getIntervalNotifFirma() {
		return intervalNotifFirma;
	}

	public void setIntervalNotifFirma(int intervalNotifFirma) {
		this.intervalNotifFirma = intervalNotifFirma;
	}

	public int getIniNotifFirma() {
		return iniNotifFirma;
	}

	public void setIniNotifFirma(int iniNotifFirma) {
		this.iniNotifFirma = iniNotifFirma;
	}

	public int getProcVencFirma() {
		return procVencFirma;
	}

	public void setProcVencFirma(int procVencFirma) {
		this.procVencFirma = procVencFirma;
	}

	public int getIniTimeAvailableCorrecion() {
		return iniTimeAvailableCorrecion;
	}

	public void setIniTimeAvailableCorrecion(int iniTimeAvailableCorrecion) {
		this.iniTimeAvailableCorrecion = iniTimeAvailableCorrecion;
	}

	public int getIntervalNotifDocRechazado() {
		return intervalNotifDocRechazado;
	}

	public void setIntervalNotifDocRechazado(int intervalNotifDocRechazado) {
		this.intervalNotifDocRechazado = intervalNotifDocRechazado;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPortalImpl() {
		return portalImpl;
	}

	public void setPortalImpl(int portalImpl) {
		this.portalImpl = portalImpl;
	}
    
}
