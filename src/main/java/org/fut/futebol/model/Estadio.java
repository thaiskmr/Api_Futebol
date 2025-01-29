package org.fut.futebol.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Estadio")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadio;

    @Column(nullable = false, unique = true)
    private String nomeEstadio;

    @OneToMany(mappedBy = "estadio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partida;

    public Long getEstadioId() {
        return idEstadio;
    }

    public void setIdEstadio(Long idEstadio) {
        this.idEstadio = idEstadio;
    }

    public String getNomeEstadio() {
        return nomeEstadio;
    }

    public void setNomeEstadio(String nomeEstadio) {
        this.nomeEstadio = nomeEstadio;
    }

    public List<Partida> getPartida() {
        return partida;
    }

    public void setPartida(List<Partida> partida) {
        this.partida = partida;
    }
}
