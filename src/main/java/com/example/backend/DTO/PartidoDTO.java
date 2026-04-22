package com.example.backend.DTO;

import java.util.Date;

public class PartidoDTO {
    private int id_partido;
    private Date fecha_partido;
    private String estadio;
    private Integer id_equipo_local;
    private String nombreEquipoLocal;
    private Integer id_equipo_visita;
    private String nombreEquipoVisita;
    private int goles_local;
    private int goles_visita;

    public PartidoDTO() {
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public Date getFecha_partido() {
        return fecha_partido;
    }

    public void setFecha_partido(Date fecha_partido) {
        this.fecha_partido = fecha_partido;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public Integer getId_equipo_local() {
        return id_equipo_local;
    }

    public void setId_equipo_local(Integer id_equipo_local) {
        this.id_equipo_local = id_equipo_local;
    }

    public String getNombreEquipoLocal() {
        return nombreEquipoLocal;
    }

    public void setNombreEquipoLocal(String nombreEquipoLocal) {
        this.nombreEquipoLocal = nombreEquipoLocal;
    }

    public Integer getId_equipo_visita() {
        return id_equipo_visita;
    }

    public void setId_equipo_visita(Integer id_equipo_visita) {
        this.id_equipo_visita = id_equipo_visita;
    }

    public String getNombreEquipoVisita() {
        return nombreEquipoVisita;
    }

    public void setNombreEquipoVisita(String nombreEquipoVisita) {
        this.nombreEquipoVisita = nombreEquipoVisita;
    }

    public int getGoles_local() {
        return goles_local;
    }

    public void setGoles_local(int goles_local) {
        this.goles_local = goles_local;
    }

    public int getGoles_visita() {
        return goles_visita;
    }

    public void setGoles_visita(int goles_visita) {
        this.goles_visita = goles_visita;
    }

}