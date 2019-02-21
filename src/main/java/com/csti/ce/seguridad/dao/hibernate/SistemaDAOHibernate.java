/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.seguridad.dao.hibernate;

import com.csti.ce.seguridad.dao.SistemaDAO;
import com.csti.ce.seguridad.domain.Sistema;
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
public class SistemaDAOHibernate extends HibernateDaoSupport implements SistemaDAO {

    @Autowired
    public SistemaDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Sistema findById(int id) {
        return (Sistema) getHibernateTemplate().get(Sistema.class, id);
    }

    @Override
    public List<Sistema> findAll() {
        return getSession().createCriteria(Sistema.class).list();
    }

    @Override
    public void save(Sistema entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Sistema entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Sistema entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteById(int id) {
        getHibernateTemplate().delete(id);
    }
}
