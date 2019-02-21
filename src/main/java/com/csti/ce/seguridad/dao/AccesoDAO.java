package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.seguridad.domain.UsuarioRol;

public interface AccesoDAO {
	Acceso getById(int id);
	
	List<Acceso> getAllByPagination(int initRow, int countRow);
	
	List<Acceso> getAll();

	void save(Acceso entity);

	void update(Acceso entity);

	void delete(Acceso entity);

	void deleteById(int id);

	List<Acceso> getByRol(int id);

	List<Acceso> getByRols(List<Integer> idRols);
	int countByRol(int id) ;
	int countByOpcion(int id);
	boolean existAcceso(Acceso acceso);
}
