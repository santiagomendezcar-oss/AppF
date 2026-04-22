package com.example.backend.Repository;

import com.example.backend.Model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    @Query(value = "SELECT j.* FROM jugador j WHERE j.id_equipo = :equipoId", nativeQuery = true)
    List<Jugador> findJugadoresByEquipoNative(@Param("equipoId") Integer equipoId);

    @Query(value = "SELECT j.* FROM jugador j " +
            "INNER JOIN estadisticas_jugador ej ON j.id_jugador = ej.id_jugador " +
            "GROUP BY j.id_jugador HAVING SUM(ej.goles) > :goles", nativeQuery = true)
    List<Jugador> findJugadoresWithMoreGoals(@Param("goles") Integer goles);

    @Query(value = "SELECT * FROM jugador WHERE nombre LIKE CONCAT('%', :nombre, '%')", nativeQuery = true)
    List<Jugador> findJugadoresByNombreContaining(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM jugador WHERE nacionalidad = :nacionalidad", nativeQuery = true)
    List<Jugador> findJugadoresByNacionalidadNative(@Param("nacionalidad") String nacionalidad);
}