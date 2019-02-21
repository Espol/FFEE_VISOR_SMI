package com.csti.ce.integrador.domain;

public class Documento {
	
	private int secuencia;
	private int idPeriodo;
	private int idSociedad;
	private String tipoDocumento;
	private String establecimiento;
	private String puntoEmision;
	private String numero;
	private String serieCorrelativo;
	private int estadoNotifRechazado;
	public int getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}
	public int getIdPeriodo() {
		return idPeriodo;
	}
	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public int getIdSociedad() {
		return idSociedad;
	}
	public void setIdSociedad(int idSociedad) {
		this.idSociedad = idSociedad;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public String getPuntoEmision() {
		return puntoEmision;
	}
	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSerieCorrelativo() {
		return serieCorrelativo;
	}
	public void setSerieCorrelativo(String serieCorrelativo) {
		this.serieCorrelativo = serieCorrelativo;
	}
	public int getEstadoNotifRechazado() {
		return estadoNotifRechazado;
	}
	public void setEstadoNotifRechazado(int estadoNotifRechazado) {
		this.estadoNotifRechazado = estadoNotifRechazado;
	}

}
