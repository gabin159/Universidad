package com.usta.Universidad.models.DAO;

import com.usta.Universidad.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioDAO extends CrudRepository<UsuarioEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE UsuarioEntity SET clave = ?2 WHERE idUsuario = ?1")
    void changePassword(Long idUsuario, String nuevaContrasena);

    @Transactional
    @Query("SELECT u FROM UsuarioEntity u WHERE u.email = ?1")
    UsuarioEntity findByEmail(String correo);
}
