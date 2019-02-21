package com.csti.ce.integrador.dao;

import com.csti.ce.integrador.domain.Documento;

public interface DocumentoDAO {
	
	public void updateDocumento(String nroSri, String tipoDocumento, int estadoNotifRech);
        
        public Documento getDocumento(String ruc, String nroSri, String tipoDoc);
        
        public void updateDocumentoRechazado(int idPeriodo, int idSociedad, int secuencia);

}
