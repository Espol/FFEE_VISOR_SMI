package com.csti.ce.seguridad.dao;

import java.util.List;

import com.csti.ce.seguridad.domain.Sistema;

public interface SistemaDAO {
	Sistema findById(int id);
	List<Sistema> findAll();
	void save(Sistema entity);
	void update(Sistema entity);
	void delete(Sistema entity);
	void deleteById(int id);
}
