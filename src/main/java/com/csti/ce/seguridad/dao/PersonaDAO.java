/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.dao;

import com.csti.ce.seguridad.domain.Persona;
import java.util.List;

/**
 *
 * @author User
 */
public interface PersonaDAO {

    Persona getById(int id);

    List<Persona> getAll();

    void save(Persona entity);

    void update(Persona entity);

    void delete(Persona entity);

    void deleteById(int id);

}
