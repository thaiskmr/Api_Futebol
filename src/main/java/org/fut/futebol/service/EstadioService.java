package org.fut.futebol.service;

import java.util.List;


import org.fut.futebol.model.Estadio;
import org.fut.futebol.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class EstadioService {
    @Autowired
    private EstadioRepository estadioRepository;

    public List<Estadio> listaEstadio() {
        return estadioRepository.findAll();
    }

    public Estadio buscaridEstadio(Long idEstadio) {
        return estadioRepository.findById(idEstadio).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estádio inexistente"));
    }

    public Estadio cadastrarEstadio(Estadio estadio) {
        if (estadioRepository.existsByNomeEstadio(estadio.getNomeEstadio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Estádio com nome já cadastrado");
        }
        return estadioRepository.save(estadio);
    }

    public Estadio editarEstadio(Long id, Estadio estadioAtualizado) {
        Estadio estadioExistente = estadioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estádio inexistente"));
        if (estadioRepository.existsByNomeEstadioIgnoreCase(estadioAtualizado.getNomeEstadio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Estádio já existente");
        }
        estadioExistente.setNomeEstadio(estadioAtualizado.getNomeEstadio());
        return estadioRepository.save(estadioExistente);
    }

//    public Estadio atualizarEstadio(Long idEstadio, Estadio estadioAtualizado) {
//        Estadio estadioExistente = buscaridEstadio(idEstadio);
//        estadioExistente.setNomeEstadio(estadioAtualizado.getNomeEstadio());
//        estadioExistente.setIdEstadio(estadioAtualizado.getIdEstadio());
//        return estadioRepository.save(estadioExistente);
//    }

    public Estadio removerEstadio(Long idEstadio) {
        Estadio estadio = buscaridEstadio(idEstadio);
        estadioRepository.delete(estadio);
        return estadio;
    }

    public Page<Estadio> listarEstadios(Pageable pageable) {
        return estadioRepository.findAll(pageable);
    }

}
