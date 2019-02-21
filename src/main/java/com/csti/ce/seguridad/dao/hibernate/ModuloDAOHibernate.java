package com.csti.ce.seguridad.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.ModuloDAO;
import com.csti.ce.seguridad.domain.Modulo;
import org.hibernate.criterion.Order;

@Repository
public class ModuloDAOHibernate extends HibernateDaoSupport implements
        ModuloDAO {

    @Autowired
    public ModuloDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Modulo getById(int id) {
        return getHibernateTemplate().get(Modulo.class, id);
    }

    @Override
    public List<Modulo> getByIds(Set<Integer> ids) {
        Criteria criteria = getSession().createCriteria(Modulo.class);
        criteria.add(Restrictions.in("idModulo", ids));
        criteria.addOrder(Order.asc("orden"));
        return (List<Modulo>) criteria.list();
    }

    @Override
    public List<Modulo> getAll() {
        Criteria criteria = getSession().createCriteria(Modulo.class);
        criteria.addOrder(Order.asc("orden"));
        return (List<Modulo>) criteria.list();
    }

    @Override
    public void save(Modulo entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Modulo entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Modulo entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteById(int id) {

    }

}
