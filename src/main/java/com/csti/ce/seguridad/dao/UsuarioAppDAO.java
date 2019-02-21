package com.csti.ce.seguridad.dao;

import com.csti.ce.seguridad.domain.UsuarioApp;

public interface UsuarioAppDAO {

	UsuarioApp verifyUserNameAndPassword(UsuarioApp usuario);

}
