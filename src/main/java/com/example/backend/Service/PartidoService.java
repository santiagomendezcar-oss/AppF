package com.example.backend.Service;

import com.example.backend.DTO.PartidoDTO;
import com.example.backend.Model.Partido;
import java.util.List;
import java.util.Map;

public interface PartidoService {
    // CRUD
    List<PartidoDTO> getAllPartidos();
    PartidoDTO getPartidoById(Integer id);
    PartidoDTO createPartido(PartidoDTO partidoDTO);
    PartidoDTO updatePartido(Integer id, PartidoDTO partidoDTO);
    void deletePartido(Integer id);

    // Consultas específicas
    Integer getTotalGolesEquipo(Integer equipoId);

    //  Ahora devuelve List<Partido> directamente
    List<Partido> getAllPartidosWithResults();

    List<PartidoDTO> getPartidosByEquipo(Integer equipoId);
    List<PartidoDTO> getPartidosByEstadio(String estadio);
}