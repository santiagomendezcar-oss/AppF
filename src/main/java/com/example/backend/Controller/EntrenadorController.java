package com.example.backend.Controller;

import com.example.backend.DTO.EntrenadorDTO;
import com.example.backend.Service.EntrenadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@CrossOrigin(originPatterns = "*")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping
    public ResponseEntity<List<EntrenadorDTO>> getAllEntrenadores() {
        return ResponseEntity.ok(entrenadorService.getAllEntrenadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorDTO> getEntrenadorById(@PathVariable Integer id) {
        return ResponseEntity.ok(entrenadorService.getEntrenadorById(id));
    }

    @PostMapping
    public ResponseEntity<EntrenadorDTO> createEntrenador(@RequestBody EntrenadorDTO entrenadorDTO) {
        return new ResponseEntity<>(entrenadorService.createEntrenador(entrenadorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorDTO> updateEntrenador(@PathVariable Integer id,
                                                          @RequestBody EntrenadorDTO entrenadorDTO) {
        return ResponseEntity.ok(entrenadorService.updateEntrenador(id, entrenadorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrenador(@PathVariable Integer id) {
        entrenadorService.deleteEntrenador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<EntrenadorDTO>> getEntrenadoresByEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(entrenadorService.getEntrenadoresByEspecialidad(especialidad));
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<EntrenadorDTO> getEntrenadorByEquipo(@PathVariable Integer equipoId) {
        return ResponseEntity.ok(entrenadorService.getEntrenadorByEquipo(equipoId));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EntrenadorDTO>> getEntrenadoresByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(entrenadorService.getEntrenadoresByNombre(nombre));
    }
}