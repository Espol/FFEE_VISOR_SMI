/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.seguridad.dao.hibernate;

import com.csti.ce.seguridad.dao.UsuarioSistemaDAO;
import com.csti.ce.seguridad.domain.UsuarioSistema;
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
public class UsuarioSistemaDAOHibernate extends HibernateDaoSupport implements UsuarioSistemaDAO {
    
    @Autowired
    public UsuarioSistemaDAOHibernate(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }

    @Override
    public List<UsuarioSistema> getAll() {
        return getSession().createCriteria(UsuarioSistema.class).list();
    }

    @Override
    public void save(UsuarioSistema entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(UsuarioSistema entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdate(UsuarioSistema entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(UsuarioSistema entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteById(int id) {
        getHibernateTemplate().delete(id);
    }
    
}
