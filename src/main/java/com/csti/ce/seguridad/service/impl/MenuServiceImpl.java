package com.csti.ce.seguridad.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csti.ce.seguridad.dao.AccesoDAO;
import com.csti.ce.seguridad.dao.ModuloDAO;
import com.csti.ce.seguridad.dao.OpcionDAO;
import com.csti.ce.seguridad.dao.UsuarioRolDAO;
import com.csti.ce.seguridad.domain.Acceso;
import com.csti.ce.seguridad.domain.Modulo;
import com.csti.ce.seguridad.domain.NodoInteface;
import com.csti.ce.seguridad.domain.Opcion;
import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioRol;
import com.csti.ce.seguridad.service.MenuService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MenuServiceImpl implements MenuService {

    // @Autowired
    // private UsuarioDAO usuarioDAO;
    @Autowired
    private AccesoDAO accesoDAO;
    @Autowired
    private OpcionDAO opcionDAO;
    @Autowired
    private ModuloDAO moduloDAO;
    @Autowired
    UsuarioRolDAO usuarioRolDAO;

    @Override
    @Transactional(readOnly = true)
    public List<NodoInteface> getMenu(Usuario usuario) {
        List<UsuarioRol> listUsuarioRol = usuarioRolDAO.getByUsuario(usuario);
        List<Integer> listIdsRol = new ArrayList<Integer>();
        for (UsuarioRol usuarioRol : listUsuarioRol) {
            listIdsRol.add(usuarioRol.getRol().getIdRol());
        }
        List<Acceso> listAcceso = accesoDAO.getByRols(listIdsRol);
        List<NodoInteface> listNodoInterface = new ArrayList<NodoInteface>();
        Set<Integer> listIdOpcion = new HashSet<Integer>();
        Set<Integer> listIdModulo = new HashSet<Integer>();
        for (Acceso acceso : listAcceso) {
            listIdOpcion.add(acceso.getOpcion().getIdOpcion());
        }
        List<Opcion> listOpcion = opcionDAO.getByIds(listIdOpcion);
        for (Opcion opcion : listOpcion) {
            listIdModulo.add(opcion.getModulo().getIdModulo());
        }
        List<Modulo> listModulo = moduloDAO.getByIds(listIdModulo);
        for (Modulo modulo : listModulo) {
            NodoInteface nodoInterfaceModulo = new NodoInteface();
            nodoInterfaceModulo.setName(modulo.getNombre());
            nodoInterfaceModulo.setText(modulo.getDescripcion());
            nodoInterfaceModulo.setIconCls("name-folder");
            nodoInterfaceModulo.setCls("first-level");
            List<NodoInteface> children = new ArrayList<NodoInteface>();
            for (Opcion opcion : listOpcion) {
                if (modulo.getIdModulo() == opcion.getModulo().getIdModulo()) {
                    NodoInteface nodoInterfaceGui = new NodoInteface();
                    nodoInterfaceGui.setName(opcion.getNombre());
                    nodoInterfaceGui.setText(opcion.getDescripcion());
//                    nodoInterfaceGui.setLeaf(true);
//                    nodoInterfaceGui.setClassInit(opcion.getControlador().trim());
                    nodoInterfaceGui.setPrincipalView(opcion.getVista().trim());
                    for (Acceso acceso : listAcceso) {
                        if (acceso.getOpcion().getIdOpcion() == opcion.getIdOpcion()) {
                            nodoInterfaceGui.setActualizar(acceso.getPermiso().getActualizar());
                            nodoInterfaceGui.setAgregar(acceso.getPermiso().getAgregar());
                            nodoInterfaceGui.setEliminar(acceso.getPermiso().getEliminar());
                            nodoInterfaceGui.setLectura(acceso.getPermiso().getLectura());
                            nodoInterfaceGui.setTodo(acceso.getPermiso().getTodo());
                        }
                    }
                    children.add(nodoInterfaceGui);
                }
            }
            nodoInterfaceModulo.setChildren(children);
            listNodoInterface.add(nodoInterfaceModulo);
        }

        return listNodoInterface;
        // return usuarioDAO.getMenu(usuario);
    }

    @Override
    public Boolean getPermiso(Usuario usuario, String idComponent) {
        return null;
        // return usuarioDAO.getPermiso(usuario, idComponent);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Modulo modulo) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(Modulo modulo) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Modulo modulo) {
        // TODO Auto-generated method stub

    }

    @Override
    public Modulo get(Modulo modulo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Modulo> getAllModulo() {
        // TODO Auto-generated method stub
        return null;
    }

}
