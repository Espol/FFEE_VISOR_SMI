package com.csti.ce.seguridad.service;

import java.util.List;

import com.csti.ce.seguridad.domain.Modulo;
import com.csti.ce.seguridad.domain.NodoInteface;
import com.csti.ce.seguridad.domain.Usuario;

public interface MenuService {

    List<NodoInteface> getMenu(Usuario usuario);

    Boolean getPermiso(Usuario usuario, String idComponent);

    /**
     * persiste o graba cada modulo
     *
     * @param modulo
     */
    void save(Modulo modulo);

    /**
     * actualiza cada modulo
     *
     * @param modulo
     */
    void update(Modulo modulo);

    /**
     * elimina o da de baja un modulo
     *
     * @param modulo
     */
    void delete(Modulo modulo);

    /**
     * obtiene un modulo para editar o algo especifico
     *
     * @param modulo
     * @return
     */
    Modulo get(Modulo modulo);

    /**
     * listado de todos los modulos
     *
     * @return
     */
    List<Modulo> getAllModulo();

}
