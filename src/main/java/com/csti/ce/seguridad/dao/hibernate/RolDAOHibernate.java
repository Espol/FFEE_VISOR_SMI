package com.csti.ce.seguridad.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.RolDAO;
import com.csti.ce.seguridad.domain.Rol;

@Repository
public class RolDAOHibernate extends HibernateDaoSupport implements RolDAO {
	@Autowired
	public RolDAOHibernate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public Rol getById(int id) {
		return (Rol) getHibernateTemplate().get(Rol.class, id);
	}

	@Override
	public List<Rol> getAll() {
		return getSession().createCriteria(Rol.class).list();
	}

	@Override
	public void save(Rol entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Rol entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Rol entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteById(int id) {
            getHibernateTemplate().delete(id);
	}

}
