/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.service.impl;

import com.csti.ce.seguridad.dao.PersonaDAO;
import com.csti.ce.seguridad.domain.Persona;
import com.csti.ce.seguridad.service.PersonaService;
import com.csti.ce.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class PersonaServiceImpl implements PersonaService{

    @Autowired
    PersonaDAO personaDAO;
    
    @Override
    public Response save(Persona entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response update(Persona entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response delete(Persona entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona get(Persona entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Persona> getAll() {
        return personaDAO.getAll();
    }
    
}
