package com.example.backend.Service;

import com.example.backend.DTO.EntrenadorDTO;
import java.util.List;

public interface EntrenadorService {
    // CRUD
    List<EntrenadorDTO> getAllEntrenadores();
    EntrenadorDTO getEntrenadorById(Integer id);
    EntrenadorDTO createEntrenador(EntrenadorDTO entrenadorDTO);
    EntrenadorDTO updateEntrenador(Integer id, EntrenadorDTO entrenadorDTO);
    void deleteEntrenador(Integer id);

    // Consultas específicas
    List<EntrenadorDTO> getEntrenadoresByEspecialidad(String especialidad);
    EntrenadorDTO getEntrenadorByEquipo(Integer equipoId);
    List<EntrenadorDTO> getEntrenadoresByNombre(String nombre);
}