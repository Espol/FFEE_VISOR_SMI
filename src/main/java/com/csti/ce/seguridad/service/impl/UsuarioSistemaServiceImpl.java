/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.service.impl;

import com.csti.ce.monitor.view.UsuarioSistemaView;
import com.csti.ce.seguridad.dao.UsuarioSistemaDAO;
import com.csti.ce.seguridad.domain.UsuarioSistema;
import com.csti.ce.seguridad.service.UsuarioSistemaService;
import com.csti.ce.util.Response;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioSistemaServiceImpl implements UsuarioSistemaService {
    
    @Autowired
    UsuarioSistemaDAO usuarioSistemaDAO;

    @Override
    public Response save(UsuarioSistema entity) {
        usuarioSistemaDAO.save(entity);
        Response response = new Response();
        response.messageINSERT();
        return response;
    }

    @Override
    public Response update(UsuarioSistema entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response delete(UsuarioSistema entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioSistema get(UsuarioSistema entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<UsuarioSistemaView> getAll() {
        List<UsuarioSistema> list = usuarioSistemaDAO.getAll();
        UsuarioSistemaView usuarioSistemaView;
        List<UsuarioSistemaView> listaUsuarioSistemaView = new ArrayList<>();
        for (UsuarioSistema usuarioSistema : list) {
            usuarioSistemaView = new UsuarioSistemaView();
            usuarioSistemaView.setUsuarioSistema(usuarioSistema.getUsuario().getUserName());
            usuarioSistemaView.setActivo(usuarioSistema.getUsuario().getActivo());
            usuarioSistemaView.setIdentificador(usuarioSistema.getSistema().getIdentificador());
            usuarioSistemaView.setDescripcion(usuarioSistema.getSistema().getDescripcion());
            usuarioSistemaView.setNombreSistema(usuarioSistema.getSistema().getNombre());
            usuarioSistemaView.setLicencia(usuarioSistema.getSistema().getLicencia());
            listaUsuarioSistemaView.add(usuarioSistemaView);
        }
        return listaUsuarioSistemaView;
    }
    
}
