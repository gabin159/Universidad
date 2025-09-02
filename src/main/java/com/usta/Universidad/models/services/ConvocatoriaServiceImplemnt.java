package com.usta.Universidad.models.services;

import com.usta.Universidad.entities.ConvocatoriaEntity;
import com.usta.Universidad.models.DAO.ConvocatoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConvocatoriaServiceImplemnt implements ConvocatoriaService {
    @Autowired
    private ConvocatoriaDAO convocatoriaDAO;

    @Override
    public List<ConvocatoriaEntity> findAll() {
        return (List<ConvocatoriaEntity>) convocatoriaDAO.findAll();
    }

    @Override
    public List<ConvocatoriaEntity> findByIdCon(Long idConvocatorias) {
        return convocatoriaDAO.findByCon(idConvocatorias); // Solo pasamos el ID
    }

    @Override
    public ConvocatoriaEntity findById(Long id) {
        Optional<ConvocatoriaEntity> c = convocatoriaDAO.findById(id);
        return c.orElse(null);
    }

    @Override
    public List<ConvocatoriaEntity> findActivas() {
        return convocatoriaDAO.findConvocatoriasActivas();
    }

    @Override
    public void save(ConvocatoriaEntity convocatoria) {
        convocatoriaDAO.save(convocatoria);
    }

    @Override
    public void deleteById(Long id) {
        convocatoriaDAO.deleteById(id);
    }
}
