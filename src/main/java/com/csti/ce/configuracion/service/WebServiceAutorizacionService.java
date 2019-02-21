/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.configuracion.service;

import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.util.Response;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public interface WebServiceAutorizacionService {

    public List<Parametro> getParametros(String sociedad);

    public Response updateParametros(Map<String, Object> maps, String sociedad);
}
