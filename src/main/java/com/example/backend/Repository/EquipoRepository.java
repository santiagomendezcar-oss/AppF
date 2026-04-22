package com.example.backend.Repository;

import com.example.backend.Model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    @Query(value = "SELECT * FROM equipo WHERE ciudad = :ciudad", nativeQuery = true)
    List<Equipo> findEquiposByCiudadNative(@Param("ciudad") String ciudad);

    @Query(value = "SELECT * FROM equipo WHERE fundacion > :anio", nativeQuery = true)
    List<Equipo> findEquiposByFundacionAfter(@Param("anio") String anio);

    @Query(value = "SELECT e.id_equipo, e.nombre, " +
            "COALESCE(SUM(CASE WHEN p.equipo_local = e.id_equipo THEN p.goles_local ELSE 0 END), 0) + " +
            "COALESCE(SUM(CASE WHEN p.equipo_visita = e.id_equipo THEN p.goles_visita ELSE 0 END), 0) as total_goles " +
            "FROM equipo e LEFT JOIN partido p ON e.id_equipo = p.equipo_local OR e.id_equipo = p.equipo_visita " +
            "GROUP BY e.id_equipo, e.nombre ORDER BY total_goles DESC", nativeQuery = true)
    List<Object[]> findRankingEquiposByGolesNative();
}