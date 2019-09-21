/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.integrador.domain;

import java.io.Serializable;

/**
 *
 * @author MARCELO
 */
public class Impuesto implements Serializable {

    private int codigo;
    private int codigoPorcentaje;
    private int porcentaje;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(int codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

}
