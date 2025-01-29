package org.fut.futebol.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "Partidas")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartida;

    @ManyToOne
    @JoinColumn(name = "clube_mandante_id", nullable = false)
    private Clube clubeMandante;

    @ManyToOne
    @JoinColumn(name = "clube_visitante_id", nullable = false)
    private Clube clubeVisitante;

    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private Estadio estadio;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "gols_mandante", nullable = false)
    private int golsMandante;

    @Column(name = "gols_visitante", nullable = false)
    private int golsVisitante;

//    @Column(name = "resultado", nullable = false)
//    private String resultado;


    public Long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Long idPartida) {
        this.idPartida = idPartida;
    }

    public Clube getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(Clube clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(Clube clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public Estadio getEstadio() {
        return estadio;
    }

    public void setEstadio(Estadio estadio) {
        this.estadio = estadio;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public void setGolsMandante(int golsMandante) {
        this.golsMandante = golsMandante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsVisitante(int golsVisitante) {
        this.golsVisitante = golsVisitante;
    }

//    public String getResultado() {
//        return resultado;
//    }
//
//    public void setResultado(String resultado) {
//        this.resultado = resultado;
//    }
}