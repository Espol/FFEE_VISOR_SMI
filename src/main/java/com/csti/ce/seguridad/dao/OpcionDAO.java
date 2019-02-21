package com.csti.ce.seguridad.dao;

import java.util.List;
import java.util.Set;

import com.csti.ce.seguridad.domain.Opcion;

public interface OpcionDAO {
	Opcion getById(int id);
	public List<Opcion> getByIds(Set<Integer> ids);
	List<Opcion> getAll();
	List<Opcion> getByQuery(String value);
	void save(Opcion entity);
	void update(Opcion entity);
	void delete(Opcion entity);
	void deleteById(int id);
	List<Opcion> getByModulo(int id);
	int countByModulo(int id);
}
