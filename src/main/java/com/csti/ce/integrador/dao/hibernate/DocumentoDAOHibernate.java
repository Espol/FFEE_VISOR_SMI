package com.csti.ce.integrador.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.integrador.dao.DocumentoDAO;
import com.csti.ce.integrador.domain.Documento;
import com.csti.ce.integrador.domain.Sociedad;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Repository
public class DocumentoDAOHibernate extends HibernateDaoSupport implements DocumentoDAO {

    @Autowired
    public DocumentoDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void updateDocumento(String nroSri, String tipoDocumento, int estadoNotifRech) {
        String sql = "UPDATE Documento SET estadoNotifRechazado = :estadoNotifcacion "
                + "WHERE serieCorrelativo= :nroSri and tipoDocumento= :tipoDocumento";
        Query query = getSession().createQuery(sql);
        query.setParameter("estadoNotifcacion", estadoNotifRech);
        query.setParameter("nroSri", nroSri);
        query.setParameter("tipoDocumento", tipoDocumento);
        query.executeUpdate();

    }

    @Override
    public Documento getDocumento(String ruc, String nroSri, String tipoDoc) {
        Sociedad s = (Sociedad) getSession().createCriteria(Sociedad.class).add(Restrictions.eq("ruc", ruc)).list().get(0);
        Criteria criteria = getSession().createCriteria(Documento.class);
        criteria.add(Restrictions.eq("serieCorrelativo", nroSri));
        criteria.add(Restrictions.eq("tipoDocumento", tipoDoc));
        criteria.add(Restrictions.eq("idSociedad", s.getIdSociedad()));
        criteria.add(Restrictions.eq("ultimo", true));
        return (Documento) criteria.list().get(0);
    }

    @Override
    public void updateDocumentoRechazado(int idPeriodo, int idSociedad, int secuencia) {
        String sql = "UPDATE Documento SET estadoSri = :estadoSri "
                + "WHERE idPeriodo= :idPeriodo and idSociedad= :idSociedad and secuencia= :secuencia and ultimo=TRUE";
        Query query = getSession().createQuery(sql);
        query.setParameter("estadoSri", "RC");
        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idSociedad", idSociedad);
        query.setParameter("secuencia", secuencia);
        query.executeUpdate();
    }
}
