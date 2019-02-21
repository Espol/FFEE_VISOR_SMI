package com.csti.ce.seguridad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.RolDAO;
import com.csti.ce.seguridad.dao.UsuarioRolDAO;
import com.csti.ce.seguridad.domain.Rol;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;
import com.csti.ce.seguridad.service.UsuarioRolService;
import com.csti.ce.util.Response;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class UsuarioRolServiceImpl implements UsuarioRolService {
	@Autowired
	UsuarioRolDAO usuarioRolDAO;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response save(UsuarioRol entity) {
		Response respuesta = new Response();
		if(usuarioRolDAO.existUsuarioRol(entity)){
			respuesta.setMessage("Ya Existe un registro con el mismo Rol y Usuario indicado.");
		}else{
			usuarioRolDAO.save(entity);
			respuesta.messageINSERT();
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response update(UsuarioRol entity) {
		Response respuesta = new Response();
		usuarioRolDAO.update(entity);
		respuesta.messageUPDATE();
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response updateByAdministrador(UsuarioRol usuarioRol) {
		Response respuesta = new Response();
		int success = usuarioRolDAO.updateByAdministrador(usuarioRol);
		respuesta.messageUPDATE();
		
		if(success==0){
			respuesta.setSuccess(false);
			respuesta.setMessage("Error, el usuario ya existe");// se modificó el nombre de usuario, pero ya existía
		}
		
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response delete(UsuarioRol entity) {
		Response respuesta = new Response();
		// if (usuarioRolDAO.countByUsuario(entity.getIdUsuarioRol()) == 0) {
		usuarioRolDAO.delete(entity);
		respuesta.messageDELETE();
		// } else {
		// respuesta.setMessage("La Entidad Esta Relacionada.");
		// }
		return respuesta;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Response deleteByAdministrador(UsuarioRol usuarioRol) {
		Response respuesta = new Response();
		int success = usuarioRolDAO.deleteByAdministrador(usuarioRol);
		respuesta.messageDELETE();
		
		if(success==0){
			respuesta.setSuccess(false);
			respuesta.setMessage("Error, el usuario no existe");
		}
		
		return respuesta;
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioRol get(UsuarioRol entity) {
		return usuarioRolDAO.getById(entity.getIdUsuarioRol());
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioRol> getAll() {
		return usuarioRolDAO.getAll();
	}
	
    @Override
    public List<UsuarioRol> getAllBySociedad(String ruc) {
        return usuarioRolDAO.getAllBySociedad(ruc);
    }
	
    @Override
    public List<UsuarioRol> getAllBySociedadPagination(int initRow, int rows, String ruc ) {
        return usuarioRolDAO.getAllBySociedadPagination(initRow, rows, ruc);
    }
    
    @Override
    public UsuarioRol getUsuarioRolByIdUsuario(int IdUsuario) {
        return (UsuarioRol) usuarioRolDAO.getUsuarioRolByIdUsuario(IdUsuario);
    }
        
        

}
