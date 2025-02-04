package org.fut.futebol.repository;

import jakarta.persistence.EntityManager;
import org.fut.futebol.model.Clube;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ClubeRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void findBySiglaEstadoClube() {
    }

    @Test
    void findByNomeClubeContainingIgnoreCaseOrSiglaEstadoClubeContainingIgnoreCaseOrAtivo() {
    }

    @Test
    void findByNomeClubeAndSiglaEstadoClube() {

    }

//    private Clube cadastrarClube(Clube clube) {
//
//    }

    @Test
    void existsByNomeClubeAndSiglaEstadoClube() {
    }

    @Test
    void findByAtivoTrue() {
    }
}