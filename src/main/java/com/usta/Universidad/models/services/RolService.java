package com.usta.Universidad.models.services;

import com.usta.Universidad.entities.RolEntity;

import java.util.List;

public interface RolService {
    List<RolEntity> findAll();
    RolEntity findById(Long id);
    void save(RolEntity rol);
    void deleteById(Long id);
}
