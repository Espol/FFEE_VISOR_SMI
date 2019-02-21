package com.csti.ce.seguridad.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.csti.ce.seguridad.dao.UsuarioAppDAO;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioApp;

@Repository
public class UsuarioAppDAOHibernate extends HibernateDaoSupport implements
        UsuarioAppDAO {

    @Autowired
    public UsuarioAppDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public UsuarioApp verifyUserNameAndPassword(UsuarioApp usuarioApp) {
//        System.out.println("************");
//        System.out.println(usuarioApp.getUserNameApp());
//        System.out.println("************");
//        System.out.println(usuarioApp.getPasswordApp());
        Criteria criteria = getSession().createCriteria(UsuarioApp.class);
        criteria.add(Restrictions.eq("userNameApp", usuarioApp.getUserNameApp()));
        criteria.add(Restrictions.eq("passwordApp", usuarioApp.getPasswordApp()));
        criteria.add(Restrictions.eq("sociedad", usuarioApp.getSociedad() ));
        UsuarioApp usuarioApp2 = (UsuarioApp) criteria.uniqueResult();
        if (usuarioApp2 != null) {
            Criteria criteriaUsuario = getSession().createCriteria(Usuario.class);
            criteriaUsuario.add(Restrictions.eq("idUsuario", usuarioApp2.getIdUsuarioApp()));
            criteriaUsuario.setFetchMode("usuarioRols", FetchMode.JOIN);
            criteriaUsuario.setFetchMode("usuarioSistemas", FetchMode.JOIN);
            criteriaUsuario.setFetchMode("usuarioSeries", FetchMode.JOIN);
            usuarioApp2.setUsuario((Usuario) criteriaUsuario.uniqueResult());
            usuarioApp2.setValido(true);
        }
        return usuarioApp2;
    }
}
