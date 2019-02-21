package com.csti.ce.seguridad.dao.hibernate;

import com.csti.ce.constant.AplicacionConstants;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.OpcionDAO;
import com.csti.ce.seguridad.domain.Opcion;
import org.hibernate.criterion.Order;

@Repository
public class OpcionDAOHibernate extends HibernateDaoSupport implements
        OpcionDAO {

    @Autowired
    public OpcionDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Opcion getById(int id) {
        return (Opcion) getHibernateTemplate().get(Opcion.class, id);
    }

    @Override
    public List<Opcion> getByIds(Set<Integer> ids) {
        Criteria criteria = getSession().createCriteria(Opcion.class);
        criteria.add(Restrictions.in("idOpcion", ids));
        criteria.add(Restrictions.eq("activo", AplicacionConstants.SUCCESS));
        criteria.addOrder(Order.asc("orden"));
        return (List<Opcion>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Opcion> getAll() {
        Criteria criteria = getSession().createCriteria(Opcion.class);
        criteria.addOrder(Order.asc("orden"));
        return (List<Opcion>) criteria.list();
    }

    @Override
    public List<Opcion> getByQuery(String value) {
        return (List<Opcion>) getSession().createCriteria(Opcion.class).add(Restrictions.like("nombre", value, MatchMode.ANYWHERE).ignoreCase()).list();
    }

    @Override
    public void save(Opcion entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Opcion entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Opcion entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void deleteById(int id) {
        getHibernateTemplate().bulkUpdate("DELETE FROM Opcion WHERE idOpcion=? ", id);
    }

    @Override
    public List<Opcion> getByModulo(int id) {
        // TODO Auto-generated method stub
        Criteria criteria = getSession().createCriteria(Opcion.class);
        criteria.add(Restrictions.eq("modulo.idModulo", id));
        criteria.setFetchMode("modulo", FetchMode.JOIN);
        criteria.setFetchMode("acesos", FetchMode.JOIN);
        criteria.addOrder(Order.asc("orden"));
        return (List<Opcion>) criteria.list();
    }

    @Override
    public int countByModulo(int id) {
        Criteria criteria = getSession().createCriteria(Opcion.class);
        criteria.add(Restrictions.eq("modulo.idModulo", id));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }
}
