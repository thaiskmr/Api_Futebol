package org.fut.futebol.service;

import org.fut.futebol.model.Clube;
import org.fut.futebol.repository.ClubeRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service

public class ClubeService {
    private ClubeRepository clubeRepository;

    public ClubeService(ClubeRepository clubeRepository) {
        this.clubeRepository = clubeRepository;
    }

    public Clube buscarPorId(Long id) {
        return clubeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube não encontrado"));
    }

    public boolean existsById(Long id) {
        return clubeRepository.existsById(id);
    }

    public Clube cadastrarClube(Clube clube) {
        validarClube(clube);
        return clubeRepository.save(clube);
    }

    public Clube editarClube(Long id, Clube novoClube) {
        Clube clubeExistente = buscarPorId(id);
        clubeExistente.setNome(novoClube.getNome());
        clubeExistente.setNome(novoClube.getNome());
        clubeExistente.setAtivo(novoClube.isAtivo());
        return clubeRepository.save(clubeExistente);
    }

    public void removerClube(Long id) {
        if (!existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clube inexistente");
        }
        clubeRepository.deleteById(id);
    }

    private void validarClube(Clube clube) {
        if (clube.getNome() == null || clube.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clube é obrigatorio");
        } else if (clube.getDataCriacao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Data de criação obrigatorio");
        }
    }

    public Page<Clube> listarClubes(String nome, String estado, Boolean ativo, Pageable pageable) {
        if (nome != null || estado != null|| ativo != null) {
            return clubeRepository.findAll(pageable);
        }
        return clubeRepository.findByNomeContainingIgnoreCaseOrSiglaEstadoContainingIgnoreCaseOrAtivo(nome, estado, ativo != null && ativo, pageable);

    }
}
