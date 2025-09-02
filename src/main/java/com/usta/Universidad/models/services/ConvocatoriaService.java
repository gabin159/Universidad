package com.usta.Universidad.models.services;


import com.usta.Universidad.entities.ConvocatoriaEntity;

import java.util.List;

public interface ConvocatoriaService {
    List<ConvocatoriaEntity> findAll();
    List<ConvocatoriaEntity> findByIdCon(Long id); // Cambiado aquí
    ConvocatoriaEntity findById(Long id);
    List<ConvocatoriaEntity> findActivas();
    void save(ConvocatoriaEntity convocatoria);
    void deleteById(Long id);
}