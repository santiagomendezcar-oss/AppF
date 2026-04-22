package com.example.backend.Service;

import com.example.backend.DTO.JugadorDTO;
import java.util.List;

public interface JugadorService {
    // CRUD
    List<JugadorDTO> getAllJugadores();
    JugadorDTO getJugadorById(Integer id);
    JugadorDTO createJugador(JugadorDTO jugadorDTO);
    JugadorDTO updateJugador(Integer id, JugadorDTO jugadorDTO);
    void deleteJugador(Integer id);

    // Consultas específicas
    List<JugadorDTO> getJugadoresByEquipo(Integer equipoId);
    List<JugadorDTO> getJugadoresConMasGoles(Integer minGoles);
    List<JugadorDTO> getJugadoresByNombre(String nombre);
    List<JugadorDTO> getJugadoresByNacionalidad(String nacionalidad);
}
