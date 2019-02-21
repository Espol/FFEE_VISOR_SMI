package com.csti.ce.seguridad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.UsuarioAppDAO;
import com.csti.ce.seguridad.dao.UsuarioDAO;
import com.csti.ce.seguridad.domain.UsuarioApp;
import com.csti.ce.seguridad.service.LoginService;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UsuarioAppDAO usuarioAppDAO;

    @Override
    @Transactional(readOnly = true)
    public UsuarioApp verifyUserNameAndPassword(UsuarioApp usuario) {
        UsuarioApp userValid = usuarioAppDAO.verifyUserNameAndPassword(usuario);
        if (userValid == null) {
            System.out.println("Usuario no Existe");
            throw new RuntimeException("Usuario No existe");
        } else if (!userValid.getPasswordApp().equals(usuario.getPasswordApp())) {
            System.out.println("Usuario o contraseña incorrectos");
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
//        for (Iterator it = usuario.getUsuarioSistemas().iterator(); it.hasNext();) {
//            UsuarioSistema object = (UsuarioSistema) it.next();
//            if (!object.getSistema().getIdentificador().equals(AplicacionConstants.IDENTIFICADOR_MONITOR)) {
//                respuesta.setMessage("No se logra identificar el Sistema en la configuración.");
//                break;
//            } else if (!object.getSistema().getAmbiente().equals(ambiente)) {
//                respuesta.setMessage("El ambiente no esta configurado.");
//                break;
//            } else {
//                try {
//                    AplicacionUtil.evaluarSociedad(sociedad);
//                    respuesta.setSuccess(true);
//                } catch (Exception ex) {
//                    respuesta.setMessage("Sociedad Inválida: " + ex.getMessage());
//                    break;
//                }
//            }
//        }

        System.out.println("FIN");
        return userValid;
    }

}
