package org.fut.futebol.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PartidaRepositoryTest {


    @Autowired
    EntityManager entityManager;

    @Autowired
    PartidaRepository partidaRepository;

    @Test
    void findByClubeMandanteIdClubeOrClubeVisitanteIdClube() {
    }

    @Test
    void findByEstadioIdEstadioAndDataHoraBetween() {
    }

    @Test
    void testFindByClubeMandanteIdClubeOrClubeVisitanteIdClube() {
    }

    @Test
    void findByClubeMandanteOrClubeVisitanteAndDataHoraBetween() {
    }

     void findByEstadioIdEstadio() {
    }

    @Test
    void findByIdPartida() {
    }
}