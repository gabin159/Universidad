package com.usta.Universidad.models.services;


import com.usta.Universidad.entities.StartupEntity;

import java.util.List;

public interface StartupService {
    List<StartupEntity> findAll();
    StartupEntity findById(Long id);
    List<StartupEntity> findAllWithConvocatoria();
    List<StartupEntity> findByEmprendedor(Long idEmprendedor);
    List<StartupEntity> findByConvocatoria(Long idConvocatoria);
    void save(StartupEntity startup);
    void deleteById(Long id);
    public StartupEntity actualizarstar(StartupEntity startup);
}