/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class Serie implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int idSerie;
    private String establecimiento;
    private String puntoEmision;
    private int activo;
    private Set usuarioSeries = new HashSet(0);

    public Serie() {
    }

    /**
     * @return the idSerie
     */
    public int getIdSerie() {
        return idSerie;
    }

    /**
     * @param idSerie the idSerie to set
     */
    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    /**
     * @return the establecimiento
     */
    public String getEstablecimiento() {
        return establecimiento;
    }

    /**
     * @param establecimiento the establecimiento to set
     */
    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    /**
     * @return the puntoEmision
     */
    public String getPuntoEmision() {
        return puntoEmision;
    }

    /**
     * @param puntoEmision the puntoEmision to set
     */
    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    /**
     * @return the activo
     */
    public int getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(int activo) {
        this.activo = activo;
    }

    /**
     * @return the usuarioSeries
     */
    public Set getUsuarioSeries() {
        return usuarioSeries;
    }

    /**
     * @param usuarioSeries the usuarioSeries to set
     */
    public void setUsuarioSeries(Set usuarioSeries) {
        this.usuarioSeries = usuarioSeries;
    }

}
