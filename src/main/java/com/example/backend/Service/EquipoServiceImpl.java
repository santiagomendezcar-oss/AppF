package com.example.backend.Service;

import com.example.backend.DTO.EquipoDTO;
import com.example.backend.DTO.JugadorDTO;
import com.example.backend.DTO.EntrenadorDTO;
import com.example.backend.Model.Equipo;
import com.example.backend.Model.Jugador;
import com.example.backend.Model.Entrenador;
import com.example.backend.Repository.EquipoRepository;
import com.example.backend.Repository.JugadorRepository;
import com.example.backend.Repository.EntrenadorRepository;
import com.example.backend.Service.EquipoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final EntrenadorRepository entrenadorRepository;

    public EquipoServiceImpl(EquipoRepository equipoRepository,
                             JugadorRepository jugadorRepository,
                             EntrenadorRepository entrenadorRepository) {
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    @Override
    public List<EquipoDTO> getAllEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipoDTO getEquipoById(Integer id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id));
        return convertToDTO(equipo);
    }

    @Override
    public EquipoDTO createEquipo(EquipoDTO equipoDTO) {
        Equipo equipo = convertToEntity(equipoDTO);
        Equipo savedEquipo = equipoRepository.save(equipo);
        return convertToDTO(savedEquipo);
    }

    @Override
    public EquipoDTO updateEquipo(Integer id, EquipoDTO equipoDTO) {
        Equipo existingEquipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + id));

        existingEquipo.setNombre(equipoDTO.getNombre());
        existingEquipo.setCiudad(equipoDTO.getCiudad());
        existingEquipo.setFundacion(equipoDTO.getFundacion());

        Equipo updatedEquipo = equipoRepository.save(existingEquipo);
        return convertToDTO(updatedEquipo);
    }

    @Override
    public void deleteEquipo(Integer id) {
        equipoRepository.deleteById(id);
    }

    @Override
    public List<EquipoDTO> getEquiposByCiudad(String ciudad) {
        return equipoRepository.findEquiposByCiudadNative(ciudad)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EquipoDTO> getEquiposByFundacionAfter(String anio) {
        return equipoRepository.findEquiposByFundacionAfter(anio)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getRankingEquiposByGoles() {
        List<Object[]> results = equipoRepository.findRankingEquiposByGolesNative();
        List<Map<String, Object>> ranking = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> equipoRanking = new HashMap<>();
            equipoRanking.put("id_equipo", row[0]);
            equipoRanking.put("nombre", row[1]);
            equipoRanking.put("total_goles", row[2]);
            ranking.add(equipoRanking);
        }
        return ranking;
    }

    private EquipoDTO convertToDTO(Equipo equipo) {
        EquipoDTO dto = new EquipoDTO();
        dto.setId_equipo(equipo.getId_equipo());
        dto.setNombre(equipo.getNombre());
        dto.setCiudad(equipo.getCiudad());
        dto.setFundacion(equipo.getFundacion());

        if (equipo.getJugadores() != null) {
            List<JugadorDTO> jugadoresDTO = equipo.getJugadores()
                    .stream()
                    .map(this::convertJugadorToDTO)
                    .collect(Collectors.toList());
            dto.setJugadores(jugadoresDTO);
        }

        return dto;
    }

    private JugadorDTO convertJugadorToDTO(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO();
        dto.setId_jugador(jugador.getId_jugador());
        dto.setNombre(jugador.getNombre());
        dto.setPosicion(jugador.getPosicion());
        dto.setDorsal(jugador.getDorsal());
        dto.setFechaNacimiento(jugador.getFechaNacimiento());
        dto.setNacionalidad(jugador.getNacionalidad());
        if (jugador.getEquipo() != null) {
            dto.setId_equipo(jugador.getEquipo().getId_equipo());
            dto.setNombreEquipo(jugador.getEquipo().getNombre());
        }
        return dto;
    }

    private Equipo convertToEntity(EquipoDTO dto) {
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setCiudad(dto.getCiudad());
        equipo.setFundacion(dto.getFundacion());
        return equipo;
    }
}