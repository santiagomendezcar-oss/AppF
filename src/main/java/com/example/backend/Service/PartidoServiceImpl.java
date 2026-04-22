package com.example.backend.Service;

import com.example.backend.DTO.PartidoDTO;
import com.example.backend.Model.Partido;
import com.example.backend.Model.Equipo;
import com.example.backend.Repository.PartidoRepository;
import com.example.backend.Repository.EquipoRepository;
import com.example.backend.Service.PartidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartidoServiceImpl implements PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;

    public PartidoServiceImpl(PartidoRepository partidoRepository, EquipoRepository equipoRepository) {
        this.partidoRepository = partidoRepository;
        this.equipoRepository = equipoRepository;
    }

    @Override
    public List<PartidoDTO> getAllPartidos() {
        return partidoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartidoDTO getPartidoById(Integer id) {
        Partido partido = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));
        return convertToDTO(partido);
    }

    @Override
    public PartidoDTO createPartido(PartidoDTO partidoDTO) {
        Partido partido = convertToEntity(partidoDTO);
        Partido savedPartido = partidoRepository.save(partido);
        return convertToDTO(savedPartido);
    }

    @Override
    public PartidoDTO updatePartido(Integer id, PartidoDTO partidoDTO) {
        Partido existingPartido = partidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));

        existingPartido.setFecha_partido(partidoDTO.getFecha_partido());
        existingPartido.setEstadio(partidoDTO.getEstadio());
        existingPartido.setGoles_local(partidoDTO.getGoles_local());
        existingPartido.setGoles_visita(partidoDTO.getGoles_visita());

        if (partidoDTO.getId_equipo_local() != null) {
            Equipo equipoLocal = equipoRepository.findById(partidoDTO.getId_equipo_local())
                    .orElseThrow(() -> new RuntimeException("Equipo local no encontrado"));
            existingPartido.setEquipo_local(equipoLocal);
        }

        if (partidoDTO.getId_equipo_visita() != null) {
            Equipo equipoVisita = equipoRepository.findById(partidoDTO.getId_equipo_visita())
                    .orElseThrow(() -> new RuntimeException("Equipo visita no encontrado"));
            existingPartido.setEquipo_visita(equipoVisita);
        }

        Partido updatedPartido = partidoRepository.save(existingPartido);
        return convertToDTO(updatedPartido);
    }

    @Override
    public void deletePartido(Integer id) {
        partidoRepository.deleteById(id);
    }

    @Override
    public Integer getTotalGolesEquipo(Integer equipoId) {
        return partidoRepository.findTotalGolesByEquipoNative(equipoId);
    }

    @Override
    public List<Partido> getAllPartidosWithResults() {
        //  Devuelve directamente los objetos Partido
        return partidoRepository.findAllPartidosWithEquiposNative();
    }

    @Override
    public List<PartidoDTO> getPartidosByEquipo(Integer equipoId) {
        return partidoRepository.findPartidosByEquipoNative(equipoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PartidoDTO> getPartidosByEstadio(String estadio) {
        return partidoRepository.findPartidosByEstadioNative(estadio)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PartidoDTO convertToDTO(Partido partido) {
        PartidoDTO dto = new PartidoDTO();
        dto.setId_partido(partido.getId_partido());
        dto.setFecha_partido(partido.getFecha_partido());
        dto.setEstadio(partido.getEstadio());
        dto.setGoles_local(partido.getGoles_local());
        dto.setGoles_visita(partido.getGoles_visita());

        if (partido.getEquipo_local() != null) {
            dto.setId_equipo_local(partido.getEquipo_local().getId_equipo());
            dto.setNombreEquipoLocal(partido.getEquipo_local().getNombre());
        }

        if (partido.getEquipo_visita() != null) {
            dto.setId_equipo_visita(partido.getEquipo_visita().getId_equipo());
            dto.setNombreEquipoVisita(partido.getEquipo_visita().getNombre());
        }

        return dto;
    }

    private Partido convertToEntity(PartidoDTO dto) {
        Partido partido = new Partido();
        partido.setFecha_partido(dto.getFecha_partido());
        partido.setEstadio(dto.getEstadio());
        partido.setGoles_local(dto.getGoles_local());
        partido.setGoles_visita(dto.getGoles_visita());

        if (dto.getId_equipo_local() != null) {
            Equipo equipoLocal = equipoRepository.findById(dto.getId_equipo_local())
                    .orElseThrow(() -> new RuntimeException("Equipo local no encontrado"));
            partido.setEquipo_local(equipoLocal);
        }

        if (dto.getId_equipo_visita() != null) {
            Equipo equipoVisita = equipoRepository.findById(dto.getId_equipo_visita())
                    .orElseThrow(() -> new RuntimeException("Equipo visita no encontrado"));
            partido.setEquipo_visita(equipoVisita);
        }

        return partido;
    }
}