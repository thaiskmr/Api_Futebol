package org.fut.futebol.controller;

import org.fut.futebol.model.Partida;
import org.fut.futebol.repository.PartidaRepository;
import org.fut.futebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/partidas")

public class PartidasController {

    private final PartidaRepository partidaRepository;
    private final PartidaService partidaService;

    @Autowired
    public PartidasController(PartidaRepository partidaRepository, PartidaService partidaService) {
        this.partidaRepository = partidaRepository;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPartida(@RequestBody Partida partida) {
        if (partida.getClubeMandante() == null || partida.getClubeVisitante() == null || partida.getEstadio() == null || partida.getDataHora() == null || partida.getGolsMandante() < 0 || partida.getGolsVisitante() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inválidos");
        } else if (partida.getClubeMandante().equals(partida.getClubeVisitante())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Os clubes precisam ser diferentes");
        } else if (partida.getDataHora().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data não pode ser anterior a atual");
        }

        for (Partida p : partidaRepository.findAll()) {
            if (Duration.between(p.getDataHora(), partida.getDataHora()).toHours() < 48 && (p.getClubeMandante().equals(partida.getClubeMandante())) || p.getClubeVisitante().equals(partida.getClubeMandante())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Clube já possui partida agendada");
            }
        }
        partidaRepository.save(partida);
        return ResponseEntity.status(HttpStatus.CREATED).body(partida);
    }

    @GetMapping("/buscarpartida/{id}")
    public ResponseEntity<Partida> buscaridPartida(@PathVariable Long id) {
        Partida partida = partidaService.buscarPartidaPorId(id);
        return ResponseEntity.ok(partida);
    }

    @GetMapping
    public ResponseEntity<Page<Partida>> listarPartidas(
            @RequestParam(required = false) Long idClube,
            @RequestParam(required = false) Long idEstadio,
            @PageableDefault(sort = "dataHora", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(partidaService.listarPartidas(idClube, idEstadio, pageable));

    }
}
