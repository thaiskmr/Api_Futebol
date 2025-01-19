package org.fut.futebol.service;

import java.util.List;


import org.fut.futebol.model.Estadio;
import org.fut.futebol.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Estadio buscarEstadioId(Long idEstadio) {
        return estadioRepository.findById(idEstadio).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estádio inexistente"));
    }

    public Estadio cadastrarEstadio(Estadio estadio) {
        if (estadioRepository.existsByNome(estadio.getNomeEstadio())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Estádio com nome já cadastrado");
        }
        return estadioRepository.save(estadio);
    }

    public Estadio atualizarEstadio(Long idEstadio, Estadio estadioAtualizado) {
        Estadio estadioExistente = buscarEstadioId(idEstadio);
        estadioExistente.setNomeEstadio(estadioAtualizado.getNomeEstadio());
        estadioExistente.setIdEstadio(estadioAtualizado.getIdEstadio());
        return estadioRepository.save(estadioExistente);
    }

    public Estadio removerEstadio(Long idEstadio) {
        Estadio estadio = buscarEstadioId(idEstadio);
        estadioRepository.delete(estadio);
        return estadio;
    }

}
