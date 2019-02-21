/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.domain;

/**
 *
 * @author Usuario
 */
public class UsuarioSerie {

    private static final long serialVersionUID = 1L;
    private int idUsuarioSerie;
    private Usuario usuario;
    private Serie serie;

    public UsuarioSerie() {
    }

    /**
     * @return the idUsuarioSerie
     */
    public int getIdUsuarioSerie() {
        return idUsuarioSerie;
    }

    /**
     * @param idUsuarioSerie the idUsuarioSerie to set
     */
    public void setIdUsuarioSerie(int idUsuarioSerie) {
        this.idUsuarioSerie = idUsuarioSerie;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the serie
     */
    public Serie getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}
