package com.csti.ce.seguridad.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.UsuarioRolDAO;
import com.csti.ce.seguridad.domain.Rol;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;

@Transactional
@Repository
public class UsuarioRolDAOHibernate extends HibernateDaoSupport implements
        UsuarioRolDAO {
	
    @Autowired
    public UsuarioRolDAOHibernate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public UsuarioRol getById(int id) {
        return (UsuarioRol) getHibernateTemplate().get(UsuarioRol.class, id);
    }
    
    @Override
    public List<UsuarioRol> getByUsuario(Usuario usuario) {
        return getSession()
                .createCriteria(UsuarioRol.class)
                .add(Restrictions.eq("usuario.idUsuario",
                                usuario.getIdUsuario())).list();
    }
    
    @Override
    public void save(UsuarioRol usuarioRol) {
    	
    	Usuario usuario = usuarioRol.getUsuario();
    	Rol rol = usuarioRol.getRol();

        getHibernateTemplate().saveOrUpdate(usuarioRol.getUsuario());
//        getHibernateTemplate().saveOrUpdate(usuarioRol.getRol());
    	
        getHibernateTemplate().saveOrUpdate(usuarioRol);
    }

    @Override
    public int updateByAdministrador(UsuarioRol usuarioRol) {
    	int rowsModified=0;
    	
    	try{
        	String updateUsuario = "update Usuario u "
				        			+ "set u.userName = :userName "
				        			+ ",u.userPassword = :password "
				        			+ ",u.activo = :activo "
				        			+ "where u.idUsuario = :idUsuario";// and u.sociedad = :sociedad";
        	
    		rowsModified = this.getSession().createQuery( updateUsuario )
    	        .setInteger( "idUsuario", usuarioRol.getUsuario().getIdUsuario() )
    	        .setString( "userName", usuarioRol.getUsuario().getUserName() )
    	        .setString( "password", usuarioRol.getUsuario().getUserPassword() )
    	        .setString( "activo", usuarioRol.getUsuario().getActivo() + "" )
//    	        .setString( "sociedad", usuarioRol.getUsuario().getSociedad() )
    	        .executeUpdate();
    		
    		if(rowsModified==0)
    			return 0;
    		
    		String updateUsuarioRol = "update UsuarioRol ur set ur.rol.idRol = :idRol where ur.idUsuarioRol = :idUsuarioRol";
    		
    		rowsModified = this.getSession().createQuery( updateUsuarioRol )
    	        .setInteger( "idRol", usuarioRol.getRol().getIdRol() )
    	        .setInteger( "idUsuarioRol", usuarioRol.getIdUsuarioRol() )
    	        .executeUpdate();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
        return rowsModified;
    }

    @Override
    public void saveOrUpdate(UsuarioRol entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void delete(UsuarioRol entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public int deleteByAdministrador(UsuarioRol usuarioRol) {
    	int rowsDelete=0;
    	
    	try{
    		String deleteUsuarioRol = "delete from UsuarioRol ur "
    								+ "	where ur.idUsuarioRol = :idUsuarioRol";
    		
    		rowsDelete = this.getSession().createQuery( deleteUsuarioRol )
    	        .setInteger( "idUsuarioRol", usuarioRol.getIdUsuarioRol() )
    	        .executeUpdate();
    		
    		if(rowsDelete==0)
    			return 0;
    		
        	String deleteUsuario = "delete from Usuario u "
				        			+ "where u.idUsuario = :idUsuario"
				        			+ " and u.sociedad = :sociedad";
        	
        	rowsDelete = this.getSession().createQuery( deleteUsuario )
    	        .setInteger( "idUsuario", usuarioRol.getUsuario().getIdUsuario() )
    	        .setString( "sociedad", usuarioRol.getUsuario().getSociedad() )
    	        .executeUpdate();
    		
    	}catch(Exception e){
    		rowsDelete = 0;
    		e.printStackTrace();
    	}
    	
        return rowsDelete;
    }

    @Override
    public void deleteById(int id) {
        // TODO Auto-generated method stub

    }

    private String getIds(List<Integer> ids) {
        String resultado = "'0'";
        for (Integer integer : ids) {
            resultado += ",'" + integer + "'";
        }
        return resultado;
    }

    @Override
    public int countByUsuario(int id) {
        Criteria criteria = getSession().createCriteria(UsuarioRol.class);
        criteria.add(Restrictions.eq("usuario.idUsuario", id));
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.list().get(0);
    }

    @Override
    public List<UsuarioRol> getAll() {
        return getSession().createCriteria(UsuarioRol.class).list();
    }
    

    @Override
    public List<UsuarioRol> getAllBySociedad(String ruc){
        Criteria criteria = getSession().createCriteria(UsuarioRol.class);
//        criteria.createAlias("usuarioRol", "ur");
        criteria.createAlias("usuario", "u");
        criteria.add(Restrictions.eq("u.sociedad", ruc));
        return criteria.list();
    }
    
    @Override
    public List<UsuarioRol> getAllBySociedadPagination(int initRow, int rows, String ruc ){
        Criteria criteria = getSession().createCriteria(UsuarioRol.class);
        criteria.createAlias("usuario", "u");
        criteria.add(Restrictions.eq("u.sociedad", ruc));
        
        criteria.setFirstResult(initRow);
        criteria.setMaxResults(rows);
        
        return criteria.list();
    }
    
    @Override
    public boolean existUsuarioRol(UsuarioRol usuarioRol) {
        Criteria criteria = getSession().createCriteria(UsuarioRol.class);
        criteria.createAlias("usuario", "u");
        criteria.add( Restrictions.eq("u.userName", usuarioRol.getUsuario().getUserName()) );
        criteria.add( Restrictions.eq("u.sociedad", usuarioRol.getUsuario().getSociedad()) );
//        criteria.setProjection(Projections.rowCount());
        
        return !criteria.list().isEmpty();
    }
    
    @Override
    public UsuarioRol getUsuarioRolByIdUsuario(int idUsuario) {
        Criteria criteria = getSession().createCriteria(UsuarioRol.class);
        criteria.add(Restrictions.eq("usuario.idUsuario", idUsuario));
        return (UsuarioRol) criteria.list().get(0);
    }

	@Override
	public void update(UsuarioRol usuarioRol) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(usuarioRol);
	}

}
