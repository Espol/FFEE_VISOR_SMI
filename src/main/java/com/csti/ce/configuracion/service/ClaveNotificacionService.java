/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.configuracion.service;

import com.csti.ce.monitor.domain.Parametro;
import java.util.List;

/**
 *
 * @author User
 */
public interface ClaveNotificacionService {
    public List<Parametro> getParametro(String sociedad);
}
