package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Rol;

public interface RolDAO {
	Rol getById(int id);
	List<Rol> getAll();
	void save(Rol entity);
	void update(Rol entity);
	void delete(Rol entity);
	void deleteById(int id);
}
