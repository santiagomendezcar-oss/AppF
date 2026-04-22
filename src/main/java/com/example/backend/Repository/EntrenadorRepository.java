package com.example.backend.Repository;

import com.example.backend.Model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {

    @Query(value = "SELECT * FROM entrenador WHERE especialidad = :especialidad", nativeQuery = true)
    List<Entrenador> findEntrenadoresByEspecialidadNative(@Param("especialidad") String especialidad);

    @Query(value = "SELECT e.* FROM entrenador e WHERE e.id_equipo = :equipoId", nativeQuery = true)
    Entrenador findEntrenadorByEquipoNative(@Param("equipoId") Integer equipoId);

    @Query(value = "SELECT * FROM entrenador WHERE nombre LIKE CONCAT('%', :nombre, '%')", nativeQuery = true)
    List<Entrenador> findEntrenadoresByNombreContaining(@Param("nombre") String nombre);
}