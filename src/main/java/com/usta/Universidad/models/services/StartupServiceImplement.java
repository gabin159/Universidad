package com.usta.Universidad.models.services;

import com.usta.Universidad.entities.StartupEntity;
import com.usta.Universidad.models.DAO.StartupDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StartupServiceImplement implements StartupService {

    private final StartupDAO startupDAO;

    public StartupServiceImplement(StartupDAO startupDAO) {
        this.startupDAO = startupDAO;
    }

    @Override
    public List<StartupEntity> findAll() {
        return (List<StartupEntity>) startupDAO.findAll();
    }

    @Override
    public StartupEntity findById(Long id) {
        Optional<StartupEntity> s = startupDAO.findById(id);
        return s.orElse(null);
    }

    @Override
    public List<StartupEntity> findAllWithConvocatoria() {
        return startupDAO.findAllWithConvocatoria();
    }

    @Override
    public List<StartupEntity> findByEmprendedor(Long idEmprendedor) {
        return startupDAO.findByUsuario(idEmprendedor);
    }

    @Override
    public List<StartupEntity> findByConvocatoria(Long idConvocatoria) {
        return startupDAO.findByConvocatoria(idConvocatoria);
    }

    @Override
    public void save(StartupEntity startup) {
        startupDAO.save(startup);
    }

    @Override
    public void deleteById(Long id) {
        startupDAO.deleteById(id);
    }

    @Override
    public StartupEntity actualizarstar(StartupEntity startup) {
        return startupDAO.save(startup);
    }
}
