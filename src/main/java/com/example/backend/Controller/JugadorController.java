package com.example.backend.Controller;

import com.example.backend.DTO.JugadorDTO;
import com.example.backend.Service.JugadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@CrossOrigin(originPatterns = "*")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> getAllJugadores() {
        return ResponseEntity.ok(jugadorService.getAllJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getJugadorById(@PathVariable Integer id) {
        return ResponseEntity.ok(jugadorService.getJugadorById(id));
    }

    @PostMapping
    public ResponseEntity<JugadorDTO> createJugador(@RequestBody JugadorDTO jugadorDTO) {
        return new ResponseEntity<>(jugadorService.createJugador(jugadorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> updateJugador(@PathVariable Integer id,
                                                    @RequestBody JugadorDTO jugadorDTO) {
        return ResponseEntity.ok(jugadorService.updateJugador(id, jugadorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJugador(@PathVariable Integer id) {
        jugadorService.deleteJugador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<JugadorDTO>> getJugadoresByEquipo(@PathVariable Integer equipoId) {
        return ResponseEntity.ok(jugadorService.getJugadoresByEquipo(equipoId));
    }

    @GetMapping("/goles/{minGoles}")
    public ResponseEntity<List<JugadorDTO>> getJugadoresConMasGoles(@PathVariable Integer minGoles) {
        return ResponseEntity.ok(jugadorService.getJugadoresConMasGoles(minGoles));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<JugadorDTO>> getJugadoresByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(jugadorService.getJugadoresByNombre(nombre));
    }

    @GetMapping("/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<JugadorDTO>> getJugadoresByNacionalidad(@PathVariable String nacionalidad) {
        return ResponseEntity.ok(jugadorService.getJugadoresByNacionalidad(nacionalidad));
    }
}