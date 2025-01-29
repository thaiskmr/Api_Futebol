package org.fut.futebol.repository;

import org.fut.futebol.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByClubeMandanteIdOrClubeVisitanteId(Long clubeMandanteId, Long visitanteId);

    List<Partida> findByIdEstadioAndDataHoraBetween(Long idEstadio, LocalDateTime start, LocalDateTime end);

    List<Partida> findByDataHoraBetween(LocalDateTime start, LocalDateTime end);

    List<Partida> findByClubeMandanteIdOrClubeVisitanteIdAndDataHoraBetween(Long clubeMandanteId, Long clubeVisitanteId, LocalDateTime start, LocalDateTime end);

    Page<Partida> findByClubeMandanteIdOrClubeVisitanteId (Long clubeMandanteId, Long clubeVisitanteId, Pageable pageable);

    Page<Partida> findByIdEstadio(Long idEstadio, Pageable pageable);

    Optional<Partida> findByIdPartida(Long idPartida);




}