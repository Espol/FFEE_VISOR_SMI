package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;

public interface UsuarioRolDAO {
	UsuarioRol getById(int id);
	List<UsuarioRol> getByUsuario(Usuario usuario);
	List<UsuarioRol> getAll();
	
    List<UsuarioRol> getAllBySociedad(String ruc);
    List<UsuarioRol> getAllBySociedadPagination(int initRow, int rows, String ruc );
	
	void save(UsuarioRol entity);
	
	int updateByAdministrador(UsuarioRol usuarioRol);
	void update(UsuarioRol usuarioRol);	
	
	void saveOrUpdate(UsuarioRol entity);
	void delete(UsuarioRol entity);
	int deleteByAdministrador(UsuarioRol usuarioRol);
	
	void deleteById(int id);
	int countByUsuario(int id);
	boolean existUsuarioRol(UsuarioRol usuarioRol);
        UsuarioRol getUsuarioRolByIdUsuario(int idUsuario);
}
