/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao.hibernate;

import com.csti.ce.integrador.dao.DocumentoAnuladoDAO;
import com.csti.ce.integrador.domain.DocumentoAnulado;
import com.csti.ce.integrador.domain.Sociedad;
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
public class DocumentoAnuladoDAOImpl extends HibernateDaoSupport  implements DocumentoAnuladoDAO {
    
    @Autowired
    public DocumentoAnuladoDAOImpl(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public DocumentoAnulado getDocumento(String ruc, String nroSri, String tipoDoc) {
        Sociedad s = (Sociedad) getSession().createCriteria(Sociedad.class).add(Restrictions.eq("ruc", ruc)).list().get(0);
        Criteria criteria = getSession().createCriteria(DocumentoAnulado.class);
        criteria.add(Restrictions.eq("serieCorrelativo", nroSri));
        criteria.add(Restrictions.eq("tipoDocumento", tipoDoc));
        criteria.add(Restrictions.eq("idSociedad", s.getIdSociedad()));
        return (DocumentoAnulado) criteria.list().get(0);
    }
    
}
