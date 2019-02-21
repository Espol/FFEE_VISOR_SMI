package com.csti.ce.seguridad.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.AccesoDAO;
import com.csti.ce.seguridad.domain.Acceso;

@Repository
public class AccesoDAOHibernate extends HibernateDaoSupport implements
		AccesoDAO {
	@Autowired
	public AccesoDAOHibernate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public Acceso getById(int id) {
		return (Acceso) getHibernateTemplate().get(Acceso.class, id);
	}
	
	@Override
	public List<Acceso> getAllByPagination(int initRow, int countRow) {
		// TODO Auto-generated method stub

        Criteria criteria = getSession().createCriteria(Acceso.class);
        criteria.setFirstResult(initRow);
        criteria.setMaxResults(countRow);
		return (List<Acceso>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Acceso> getAll() {
		// TODO Auto-generated method stub
		return (List<Acceso>) getSession().createCriteria(Acceso.class).list();
	}

	@Override
	public void save(Acceso entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Acceso entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Acceso entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteById(int id) {
		getHibernateTemplate().bulkUpdate( "DELETE FROM Acceso WHERE idAcceso=? ", id);
	}

	@Override
	public List<Acceso> getByRol(int id) {
		Criteria criteria = getSession().createCriteria(Acceso.class);
		criteria.add(Restrictions.eq("rol.idRol", id));
		criteria.setFetchMode("rol", FetchMode.JOIN);
		criteria.setFetchMode("opcion", FetchMode.JOIN);
		return (List<Acceso>) criteria.list();
	}
	@Override
	public List<Acceso> getByRols(List<Integer> idRols){
		Criteria criteria = getSession().createCriteria(Acceso.class);
		criteria.add(Restrictions.in("rol.idRol", idRols));
		criteria.setFetchMode("rol", FetchMode.JOIN);
		criteria.setFetchMode("opcion", FetchMode.JOIN);
		return (List<Acceso>) criteria.list();
	}
	@Override
	public int countByRol(int id) {
		Criteria criteria = getSession().createCriteria(Acceso.class);
		criteria.add(Restrictions.eq("rol.idRol",id));
		criteria.setProjection(Projections.rowCount());
        return (Integer)criteria.list().get(0);
	}
	@Override
	public int countByOpcion(int id) {
		Criteria criteria = getSession().createCriteria(Acceso.class);
		criteria.add(Restrictions.eq("opcion.idOpcion",id));
		criteria.setProjection(Projections.rowCount());
		return (Integer)criteria.list().get(0);
	}

	@Override
	public boolean existAcceso(Acceso acceso) {
		Criteria criteria = getSession().createCriteria(Acceso.class);
		criteria.add(Restrictions.eq("opcion.idOpcion",acceso.getOpcion().getIdOpcion()));
		criteria.add(Restrictions.eq("rol.idRol",acceso.getRol().getIdRol()));
		criteria.setProjection(Projections.rowCount());
		return (Integer)criteria.list().get(0)>0;
	}
}
