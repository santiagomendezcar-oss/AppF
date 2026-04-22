package com.example.backend.Service;

import com.example.backend.DTO.EstadisticasJugadorDTO;
import com.example.backend.Model.EstadisticasJugador;
import com.example.backend.Model.Jugador;
import com.example.backend.Model.Partido;
import com.example.backend.Repository.EstadisticasJugadorRepository;
import com.example.backend.Repository.JugadorRepository;
import com.example.backend.Repository.PartidoRepository;
import com.example.backend.Service.EstadisticasJugadorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadisticasJugadorServiceImpl implements EstadisticasJugadorService {

    private final EstadisticasJugadorRepository estadisticasRepository;
    private final JugadorRepository jugadorRepository;
    private final PartidoRepository partidoRepository;

    public EstadisticasJugadorServiceImpl(EstadisticasJugadorRepository estadisticasRepository,
                                   JugadorRepository jugadorRepository,
                                   PartidoRepository partidoRepository) {
        this.estadisticasRepository = estadisticasRepository;
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
    }

    @Override
    public List<EstadisticasJugadorDTO> getAllEstadisticas() {
        return estadisticasRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EstadisticasJugadorDTO getEstadisticaById(Integer id) {
        EstadisticasJugador estadistica = estadisticasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estadística no encontrada con id: " + id));
        return convertToDTO(estadistica);
    }

    @Override
    public EstadisticasJugadorDTO createEstadistica(EstadisticasJugadorDTO estadisticaDTO) {
        EstadisticasJugador estadistica = convertToEntity(estadisticaDTO);
        EstadisticasJugador savedEstadistica = estadisticasRepository.save(estadistica);
        return convertToDTO(savedEstadistica);
    }

    @Override
    public EstadisticasJugadorDTO updateEstadistica(Integer id, EstadisticasJugadorDTO estadisticaDTO) {
        EstadisticasJugador existingEstadistica = estadisticasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estadística no encontrada con id: " + id));

        existingEstadistica.setMinutos_jugados(estadisticaDTO.getMinutos_jugados());
        existingEstadistica.setGoles(estadisticaDTO.getGoles());
        existingEstadistica.setAsistencias(estadisticaDTO.getAsistencias());
        existingEstadistica.setTarjetas_amarillas(estadisticaDTO.getTarjetas_amarillas());
        existingEstadistica.setTarjetas_rojas(estadisticaDTO.getTarjetas_rojas());

        EstadisticasJugador updatedEstadistica = estadisticasRepository.save(existingEstadistica);
        return convertToDTO(updatedEstadistica);
    }

    @Override
    public void deleteEstadistica(Integer id) {
        estadisticasRepository.deleteById(id);
    }

    @Override
    public EstadisticasJugadorDTO getEstadisticasByJugadorAndPartido(Integer jugadorId, Integer partidoId) {
        EstadisticasJugador estadistica = estadisticasRepository
                .findEstadisticasByJugadorAndPartidoNative(jugadorId, partidoId);
        return estadistica != null ? convertToDTO(estadistica) : null;
    }

    @Override
    public List<Map<String, Object>> getTopGoleadores() {
        List<Object[]> results = estadisticasRepository.findTopGoleadoresNative();
        List<Map<String, Object>> goleadores = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> goleador = new HashMap<>();
            goleador.put("id_jugador", row[0]);
            goleador.put("nombre", row[1]);
            goleador.put("total_goles", row[2]);
            goleadores.add(goleador);
        }
        return goleadores;
    }

    @Override
    public List<Map<String, Object>> getResumenEstadisticasJugadores() {
        List<Object[]> results = estadisticasRepository.findResumenEstadisticasJugadoresNative();
        List<Map<String, Object>> resumenes = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> resumen = new HashMap<>();
            resumen.put("id_jugador", row[0]);
            resumen.put("nombre", row[1]);
            resumen.put("total_goles", row[2]);
            resumen.put("total_asistencias", row[3]);
            resumen.put("total_tarjetas_amarillas", row[4]);
            resumen.put("total_tarjetas_rojas", row[5]);
            resumen.put("total_partidos_jugados", row[6]);
            resumenes.add(resumen);
        }
        return resumenes;
    }

    @Override
    public List<Map<String, Object>> getEstadisticasPorEquipo() {
        List<Object[]> results = estadisticasRepository.findEstadisticasPorEquipoNative();
        List<Map<String, Object>> estadisticasEquipo = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> stats = new HashMap<>();
            stats.put("equipo", row[0]);
            stats.put("total_goles_equipo", row[1]);
            stats.put("total_asistencias_equipo", row[2]);
            stats.put("total_tarjetas_amarillas", row[3]);
            stats.put("total_tarjetas_rojas", row[4]);
            estadisticasEquipo.add(stats);
        }
        return estadisticasEquipo;
    }

    @Override
    public Map<String, Object> getEstadisticasCompletasByJugador(Integer jugadorId) {
        List<Object[]> results = estadisticasRepository.findEstadisticasCompletasByJugador(jugadorId);
        Map<String, Object> estadisticas = new HashMap<>();

        if (!results.isEmpty()) {
            Object[] row = results.get(0);
            estadisticas.put("total_goles", row[0] != null ? row[0] : 0);
            estadisticas.put("total_asistencias", row[1] != null ? row[1] : 0);
            estadisticas.put("total_tarjetas_amarillas", row[2] != null ? row[2] : 0);
            estadisticas.put("total_tarjetas_rojas", row[3] != null ? row[3] : 0);
            estadisticas.put("partidos_jugados", row[4] != null ? row[4] : 0);
        }
        return estadisticas;
    }

    private EstadisticasJugadorDTO convertToDTO(EstadisticasJugador estadistica) {
        EstadisticasJugadorDTO dto = new EstadisticasJugadorDTO();
        dto.setId_estadistica(estadistica.getId_estadistica());
        dto.setMinutos_jugados(estadistica.getMinutos_jugados());
        dto.setGoles(estadistica.getGoles());
        dto.setAsistencias(estadistica.getAsistencias());
        dto.setTarjetas_amarillas(estadistica.getTarjetas_amarillas());
        dto.setTarjetas_rojas(estadistica.getTarjetas_rojas());

        if (estadistica.getJugador() != null) {
            dto.setId_jugador(estadistica.getJugador().getId_jugador());
            dto.setNombreJugador(estadistica.getJugador().getNombre());
        }

        if (estadistica.getPartido() != null) {
            dto.setId_partido(estadistica.getPartido().getId_partido());
            String descripcion = estadistica.getPartido().getEquipo_local().getNombre() +
                    " vs " +
                    estadistica.getPartido().getEquipo_visita().getNombre();
            dto.setPartidoDescripcion(descripcion);
        }

        return dto;
    }

    private EstadisticasJugador convertToEntity(EstadisticasJugadorDTO dto) {
        EstadisticasJugador estadistica = new EstadisticasJugador();
        estadistica.setMinutos_jugados(dto.getMinutos_jugados());
        estadistica.setGoles(dto.getGoles());
        estadistica.setAsistencias(dto.getAsistencias());
        estadistica.setTarjetas_amarillas(dto.getTarjetas_amarillas());
        estadistica.setTarjetas_rojas(dto.getTarjetas_rojas());

        if (dto.getId_jugador() != null) {
            Jugador jugador = jugadorRepository.findById(dto.getId_jugador())
                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
            estadistica.setJugador(jugador);
        }

        if (dto.getId_partido() != null) {
            Partido partido = partidoRepository.findById(dto.getId_partido())
                    .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
            estadistica.setPartido(partido);
        }

        return estadistica;
    }
}