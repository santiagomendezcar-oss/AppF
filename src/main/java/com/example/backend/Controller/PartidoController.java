package com.example.backend.Controller;

import com.example.backend.DTO.PartidoDTO;
import com.example.backend.Model.Partido;
import com.example.backend.Service.PartidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/partidos")
@CrossOrigin(originPatterns = "*")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping
    public ResponseEntity<List<PartidoDTO>> getAllPartidos() {
        return ResponseEntity.ok(partidoService.getAllPartidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getPartidoById(@PathVariable Integer id) {
        return ResponseEntity.ok(partidoService.getPartidoById(id));
    }

    @PostMapping
    public ResponseEntity<PartidoDTO> createPartido(@RequestBody PartidoDTO partidoDTO) {
        return new ResponseEntity<>(partidoService.createPartido(partidoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> updatePartido(@PathVariable Integer id,
                                                    @RequestBody PartidoDTO partidoDTO) {
        return ResponseEntity.ok(partidoService.updatePartido(id, partidoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Integer id) {
        partidoService.deletePartido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total-goles/{equipoId}")
    public ResponseEntity<Map<String, Integer>> getTotalGolesEquipo(@PathVariable Integer equipoId) {
        Integer totalGoles = partidoService.getTotalGolesEquipo(equipoId);
        Map<String, Integer> response = new HashMap<>();
        response.put("totalGoles", totalGoles);
        return ResponseEntity.ok(response);
    }

    //  Ahora devuelve directamente los objetos Partido
    @GetMapping("/resultados")
    public ResponseEntity<List<Partido>> getAllResultados() {
        return ResponseEntity.ok(partidoService.getAllPartidosWithResults());
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<PartidoDTO>> getPartidosByEquipo(@PathVariable Integer equipoId) {
        return ResponseEntity.ok(partidoService.getPartidosByEquipo(equipoId));
    }

    @GetMapping("/estadio/{estadio}")
    public ResponseEntity<List<PartidoDTO>> getPartidosByEstadio(@PathVariable String estadio) {
        return ResponseEntity.ok(partidoService.getPartidosByEstadio(estadio));
    }
}