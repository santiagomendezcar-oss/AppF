package com.example.backend.Service;

import com.example.backend.DTO.EquipoDTO;
import java.util.List;
import java.util.Map;

public interface EquipoService {
    // CRUD
    List<EquipoDTO> getAllEquipos();
    EquipoDTO getEquipoById(Integer id);
    EquipoDTO createEquipo(EquipoDTO equipoDTO);
    EquipoDTO updateEquipo(Integer id, EquipoDTO equipoDTO);
    void deleteEquipo(Integer id);

    // Consultas específicas
    List<EquipoDTO> getEquiposByCiudad(String ciudad);
    List<EquipoDTO> getEquiposByFundacionAfter(String anio);
    List<Map<String, Object>> getRankingEquiposByGoles();
}