package com.csti.ce.configuracion.beanXML;

import java.util.Date;

public class FirmaView {
	
	private String pathBase;
	private String pathFirma;
	private String password;
	private Date vencFirma;
	

    private int intervalNotifFirma;
    private int iniNotifFirma;
    private int procVencFirma;
	
    private String mailNotificacion;
	
	public String getPathBase() {
		return pathBase;
	}
	public void setPathBase(String pathBase) {
		this.pathBase = pathBase;
	}
	public String getPathFirma() {
		return pathFirma;
	}
	public void setPathFirma(String pathFirma) {
		this.pathFirma = pathFirma;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getVencFirma() {
		return vencFirma;
	}
	public void setVencFirma(Date vencFirma) {
		this.vencFirma = vencFirma;
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
	public String getMailNotificacion() {
		return mailNotificacion;
	}
	public void setMailNotificacion(String mailNotificacion) {
		this.mailNotificacion = mailNotificacion;
	}
}
