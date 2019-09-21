package com.csti.ce.integrador.domain;

public class Documento {
	
	private int secuencia;
	private int idPeriodo;
	private int idSociedad;
	private String tipoDocumento;
        private int subTipoDoc;
	private String establecimiento;
	private String puntoEmision;
	private String numero;
	private String serieCorrelativo;
	private Integer estadoNotifRechazado;
        private String pathXML;//XML SIN FIRMAR
        private String claveAcceso;
        private boolean ultimo;
    private String estadoSri;
        
	public Integer getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Integer secuencia) {
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
	public Integer getEstadoNotifRechazado() {
		return estadoNotifRechazado;
	}
	public void setEstadoNotifRechazado(Integer estadoNotifRechazado) {
		this.estadoNotifRechazado = estadoNotifRechazado;
	}

    public String getPathXML() {
        return pathXML;
    }

    public void setPathXML(String pathXML) {
        this.pathXML = pathXML;
    }

    public int getSubTipoDoc() {
        return subTipoDoc;
    }

    public void setSubTipoDoc(int subTipoDoc) {
        this.subTipoDoc = subTipoDoc;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getEstadoSri() {
        return estadoSri;
    }

    public void setEstadoSri(String estadoSri) {
        this.estadoSri = estadoSri;
    }

    public boolean isUltimo() {
        return ultimo;
    }

    public void setUltimo(boolean ultimo) {
        this.ultimo = ultimo;
    }

}
