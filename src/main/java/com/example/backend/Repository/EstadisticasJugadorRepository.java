package com.example.backend.Repository;

import com.example.backend.Model.EstadisticasJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface EstadisticasJugadorRepository extends JpaRepository<EstadisticasJugador, Integer> {

    // Consulta nativa: Estadísticas por partido de un jugador
    @Query(value = "SELECT ej.* FROM estadisticas_jugador ej " +
            "WHERE ej.id_jugador = :jugadorId AND ej.id_partido = :partidoId",
            nativeQuery = true)
    EstadisticasJugador findEstadisticasByJugadorAndPartidoNative(
            @Param("jugadorId") Integer jugadorId,
            @Param("partidoId") Integer partidoId);

    //  Devuelve List<Object[]> y lo procesamos en Service
    @Query(value = "SELECT j.id_jugador, j.nombre, SUM(ej.goles) as total_goles " +
            "FROM estadisticas_jugador ej " +
            "INNER JOIN jugador j ON ej.id_jugador = j.id_jugador " +
            "GROUP BY j.id_jugador, j.nombre ORDER BY total_goles DESC LIMIT 10",
            nativeQuery = true)
    List<Object[]> findTopGoleadoresNative();

    //  Devuelve List<Object[]> y lo procesamos en Service (sin DTO extra)
    @Query(value = "SELECT " +
            "j.id_jugador, " +
            "j.nombre, " +
            "COALESCE(SUM(ej.goles), 0) as totalGoles, " +
            "COALESCE(SUM(ej.asistencias), 0) as totalAsistencias, " +
            "COALESCE(SUM(ej.tarjetas_amarillas), 0) as totalTarjetasAmarillas, " +
            "COALESCE(SUM(ej.tarjetas_rojas), 0) as totalTarjetasRojas, " +
            "COUNT(DISTINCT ej.id_partido) as totalPartidosJugados " +
            "FROM jugador j " +
            "LEFT JOIN estadisticas_jugador ej ON j.id_jugador = ej.id_jugador " +
            "GROUP BY j.id_jugador, j.nombre ORDER BY totalGoles DESC",
            nativeQuery = true)
    List<Object[]> findResumenEstadisticasJugadoresNative();

    //  Devuelve List<Object[]> con estadísticas por equipo
    @Query(value = "SELECT " +
            "eq.nombre as equipo, " +
            "SUM(ej.goles) as total_goles_equipo, " +
            "SUM(ej.asistencias) as total_asistencias_equipo, " +
            "SUM(ej.tarjetas_amarillas) as total_tarjetas_amarillas, " +
            "SUM(ej.tarjetas_rojas) as total_tarjetas_rojas " +
            "FROM estadisticas_jugador ej " +
            "INNER JOIN jugador j ON ej.id_jugador = j.id_jugador " +
            "INNER JOIN equipo eq ON j.id_equipo = eq.id_equipo " +
            "GROUP BY eq.id_equipo, eq.nombre",
            nativeQuery = true)
    List<Object[]> findEstadisticasPorEquipoNative();

    // Consulta nativa: Estadísticas de un jugador en todos los partidos
    @Query(value = "SELECT " +
            "SUM(ej.goles) as total_goles, " +
            "SUM(ej.asistencias) as total_asistencias, " +
            "SUM(ej.tarjetas_amarillas) as total_amarillas, " +
            "SUM(ej.tarjetas_rojas) as total_rojas, " +
            "COUNT(ej.id_partido) as partidos_jugados " +
            "FROM estadisticas_jugador ej " +
            "WHERE ej.id_jugador = :jugadorId",
            nativeQuery = true)
    List<Object[]> findEstadisticasCompletasByJugador(@Param("jugadorId") Integer jugadorId);
}