package org.fut.futebol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "clubes")

public class Clube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClube;

    @NotNull
    private String nomeClube;

    @NotNull
    private String siglaEstadoClube;

    @NotNull
    private LocalDate dataCriacaoClube;

    @NotNull
    private boolean ativo;

    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getNomeClube() {
        return nomeClube;
    }

    public void setNomeClube(String nomeClube) {
        this.nomeClube = nomeClube;
    }

    public Long getIdClube() {
        return idClube;
    }

    public void setIdClube(Long idClube) {
        this.idClube = idClube;
    }

    public LocalDate getDataCriacaoClube() {
        return dataCriacaoClube;
    }

    public void setDataCriacaoClube(LocalDate dataCriacaoClube) {
        this.dataCriacaoClube = dataCriacaoClube;
    }

    public String getSiglaEstadoClube() {
        return siglaEstadoClube;
    }

    public void setSiglaEstadoClube(String siglaEstadoClube) {
        this.siglaEstadoClube = siglaEstadoClube;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
