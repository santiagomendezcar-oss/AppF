package com.example.backend.Service;


import com.example.backend.DTO.JugadorDTO;
import com.example.backend.Model.Jugador;
import com.example.backend.Model.Equipo;
import com.example.backend.Repository.JugadorRepository;
import com.example.backend.Repository.EquipoRepository;
import com.example.backend.Service.JugadorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;


    public JugadorServiceImpl(JugadorRepository jugadorRepository, EquipoRepository equipoRepository) {
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    @Override
    public List<JugadorDTO> getAllJugadores() {
        return jugadorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JugadorDTO getJugadorById(Integer id) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
        return convertToDTO(jugador);
    }

    @Override
    public JugadorDTO createJugador(JugadorDTO jugadorDTO) {
        Jugador jugador = convertToEntity(jugadorDTO);
        Jugador savedJugador = jugadorRepository.save(jugador);
        return convertToDTO(savedJugador);
    }

    @Override
    public JugadorDTO updateJugador(Integer id, JugadorDTO jugadorDTO) {
        Jugador existingJugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));

        existingJugador.setNombre(jugadorDTO.getNombre());
        existingJugador.setPosicion(jugadorDTO.getPosicion());
        existingJugador.setDorsal(jugadorDTO.getDorsal());
        existingJugador.setFechaNacimiento(jugadorDTO.getFechaNacimiento());
        existingJugador.setNacionalidad(jugadorDTO.getNacionalidad());

        if (jugadorDTO.getId_equipo() != null) {
            Equipo equipo = equipoRepository.findById(jugadorDTO.getId_equipo())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            existingJugador.setEquipo(equipo);
        }

        Jugador updatedJugador = jugadorRepository.save(existingJugador);
        return convertToDTO(updatedJugador);
    }

    @Override
    public void deleteJugador(Integer id) {
        jugadorRepository.deleteById(id);
    }

    @Override
    public List<JugadorDTO> getJugadoresByEquipo(Integer equipoId) {
        return jugadorRepository.findJugadoresByEquipoNative(equipoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JugadorDTO> getJugadoresConMasGoles(Integer minGoles) {
        return jugadorRepository.findJugadoresWithMoreGoals(minGoles)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JugadorDTO> getJugadoresByNombre(String nombre) {
        return jugadorRepository.findJugadoresByNombreContaining(nombre)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JugadorDTO> getJugadoresByNacionalidad(String nacionalidad) {
        return jugadorRepository.findJugadoresByNacionalidadNative(nacionalidad)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private JugadorDTO convertToDTO(Jugador jugador) {
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

    private Jugador convertToEntity(JugadorDTO dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setPosicion(dto.getPosicion());
        jugador.setDorsal(dto.getDorsal());
        jugador.setFechaNacimiento(dto.getFechaNacimiento());
        jugador.setNacionalidad(dto.getNacionalidad());

        if (dto.getId_equipo() != null) {
            Equipo equipo = equipoRepository.findById(dto.getId_equipo())
                    .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
            jugador.setEquipo(equipo);
        }
        return jugador;
    }
}