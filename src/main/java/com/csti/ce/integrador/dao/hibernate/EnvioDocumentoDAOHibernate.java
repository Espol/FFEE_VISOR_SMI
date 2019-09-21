/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.csti.ce.integrador.dao.hibernate;

import com.csti.ce.integrador.dao.EnvioDocumentoDAO;
import com.csti.ce.integrador.domain.Documento;
import com.csti.ce.integrador.domain.DocumentoPendiente;
import com.csti.ce.integrador.domain.EnvioDocumento;
import com.csti.ce.integrador.domain.Sociedad;
import com.csti.ce.validacion.Offline;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
public class EnvioDocumentoDAOHibernate extends HibernateDaoSupport implements EnvioDocumentoDAO {
    
    @Autowired
    public EnvioDocumentoDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void updateEstado(int id) throws Exception {
        String sql = "UPDATE EnvioDocumento SET estado ='PE' WHERE idEnvio ="+id;
        Query query = getSession().createQuery(sql);
        query.executeUpdate();
    }

    @Override
    public EnvioDocumento getEnviodocumento(String ruc, String nroSri, String tipoDoc) throws Exception {
        
        Sociedad s = (Sociedad) getSession().createCriteria(Sociedad.class).add(Restrictions.eq("ruc", ruc)).list().get(0);
        
        Criteria criteria = getSession().createCriteria(DocumentoPendiente.class);
        criteria.add(Restrictions.eq("serieCorrelativo", nroSri));
        criteria.add(Restrictions.eq("tipoDocumento", tipoDoc));
        criteria.add(Restrictions.eq("idSociedad", s.getIdSociedad()));
        DocumentoPendiente doc = (DocumentoPendiente) criteria.list().get(0);
        
        Criteria cri = getSession().createCriteria(EnvioDocumento.class);
        cri.add(Restrictions.eq("secuencia", doc.getSecuencia()));
        if(Offline.estaAutorizado(doc.getClaveAcceso())){
            ArrayList<String> lst = new ArrayList<>();
            lst.add("EP");
            lst.add("EC");
            cri.add(Restrictions.in("estado", lst));
        } else {
            cri.add(Restrictions.eq("destino", "SR"));
        }
        List<EnvioDocumento> list = cri.list();
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<EnvioDocumento> getEnvioDocumentoRechazado(String ruc, String nroSri, String tipoDoc) throws Exception {
        
        Sociedad s = (Sociedad) getSession().createCriteria(Sociedad.class).add(Restrictions.eq("ruc", ruc)).list().get(0);
        
        Criteria criteria = getSession().createCriteria(Documento.class);
        criteria.add(Restrictions.eq("serieCorrelativo", nroSri));
        criteria.add(Restrictions.eq("tipoDocumento", tipoDoc));
        criteria.add(Restrictions.eq("idSociedad", s.getIdSociedad()));
        Documento doc = (Documento) criteria.list().get(0);
        
        Criteria cri = getSession().createCriteria(EnvioDocumento.class);
        cri.add(Restrictions.eq("secuencia", doc.getSecuencia()));
        return cri.list();
    }

    @Override
    public void save(EnvioDocumento entity) {
        getHibernateTemplate().save(entity);
    }
}
