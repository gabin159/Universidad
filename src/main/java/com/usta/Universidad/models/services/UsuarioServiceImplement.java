package com.usta.Universidad.models.services;



import com.usta.Universidad.entities.UsuarioEntity;
import com.usta.Universidad.models.DAO.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplement implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) usuarioDAO.findAll();
    }

    @Override
    public UsuarioEntity findById(Long id) {
        Optional<UsuarioEntity> u = usuarioDAO.findById(id);
        return u.orElse(null);
    }

    @Override
    public UsuarioEntity findByCorreo(String correo) {
        return usuarioDAO.findByEmail(correo);
    }

    @Override
    public void save(UsuarioEntity usuario) {
        usuarioDAO.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void changePassword(Long id, String nuevaContrasena) {
        usuarioDAO.changePassword(id, nuevaContrasena);
    }
}



