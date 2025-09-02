package com.usta.Universidad.models.DAO;

import com.usta.Universidad.entities.StartupEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StartupDAO extends CrudRepository<StartupEntity, Long> {

    @Transactional
    @Query("SELECT s FROM StartupEntity s WHERE s.usuario = ?1")
    List<StartupEntity> findByUsuario(Long idEmprendedor);

    @Transactional
    @Query("SELECT s FROM StartupEntity s WHERE s.convocatoria = ?1")
    List<StartupEntity> findByConvocatoria(Long idConvocatoria);

    @Query("SELECT s FROM StartupEntity s JOIN FETCH s.convocatoria")
    List<StartupEntity> findAllWithConvocatoria();
}
