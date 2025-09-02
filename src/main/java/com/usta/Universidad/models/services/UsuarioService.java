package com.usta.Universidad.models.services;

// UsuarioService.java


import com.usta.Universidad.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    public  List<UsuarioEntity>findAll();
    UsuarioEntity findById(Long id);
    UsuarioEntity findByCorreo(String correo);
    void save(UsuarioEntity usuario);
    void deleteById(Long id);
    void changePassword(Long id, String nuevaContrasena);
}
