/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao.hibernate;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.constant.AplicacionConstants;
import com.csti.ce.monitor.dao.ComprobanteDAO;
import com.csti.ce.monitor.domain.Comprobante;

/**
 *
 * @author Usuario
 */
@Repository
public class ComprobanteDAOHibernate extends HibernateDaoSupport implements ComprobanteDAO {

//    String ambiente;
    @Autowired
    public ComprobanteDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
//
//    @Override
//    public void setAmbiente(String ambiente) {
//        this.ambiente = ambiente;
//    }

    @Override
    public void save(Comprobante entity) {
        getHibernateTemplate().save(entity);
    }

    @Override
    public void update(Comprobante entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void delete(Comprobante entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public Comprobante getByID(long id, String ruc) {
        Criteria criteria = getSession().createCriteria(Comprobante.class);
        criteria.add(Restrictions.eq("idComprobante", id));
        criteria.add(Restrictions.eq("ruc", ruc));
        return (Comprobante) criteria.uniqueResult();
    }

    @Override
    public Comprobante getByUltimo(long id, String ruc) {
        Criteria criteria = getSession().createCriteria(Comprobante.class);
        criteria.add(Restrictions.eq("idComprobante", id));
        criteria.add(Restrictions.eq("ruc", ruc));
        criteria.add(Restrictions.eq("ultimo", AplicacionConstants.SUCCESS));
        return (Comprobante) criteria.uniqueResult();
    }

    @Override
    public Comprobante getByUltimo(String identificador,String tipoDoc, String ruc) {
        Criteria criteria = getSession().createCriteria(Comprobante.class);
        criteria.add(Restrictions.eq("tipoDoc", tipoDoc));
        criteria.add(Restrictions.eq("nroSri", identificador));
        criteria.add(Restrictions.eq("ruc", ruc));
        criteria.add(Restrictions.eq("ultimo", AplicacionConstants.SUCCESS));
        return (Comprobante) criteria.uniqueResult();
    }

    @Override
    public List<Comprobante> getByEscenario(String ruc, String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaDesde,
            Timestamp fechaHasta,
            int initRow,
            int countRow,
            List<Integer> escenarios,
            int ultimo,
            int anulado,
            int offline,
            String codInterlocutor) {
        Criteria criteria = getSession().createCriteria(Comprobante.class);
        criteria.add(Restrictions.eq("ruc", ruc));
        criteria.add(Restrictions.eq("tipoDoc", tipoDoc));
        criteria.add(Restrictions.in("escenario", escenarios));
        
//        criteria.add(Restrictions.like("nroSri", nroSri, MatchMode.START));
//        criteria.add(Restrictions.like("docReferencia", docReferencia, MatchMode.START));
//        criteria.add(Restrictions.like("interlocutor", codInterlocutor, MatchMode.START));        
//        criteria.add(Restrictions.ge("fechaRegistro", fechaDesde));
//        criteria.add(Restrictions.le("fechaRegistro", fechaHasta));
        
        
        if( nroSri != null && !nroSri.isEmpty() ){
            criteria.addOrder(Order.desc("nroSri"));
            criteria.add(Restrictions.like("nroSri", nroSri, MatchMode.START));        	
        }
        
        if( docReferencia != null && !docReferencia.isEmpty() )
        	criteria.add(Restrictions.like("docReferencia", docReferencia, MatchMode.START));

        if( codInterlocutor != null && !codInterlocutor.isEmpty() )
        	criteria.add(Restrictions.like("interlocutor", codInterlocutor, MatchMode.START));
        
        if( fechaDesde != null && fechaHasta != null )
	        criteria.add(
	        	Restrictions.and(
	        		Restrictions.ge("fechaRegistro",fechaDesde)
	        		,Restrictions.le("fechaRegistro", fechaHasta) 
	        	)
	        );
        
        
        if(ultimo==1){
            criteria.add(Restrictions.eq("ultimo", ultimo));
        }
        
        // By: Ammiel Pajuelo 
        //     Filtrar comprobantes Anulados
        if (anulado == 1) {
            criteria.add(Restrictions.eq("anulado", anulado));
        }
        criteria.add(Restrictions.eq("offline", offline));//esquema = offline
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        criteria.setFirstResult(initRow);
        criteria.setMaxResults(countRow);
        
        
        criteria.addOrder(Order.desc("nroSri"));
        
        return criteria.list();
    }

    @Override
    public Integer getCount(String ruc,
            String tipoDoc,
            String nroSri,
            String docReferencia,
            Timestamp fechaDesde,
            Timestamp fechaHasta,
            List<Integer> escenarios,
            int ultimo,
            int anulado,
            int offline,
            String codInterlocutor) {
        Criteria criteria = getSession().createCriteria(Comprobante.class);
        
        criteria.add(Restrictions.eq("ruc", ruc));
        criteria.add(Restrictions.eq("tipoDoc", tipoDoc));
        criteria.add(Restrictions.in("escenario", escenarios));
        
//        criteria.add(Restrictions.like("nroSri", nroSri, MatchMode.START));
//        criteria.add(Restrictions.like("docReferencia", docReferencia, MatchMode.START));
//        criteria.add(Restrictions.like("interlocutor", codInterlocutor, MatchMode.START));
//        criteria.add(Restrictions.ge("fechaRegistro", fechaDesde));
//        criteria.add(Restrictions.le("fechaRegistro", fechaHasta));
        
        
        if( nroSri != null && !nroSri.isEmpty() )
        	criteria.add(Restrictions.like("nroSri", nroSri, MatchMode.START));
        
        if( docReferencia != null && !docReferencia.isEmpty() )
        criteria.add(Restrictions.like("docReferencia", docReferencia, MatchMode.START));

        if( codInterlocutor != null && !codInterlocutor.isEmpty() )
        criteria.add(Restrictions.like("interlocutor", codInterlocutor, MatchMode.START));
        
        if( fechaDesde != null && fechaHasta != null )
	        criteria.add(
	        	Restrictions.and(
	        		Restrictions.ge("fechaRegistro",fechaDesde)
	        		,Restrictions.le("fechaRegistro", fechaHasta) 
	        	)
	        );
        
        if(ultimo==1){
            criteria.add(Restrictions.eq("ultimo", ultimo));
        }
        criteria.add(Restrictions.eq("offline", offline));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }
     @Override
    public Integer getNroRegistros(String identificador,String tipoDoc, String estado) {
        Criteria criteria=getSession().createCriteria(Comprobante.class);
        criteria.add(Restrictions.eq("nroSri", identificador));
        criteria.add(Restrictions.eq("tipoDoc",tipoDoc));
        criteria.add(Restrictions.eq("estado",estado));        
        criteria.setProjection(Projections.rowCount());
        List list=criteria.list();
        Integer count=(Integer) list.get(0);
        return count;
   }
    
    @Override
    public Comprobante getComprobateRechazadoClaveAccesoRechazado(String ruc, String nroSri, String tipoDoc) {
        try {
            Criteria criteria = getSession().createCriteria(Comprobante.class);
            criteria.add(Restrictions.eq("ruc", ruc));
            criteria.add(Restrictions.eq("nroSri", nroSri));
            criteria.add(Restrictions.eq("tipoDoc", tipoDoc));
            criteria.add(Restrictions.eq("ultimo", 1));
            criteria.add(Restrictions.eq("estado", "INCONSISTENTE"));
            return (Comprobante) criteria.list().get(0);
        } catch (Exception e) {
            return null;
        }

    }
}
