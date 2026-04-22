package com.example.backend.Service;

import com.example.backend.DTO.EstadisticasJugadorDTO;
import java.util.List;
import java.util.Map;

public interface EstadisticasJugadorService {
    // CRUD
    List<EstadisticasJugadorDTO> getAllEstadisticas();
    EstadisticasJugadorDTO getEstadisticaById(Integer id);
    EstadisticasJugadorDTO createEstadistica(EstadisticasJugadorDTO estadisticaDTO);
    EstadisticasJugadorDTO updateEstadistica(Integer id, EstadisticasJugadorDTO estadisticaDTO);
    void deleteEstadistica(Integer id);

    // Consultas específicas
    EstadisticasJugadorDTO getEstadisticasByJugadorAndPartido(Integer jugadorId, Integer partidoId);

    //  Devuelven Map<String, Object> en lugar de DTOs específicos
    List<Map<String, Object>> getTopGoleadores();
    List<Map<String, Object>> getResumenEstadisticasJugadores();
    List<Map<String, Object>> getEstadisticasPorEquipo();
    Map<String, Object> getEstadisticasCompletasByJugador(Integer jugadorId);
}