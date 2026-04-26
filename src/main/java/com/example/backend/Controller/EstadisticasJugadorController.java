package com.example.backend.Controller;

import com.example.backend.DTO.EstadisticasJugadorDTO;
import com.example.backend.Service.EstadisticasJugadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(originPatterns = "*")
public class EstadisticasJugadorController {

    private final EstadisticasJugadorService estadisticasService;

    public EstadisticasJugadorController(EstadisticasJugadorService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @GetMapping
    public ResponseEntity<List<EstadisticasJugadorDTO>> getAllEstadisticas() {
        return ResponseEntity.ok(estadisticasService.getAllEstadisticas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadisticasJugadorDTO> getEstadisticaById(@PathVariable Integer id) {
        return ResponseEntity.ok(estadisticasService.getEstadisticaById(id));
    }

    @PostMapping
    public ResponseEntity<EstadisticasJugadorDTO> createEstadistica(
            @RequestBody EstadisticasJugadorDTO estadisticaDTO) {
        return new ResponseEntity<>(estadisticasService.createEstadistica(estadisticaDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadisticasJugadorDTO> updateEstadistica(
            @PathVariable Integer id,
            @RequestBody EstadisticasJugadorDTO estadisticaDTO) {
        return ResponseEntity.ok(estadisticasService.updateEstadistica(id, estadisticaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadistica(@PathVariable Integer id) {
        estadisticasService.deleteEstadistica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jugador/{jugadorId}/partido/{partidoId}")
    public ResponseEntity<EstadisticasJugadorDTO> getEstadisticasByJugadorAndPartido(
            @PathVariable Integer jugadorId,
            @PathVariable Integer partidoId) {
        return ResponseEntity.ok(estadisticasService
                .getEstadisticasByJugadorAndPartido(jugadorId, partidoId));
    }

    //  Devuelven Map<String, Object>
    @GetMapping("/top-goleadores")
    public ResponseEntity<List<Map<String, Object>>> getTopGoleadores() {
        return ResponseEntity.ok(estadisticasService.getTopGoleadores());
    }

    @GetMapping("/resumen-jugadores")
    public ResponseEntity<List<Map<String, Object>>> getResumenEstadisticasJugadores() {
        return ResponseEntity.ok(estadisticasService.getResumenEstadisticasJugadores());
    }

    @GetMapping("/por-equipo")
    public ResponseEntity<List<Map<String, Object>>> getEstadisticasPorEquipo() {
        return ResponseEntity.ok(estadisticasService.getEstadisticasPorEquipo());
    }

    @GetMapping("/jugador/{jugadorId}/completo")
    public ResponseEntity<Map<String, Object>> getEstadisticasCompletasByJugador(
            @PathVariable Integer jugadorId) {
        return ResponseEntity.ok(estadisticasService.getEstadisticasCompletasByJugador(jugadorId));
    }
}