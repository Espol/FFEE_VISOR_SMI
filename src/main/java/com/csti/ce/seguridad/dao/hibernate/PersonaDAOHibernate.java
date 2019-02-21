/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.dao.hibernate;

import com.csti.ce.seguridad.dao.PersonaDAO;
import com.csti.ce.seguridad.domain.Persona;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class PersonaDAOHibernate extends HibernateDaoSupport implements PersonaDAO {
    
    @Autowired
    public PersonaDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Persona getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public List<Persona> getAll() {
        return getSession().createCriteria(Persona.class).list();
    }

    @Override
    public void save(Persona entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Persona entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Persona entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
