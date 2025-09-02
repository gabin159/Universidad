package com.usta.Universidad.models.DAO;


import com.usta.Universidad.entities.ConvocatoriaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConvocatoriaDAO extends CrudRepository<ConvocatoriaEntity, Long> {

    @Transactional
    @Query("SELECT c FROM ConvocatoriaEntity c WHERE CURRENT_DATE BETWEEN c.fechaIni AND c.fechaFin")
    List<ConvocatoriaEntity> findConvocatoriasActivas();

    @Query("SELECT c FROM ConvocatoriaEntity c WHERE c.idConvocatorias = :id")
    List<ConvocatoriaEntity> findByCon(@Param("id") Long id);
}
