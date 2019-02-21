/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.monitor.dao.hibernate;

import com.csti.ce.monitor.dao.ClaveDAO;
import com.csti.ce.monitor.domain.Clave;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

 

/**
 *
 * @author Usuario
 */
@Repository
public class ClaveDAOHibernate extends HibernateDaoSupport implements ClaveDAO {

    @Autowired
    public ClaveDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void setAmbiente(String ambiente) {
    }

    @Override
    public String getAmbiente() {
        return "124";
    }

    @Override
    public Clave getAleatorio(String ruc, String ambiente) throws Exception {
        return null;
    }

    @Override
    public boolean registrarClaveUtilizada(long idClave) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean guardarClave(Clave clave) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumeroClavesDisponibles(String ruc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumeroClavesTotal(String ruc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
