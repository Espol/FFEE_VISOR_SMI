package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Permiso;


public interface PermisoDAO {
//	Permiso getById(int id);
	List<Permiso> getAll();
	void save(Permiso entity);
	void update(Permiso entity);
//	void saveOrUpdate(Permiso entity);
	void delete(Permiso entity);
	void deleteById(int id);
}
