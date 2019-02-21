package com.csti.ce.seguridad.dao;

import java.util.List;
import java.util.Set;

import com.csti.ce.seguridad.domain.Modulo;
import com.csti.ce.seguridad.domain.Opcion;

public interface ModuloDAO {
	Modulo getById(int id);
	List<Modulo> getByIds(Set<Integer> ids);
	List<Modulo> getAll();
	void save(Modulo entity);
	void update(Modulo entity);
	void delete(Modulo entity);
	void deleteById(int id);
}
