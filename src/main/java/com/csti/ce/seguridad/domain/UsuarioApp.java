package com.csti.ce.seguridad.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UsuarioApp implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuarioApp;
    private String userNameApp;
    private String passwordApp;
    private String sociedad;
    private String ambiente;

    private String sistema;
    private String estadoApp;
    private Usuario usuario;
    private boolean valido = false;

    public int getIdUsuarioApp() {
        return idUsuarioApp;
    }

    public void setIdUsuarioApp(int idUsuarioApp) {
        this.idUsuarioApp = idUsuarioApp;
    }

    public String getUserNameApp() {
        return userNameApp;
    }

    public void setUserNameApp(String userNameApp) {
//        this.userNameApp = userNameApp.toUpperCase();
        this.userNameApp = userNameApp;
    }

    public String getPasswordApp() {
        return passwordApp;
    }

    public void setPasswordApp(String passwordApp) {
//        this.passwordApp = passwordApp.toUpperCase();
        this.passwordApp = passwordApp;
    }

    public String getEstadoApp() {
        return estadoApp;
    }

    public void setEstadoApp(String estadoApp) {
        this.estadoApp = estadoApp;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getAmbiente() {
        return ambiente;
    }

}
