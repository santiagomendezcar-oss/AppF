package com.example.backend.Service;

import com.example.backend.DTO.EntrenadorDTO;
import com.example.backend.Model.Entrenador;
import com.example.backend.Model.Equipo;
import com.example.backend.Repository.EntrenadorRepository;
import com.example.backend.Repository.EquipoRepository;
import com.example.backend.Service.EntrenadorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntrenadorServiceImpl implements EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final EquipoRepository equipoRepository;

    public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepository, EquipoRepository equipoRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.equipoRepository = equipoRepository;
    }

    @Override
    public List<EntrenadorDTO> getAllEntrenadores() {
        return entrenadorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EntrenadorDTO getEntrenadorById(Integer id) {
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id: " + id));
        return convertToDTO(entrenador);
    }

    @Override
    public EntrenadorDTO createEntrenador(EntrenadorDTO entrenadorDTO) {
        Entrenador entrenador = convertToEntity(entrenadorDTO);
        Entrenador savedEntrenador = entrenadorRepository.save(entrenador);
        return convertToDTO(savedEntrenador);
    }

    @Override
    public EntrenadorDTO updateEntrenador(Integer id, EntrenadorDTO entrenadorDTO) {
        Entrenador existingEntrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id: " + id));

        existingEntrenador.setNombre(entrenadorDTO.getNombre());
        existingEntrenador.setEspecialidad(entrenadorDTO.getEspecialidad());

        if (entrenadorDTO.getId_equipo() != null) {
            Equipo equipo = equipoRepository.findById(entrenadorDTO.getId_equipo())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            existingEntrenador.setEquipo(equipo);
        }

        Entrenador updatedEntrenador = entrenadorRepository.save(existingEntrenador);
        return convertToDTO(updatedEntrenador);
    }

    @Override
    public void deleteEntrenador(Integer id) {
        entrenadorRepository.deleteById(id);
    }

    @Override
    public List<EntrenadorDTO> getEntrenadoresByEspecialidad(String especialidad) {
        return entrenadorRepository.findEntrenadoresByEspecialidadNative(especialidad)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EntrenadorDTO getEntrenadorByEquipo(Integer equipoId) {
        Entrenador entrenador = entrenadorRepository.findEntrenadorByEquipoNative(equipoId);
        return entrenador != null ? convertToDTO(entrenador) : null;
    }

    @Override
    public List<EntrenadorDTO> getEntrenadoresByNombre(String nombre) {
        return entrenadorRepository.findEntrenadoresByNombreContaining(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EntrenadorDTO convertToDTO(Entrenador entrenador) {
        EntrenadorDTO dto = new EntrenadorDTO();
        dto.setId_entrenador(entrenador.getId_entrenador());
        dto.setNombre(entrenador.getNombre());
        dto.setEspecialidad(entrenador.getEspecialidad());
        if (entrenador.getEquipo() != null) {
            dto.setId_equipo(entrenador.getEquipo().getId_equipo());
            dto.setNombreEquipo(entrenador.getEquipo().getNombre());
        }
        return dto;
    }

    private Entrenador convertToEntity(EntrenadorDTO dto) {
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(dto.getNombre());
        entrenador.setEspecialidad(dto.getEspecialidad());

        if (dto.getId_equipo() != null) {
            Equipo equipo = equipoRepository.findById(dto.getId_equipo())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            entrenador.setEquipo(equipo);
        }
        return entrenador;
    }
}