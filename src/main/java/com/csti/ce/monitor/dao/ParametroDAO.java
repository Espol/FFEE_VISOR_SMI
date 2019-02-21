/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao;

import com.csti.ce.monitor.domain.Parametro;
import com.csti.ce.exception.ParametroReaderException;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface ParametroDAO {

    public Parametro getSociedad(String sociedad);

    public List<Parametro> getParametrosByPrograma(String programa) ;

    public void update(Parametro parametro);
 
    public Parametro getParametroByCampo(String programa, String campo);

    public Parametro getById(int id);
}
