package com.csti.ce.seguridad.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.PermisoDAO;
import com.csti.ce.seguridad.domain.Permiso;

@Repository
public class PermisoDAOHibernate extends HibernateDaoSupport implements PermisoDAO {
	@Autowired
	public PermisoDAOHibernate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}
	@Override
	public List<Permiso> getAll() {
		return getSession().createCriteria(Permiso.class).list();
	}

    @Override
    public void save(Permiso entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Permiso entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void deleteById(int id) {
        getHibernateTemplate().delete(id);
    }

    @Override
    public void delete(Permiso entity) {
        getHibernateTemplate().delete(entity);
    }

}
