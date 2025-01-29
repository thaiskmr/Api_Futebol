package org.fut.futebol.controller;


import jakarta.validation.Valid;
import org.fut.futebol.model.Clube;
import org.fut.futebol.repository.ClubeRepository;
import org.fut.futebol.service.ClubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/clubes")

public class ClubeController {
    private final ClubeRepository clubeRepository;
    private final ClubeService clubeService;

    @Autowired
    public ClubeController(ClubeRepository clubeRepository, ClubeService clubeService) {
        this.clubeRepository = clubeRepository;
        this.clubeService = clubeService;

    }


    //Cadastro do clube

    @PostMapping
    public ResponseEntity<?> cadastrarClube(@Valid @RequestBody Clube clube, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        //Clube repetido

        Optional<Clube> clubeExistente = clubeRepository.findByNomeAndSiglaEstado(clube.getNome(), clube.getSiglaEstado());
        if (clubeExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Clube já existe no estado " + clube.getSiglaEstado());
        }

        //Data invalida - Criacao maior que data atual

        if (clube.getDataCriacao().isAfter(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de criação não pode ser maior que a data de atual");
        }

        //Save

        Clube savedClube = clubeRepository.save(clube);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClube);
    }

    //Edit

    @PutMapping("/estadio/{id}")
    public ResponseEntity<?> editarClube(@PathVariable Long id, @Valid @RequestBody Clube clube, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Optional<Clube> clubeExistente = clubeRepository.findById(id);
        if (clubeExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clube não encontrado");
        }


        Optional<Clube> clubeComNomeExistente = clubeRepository.findByNomeAndSiglaEstado(clube.getNome(), clube.getSiglaEstado());
        if (clubeComNomeExistente.isPresent() && !clubeComNomeExistente.get().getClubeId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Clube com mesmo nome no estado " + clube.getSiglaEstado());
        }

        Clube updateClube = clubeExistente.get();
        updateClube.setNome(clube.getNome());
        updateClube.setSiglaEstado(clube.getSiglaEstado());
        updateClube.setDataCriacao(clube.getDataCriacao());
        updateClube.setAtivo(clube.isAtivo());
        clubeRepository.save(updateClube);

        return ResponseEntity.status(HttpStatus.OK).body(updateClube);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirClube(@PathVariable Long id) {
        Optional<Clube> clube = clubeRepository.findById(id);
        if (clube.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clube não encontrado");
        }

        Clube clubeExistente = clube.get();
        clubeExistente.setAtivo(false);
        clubeRepository.save(clubeExistente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarClubeId(@PathVariable Long id) {
        Optional<Clube> clube = clubeRepository.findById(id);
        return clube.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Clube>>listarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return  ResponseEntity.ok(clubeService.listarClubes(nome, estado, ativo, pageable));
    }
}
