package com.csti.ce.seguridad.domain;
// Generated 26/04/2014 10:50:12 AM by Hibernate Tools 3.6.0

/**
 * Acceso generated by hbm2java
 */
public class Acceso implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idAcceso;
    private Permiso permiso;
    private Rol rol;
    private Opcion opcion;

    public Acceso() {
    }

    public Acceso(int id) {
        this.idAcceso = id;
    }

    public Acceso(int idAcceso, Permiso permiso, Rol rol, Opcion opcion) {
        this.idAcceso = idAcceso;
        this.permiso = permiso;
        this.rol = rol;
        this.opcion = opcion;
    }

    public int getIdAcceso() {
        return this.idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Permiso getPermiso() {
        return this.permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Rol getRol() {
        return this.rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Opcion getOpcion() {
        return this.opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

}
