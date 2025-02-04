package org.fut.futebol.service;

import org.fut.futebol.model.Clube;
import org.fut.futebol.repository.ClubeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ClubeServiceTest {

    @InjectMocks
    private ClubeService clubeService;

    @Mock
    private ClubeRepository clubeRepository;




    @Test
    void buscarClubePorId() {
        Clube clube = new Clube();

    }

    @Test
    void existsById() {
    }

    @Test
    void cadastrarClube() {
        Clube clube = new Clube();
        clube.setNomeClube("Fortaleza");
        clube.setSiglaEstadoClube("CE");
        clube.setDataCriacaoClube(LocalDate.parse("1920-01-01"));
        clube.setAtivo(true);
        when(clubeRepository.save(any(Clube.class))).thenReturn(clube);
        Clube resultado = clubeService.cadastrarClube(clube);

        assertEquals(clube.getNomeClube(), resultado.getNomeClube());
        assertEquals(clube.getSiglaEstadoClube(), resultado.getSiglaEstadoClube());
        assertEquals(LocalDate.parse("1920-01-01"), resultado.getDataCriacaoClube());
        assertEquals(true, clube.isAtivo());
        assertNotNull(resultado);

    }

    @Test
    void cadastrarClubeWhenNomeClubleNull() {
        Clube clube = new Clube();
        clube.setNomeClube(null);
        clube.setSiglaEstadoClube("CE");
        clube.setDataCriacaoClube(LocalDate.parse("1920-01-01"));
        clube.setAtivo(true);
        assertThrows(ResponseStatusException.class, () -> clubeService.cadastrarClube(clube));


    }


    @Test
    void editarClube() {
    }

    @Test
    void removerClube() {
    }

    @Test
    void listarClubes() {
    }
}