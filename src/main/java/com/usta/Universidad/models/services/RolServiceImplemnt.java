package com.usta.Universidad.models.services;

import com.usta.Universidad.entities.RolEntity;
import com.usta.Universidad.models.DAO.RolDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplemnt implements RolService{

    @Autowired
    private RolDAO rolDAO;

    @Override
    public List<RolEntity> findAll() {
        return (List<RolEntity>) rolDAO.findAll();
    }

    @Override
    public RolEntity findById(Long id) {
        Optional<RolEntity> r = rolDAO.findById(id);
        return r.orElse(null);
    }

    @Override
    public void save(RolEntity rol) {
        rolDAO.save(rol);
    }

    @Override
    public void deleteById(Long id) {
        rolDAO.deleteById(id);

    }
}