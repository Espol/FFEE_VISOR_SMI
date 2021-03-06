/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao;

import com.csti.ce.monitor.domain.Clave;

/**
 *
 * @author Usuario
 */
public interface ClaveDAO {

    public void setAmbiente(String ambiente);

    public String getAmbiente();

    public Clave getAleatorio(String ruc, String ambiente) throws Exception;

    public boolean registrarClaveUtilizada(long idClave) throws Exception;

    public boolean guardarClave(Clave clave) throws Exception;

    public int getNumeroClavesDisponibles(String ruc);

    public int getNumeroClavesTotal(String ruc);
}
