package com.csti.ce.seguridad.service;

import com.csti.ce.seguridad.domain.UsuarioApp;

public interface LoginService {

	UsuarioApp verifyUserNameAndPassword(UsuarioApp $usuarioAValidad);
}
