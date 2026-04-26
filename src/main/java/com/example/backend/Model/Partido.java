package com.example.backend.Model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.util.Date;

@Entity
@Table(name = "partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_partido;

    @Temporal(TemporalType.DATE)
    private Date fecha_partido;

    @Column(length = 100)
    private String estadio;

    @ManyToOne
    @JoinColumn(name = "equipo_local")
    private Equipo equipo_local;

    @ManyToOne
    @JoinColumn(name = "equipo_visita")
    private Equipo equipo_visita;

    private int goles_local;
    private int goles_visita;

    public Partido() {
    }

    public Partido(int id_partido, Date fecha_partido, String estadio, Equipo equipo_local, Equipo equipo_visita, int goles_local, int goles_visita) {
        this.id_partido = id_partido;
        this.fecha_partido = fecha_partido;
        this.estadio = estadio;
        this.equipo_local = equipo_local;
        this.equipo_visita = equipo_visita;
        this.goles_local = goles_local;
        this.goles_visita = goles_visita;
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

    public Equipo getEquipo_local() {
        return equipo_local;
    }

    public void setEquipo_local(Equipo equipo_local) {
        this.equipo_local = equipo_local;
    }

    public Equipo getEquipo_visita() {
        return equipo_visita;
    }

    public void setEquipo_visita(Equipo equipo_visita) {
        this.equipo_visita = equipo_visita;
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



