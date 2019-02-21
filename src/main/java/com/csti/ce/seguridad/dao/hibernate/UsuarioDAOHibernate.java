package com.csti.ce.seguridad.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.UsuarioDAO;
import com.csti.ce.seguridad.domain.Opcion;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;

@Transactional
@Repository
public class UsuarioDAOHibernate extends HibernateDaoSupport implements UsuarioDAO {
	@Autowired
	public UsuarioDAOHibernate(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

//	@Override
//	public List<NodoInteface> getMenu(Usuario usuario) {
//		Iterator<UsuarioRol> usuarioRols = usuario.getUsuarioRols().iterator();
//		List<Integer> idRols = new ArrayList<Integer>();
//		while(usuarioRols.hasNext()){
//			UsuarioRol usuarioRol=usuarioRols.next();
//			idRols.add(usuarioRol.getRol().getIdRol());
//		}
//		System.out.println("count Rol:"+idRols.size());
//		return null;
//	}
	
	public List<Opcion> getOpciones(List<Integer> idRols){
		String ids=this.getIds(idRols);
		System.err.println("Opciones:");
		String sql = "select acc.opcion.idOpcion,acc.opcion.codigo,acc.opcion.nombre," +
				"acc.opcion.descripcion,acc.opcion.icono,acc.opcion.url," +
				"acc.opcion.esDirectorio,acc.opcion.file," +
				"acc.opcion.modulo.idModulo,acc.opcion.modulo.codigo," +
				"acc.opcion.modulo.nombre,acc.opcion.modulo.descripcion,acc.opcion.modulo.icono " +
				"from Acceso acc " +
				"where acc.rol.idRol in ("+ids+") and acc.rol.estado='1' and acc.opcion.modulo.estado='1' and acc.opcion.estado='1'" +
				"group by acc.opcion.idOpcion,acc.opcion.codigo,acc.opcion.nombre," +
				"acc.opcion.descripcion,acc.opcion.icono,acc.opcion.url," +
				"acc.opcion.esDirectorio,acc.opcion.file," +
				"acc.opcion.modulo.idModulo,acc.opcion.modulo.codigo," +
				"acc.opcion.modulo.nombre,acc.opcion.modulo.descripcion,acc.opcion.modulo.icono";
		Query query = getSession().createQuery(sql);
		List<Opcion> Opcion=query.list();
		return Opcion;
	}

	@Override
	public Boolean getPermiso(Usuario usuario, String idComponent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getById(int id) {
		System.out.println("************");
		System.out.println("USUARIO findById");
		System.out.println("************");
		Criteria criteria = getSession().createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("idUsuario", id));
		return (Usuario) criteria.uniqueResult();
	}
	

	@Override
	public List<Usuario> getAllBySociedad(String ruc) {
		return getSession().createCriteria(Usuario.class).list();
	}

	@Override
	public List<Usuario> getAll() {
		return getSession().createCriteria(Usuario.class).list();
	}
	@Override
	public List<Usuario> getByQuery(String value) {
		return (List<Usuario>)getSession().createCriteria(Usuario.class).add(Restrictions.like("userName",value,MatchMode.ANYWHERE).ignoreCase()).list();
	}
	@Override
	public void save(Usuario entity) {
		getHibernateTemplate().save(entity);
	}

	@Override
	public void update(Usuario entity) {
		getHibernateTemplate().update(entity);		
	}

	@Override
	public void delete(Usuario entity) {
		getHibernateTemplate().delete(entity);		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	private String getIds(List<Integer> ids){
		String resultado ="'0'";
		for (Integer integer : ids) {
			resultado+=",'"+integer+"'";
		}
		return resultado;
	}
}
