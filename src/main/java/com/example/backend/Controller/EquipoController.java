package com.example.backend.Controller;

import com.example.backend.DTO.EquipoDTO;
import com.example.backend.Service.EquipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(originPatterns = "*")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> getAllEquipos() {
        return ResponseEntity.ok(equipoService.getAllEquipos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> getEquipoById(@PathVariable Integer id) {
        return ResponseEntity.ok(equipoService.getEquipoById(id));
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> createEquipo(@RequestBody EquipoDTO equipoDTO) {
        return new ResponseEntity<>(equipoService.createEquipo(equipoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> updateEquipo(@PathVariable Integer id,
                                                  @RequestBody EquipoDTO equipoDTO) {
        return ResponseEntity.ok(equipoService.updateEquipo(id, equipoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Integer id) {
        equipoService.deleteEquipo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<EquipoDTO>> getEquiposByCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(equipoService.getEquiposByCiudad(ciudad));
    }

    @GetMapping("/fundacion/{anio}")
    public ResponseEntity<List<EquipoDTO>> getEquiposByFundacionAfter(@PathVariable String anio) {
        return ResponseEntity.ok(equipoService.getEquiposByFundacionAfter(anio));
    }

    @GetMapping("/ranking/goles")
    public ResponseEntity<List<Map<String, Object>>> getRankingEquiposByGoles() {
        return ResponseEntity.ok(equipoService.getRankingEquiposByGoles());
    }
}