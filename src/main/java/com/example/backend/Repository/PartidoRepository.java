package com.example.backend.Repository;

import com.example.backend.Model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Integer> {

    // Consulta nativa: Total de goles de un equipo
    @Query(value = "SELECT COALESCE(SUM(goles_local), 0) + COALESCE(SUM(goles_visita), 0) " +
            "FROM partido WHERE equipo_local = :equipoId OR equipo_visita = :equipoId",
            nativeQuery = true)
    Integer findTotalGolesByEquipoNative(@Param("equipoId") Integer equipoId);

    //  Devuelve List<Partido> directamente (sin DTO extra)
    @Query(value = "SELECT p.* FROM partido p " +
            "INNER JOIN equipo e1 ON p.equipo_local = e1.id_equipo " +
            "INNER JOIN equipo e2 ON p.equipo_visita = e2.id_equipo " +
            "ORDER BY p.fecha_partido DESC",
            nativeQuery = true)
    List<Partido> findAllPartidosWithEquiposNative();

    // Consulta nativa: Partidos por equipo
    @Query(value = "SELECT p.* FROM partido p " +
            "WHERE p.equipo_local = :equipoId OR p.equipo_visita = :equipoId " +
            "ORDER BY p.fecha_partido DESC",
            nativeQuery = true)
    List<Partido> findPartidosByEquipoNative(@Param("equipoId") Integer equipoId);

    // Consulta nativa: Partidos por estadio
    @Query(value = "SELECT * FROM partido WHERE estadio = :estadio", nativeQuery = true)
    List<Partido> findPartidosByEstadioNative(@Param("estadio") String estadio);
}