/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.dao;

import com.csti.ce.seguridad.domain.UsuarioSistema;
import java.util.List;

/**
 *
 * @author User
 */
public interface UsuarioSistemaDAO {

    List<UsuarioSistema> getAll();

    void save(UsuarioSistema entity);

    void update(UsuarioSistema entity);

    void saveOrUpdate(UsuarioSistema entity);

    void delete(UsuarioSistema entity);

    void deleteById(int id);

}
