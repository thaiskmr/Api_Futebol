package org.fut.futebol.controller;

import org.fut.futebol.model.Estadio;
import org.fut.futebol.service.EstadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;


import java.util.List;

@RestController
@RequestMapping("/api/estadios")

public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @GetMapping
    public ResponseEntity<List<Estadio>> listaEstadio(){
        List<Estadio> estadios = estadioService.listaEstadio();
        return ResponseEntity.status(HttpStatus.OK).body(estadios);
    }

    @PostMapping
    public ResponseEntity<Estadio> cadastrarEstadio(@RequestBody Estadio estadio){
        Estadio novoEstadio = estadioService.cadastrarEstadio(estadio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstadio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadio>editarEstadio(@PathVariable Long id, @RequestBody Estadio estadio){
        Estadio estadioAtualizado = estadioService.editarEstadio(id, estadio);
        return ResponseEntity.status(HttpStatus.OK).body(estadioAtualizado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Estadio> atualizarEstadio(@PathVariable Long id, @RequestBody Estadio estadio){
        Estadio estadioAtualizado = estadioService.atualizarEstadio(id, estadio);
        return ResponseEntity.status(HttpStatus.OK).body(estadioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Estadio> removerEstadio(@PathVariable Long id){
        estadioService.removerEstadio(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<Estadio>>listarEstadios(@PageableDefault(sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Estadio> estadio = estadioService.listarEstadios(pageable);
        return ResponseEntity.ok(estadio);
    }
}
