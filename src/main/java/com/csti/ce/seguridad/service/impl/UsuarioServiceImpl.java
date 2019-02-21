package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.UsuarioDAO;
import com.csti.ce.seguridad.dao.UsuarioRolDAO;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.service.UsuarioService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    UsuarioRolDAO usuarioRolDAO;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response save(Usuario entity) {
        Response respuesta = new Response();
        usuarioDAO.save(entity);
        respuesta.messageINSERT();
        return respuesta;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response update(Usuario entity) {
        Response respuesta = new Response();
        usuarioDAO.update(entity);
        respuesta.messageUPDATE();
        return respuesta;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Response delete(Usuario entity) {
        Response respuesta = new Response();
        if (usuarioRolDAO.countByUsuario(entity.getIdUsuario()) == 0) {
            usuarioDAO.delete(entity);
            respuesta.messageDELETE();
        } else {
            respuesta.setMessage("La Entidad Esta Relacionada.");
        }
        return respuesta;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario get(Usuario entity) {
        return usuarioDAO.getById(entity.getIdUsuario());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getAll() {
        return usuarioDAO.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getByQuery(String value) {
        return usuarioDAO.getByQuery(value);
    }
;
}
