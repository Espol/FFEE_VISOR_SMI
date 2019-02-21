package com.csti.ce.seguridad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csti.ce.seguridad.dao.UsuarioAppDAO;
import com.csti.ce.seguridad.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private UsuarioAppDAO usuarioDAO;
}
