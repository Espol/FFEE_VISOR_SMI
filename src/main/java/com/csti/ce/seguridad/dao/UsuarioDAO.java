package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Usuario;

public interface UsuarioDAO {

    Boolean getPermiso(Usuario usuario, String idComponent);

    Usuario getById(int id);

    List<Usuario> getAllBySociedad(String ruc);
    
    List<Usuario> getAll();

    List<Usuario> getByQuery(String value);

    void save(Usuario entity);

    void update(Usuario entity);

    void delete(Usuario entity);

    void deleteById(Long id);
}
