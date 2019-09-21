/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao.hibernate;

import com.csti.ce.integrador.dao.ImpuestoDAO;
import com.csti.ce.integrador.domain.Impuesto;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MARCELO
 */
@Repository
public class ImpuestoDAOHibernate extends HibernateDaoSupport implements ImpuestoDAO {
    
    @Autowired
    public ImpuestoDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public Impuesto getImpuesto(int codigo, int codPorcentaje) {
        Criteria criteria = getSession().createCriteria(Impuesto.class);
        criteria.add(Restrictions.eq("codigo", codigo));
        criteria.add(Restrictions.eq("codigoPorcentaje", codPorcentaje));
        return (Impuesto) criteria.list().get(0);
    }
}
