/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao.hibernate;

import com.csti.ce.constant.GeneralConstant;
import com.csti.ce.monitor.dao.ParametroDAO;
import com.csti.ce.monitor.domain.Parametro;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public class ParametroDAOHibernate extends HibernateDaoSupport implements ParametroDAO {

    @Autowired
    public ParametroDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Parametro getSociedad(String sociedad)  {
        Criteria criteria = getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("programa", GeneralConstant.SOCIEDAD));
        criteria.add(Restrictions.eq("parametroLow", sociedad));
        return (Parametro) criteria.uniqueResult();
    }

    @Override
    public List<Parametro> getParametrosByPrograma(String programa) {
        Criteria criteria = getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("programa", programa));
        return (List<Parametro>) criteria.list();
    }

    @Override
    public void update(Parametro parametro)  {
        getHibernateTemplate().update(parametro);
    }

    @Override
    public Parametro getParametroByCampo(String programa, String campo) {
        Criteria criteria = getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("programa", programa));
        criteria.add(Restrictions.eq("campo", campo));
        return (Parametro) criteria.uniqueResult();
    }

    @Override
    public Parametro getById(int id) {
        return (Parametro) getHibernateTemplate().get(Parametro.class, id);
    }
}
