/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao.hibernate;

import com.csti.ce.integrador.dao.SociedadDAO;
import com.csti.ce.integrador.domain.Sociedad;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CSTICORP
 */
@Repository
public class SociedadDAOHibernate extends HibernateDaoSupport implements SociedadDAO {
    
    @Autowired
    public SociedadDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }


    @Override
    public Sociedad getSociedad(String ruc) {
        Criteria criteria = getSession().createCriteria(Sociedad.class);
        criteria.add(Restrictions.eq("ruc", ruc));
        return (Sociedad) criteria.list().get(0);
    }

    @Override
    public void updateVersionBySociedad(String ruc, int offline) {
        String sql = "UPDATE Sociedad SET offline = :offline WHERE ruc = :ruc";
        Query query = getSession().createQuery(sql);
        query.setParameter("offline", offline);
        query.setParameter("ruc", ruc);
        query.executeUpdate();
    }


    @Override
    public void updateURLPortalBySociedad(String ruc, String url, int portalImpl) {
        // TODO Auto-generated method stub
        String sql = "UPDATE Sociedad SET url = :url, portalImpl = :portalImpl WHERE ruc = :ruc";
        Query query = getSession().createQuery(sql);
        query.setParameter("ruc", ruc);
        query.setParameter("url", url);
        query.setParameter("portalImpl", portalImpl);
        query.executeUpdate();
    }


	@Override
	public void updateSociedad(Sociedad sociedad) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(sociedad);
	}
}
