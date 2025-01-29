package org.fut.futebol.service;

import java.util.List;
import java.time.LocalDateTime;

import org.fut.futebol.model.Partida;
import org.fut.futebol.model.Clube;
import org.fut.futebol.model.Estadio;
import org.fut.futebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeService clubeService;

    @Autowired
    private EstadioService estadioService;

    public Partida cadastrarPartida(Partida partida) {
        validarPartida(partida);
        return partidaRepository.save(partida);
    }

    /// revisado
    public Partida editarPartida(Long id, Partida partidaAtualizada) {
        Partida partidaExistente = partidaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida inexistente"));
        validarPartida(partidaAtualizada);

        partidaExistente.setClubeMandante(partidaAtualizada.getClubeMandante());
        partidaExistente.setClubeVisitante(partidaAtualizada.getClubeVisitante());
        partidaExistente.setEstadio(partidaAtualizada.getEstadio());
        partidaExistente.setDataHora(partidaAtualizada.getDataHora());

        return partidaRepository.save(partidaExistente);
    }

    public void removerPartida(Long id) {
        if (!partidaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não localizada");
        }
        partidaRepository.deleteById(id);
    }

    /// revisado
    public Partida buscarPartidaPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não localizada"));
    }

    public Page<Partida> listarPartidas(Long clubeId, Long estadioId, Pageable pageable) {
        if (clubeId != null) {
            return partidaRepository.findByClubeMandanteIdOrClubeVisitanteId(clubeId, clubeId, pageable);
        } else if (estadioId != null) {
            return partidaRepository.findByClubeMandanteIdOrClubeVisitanteId(clubeId, clubeId, pageable);
        }
        return partidaRepository.findAll(pageable);
    }

    /// rev
    private void validarPartida(Partida partida) {
        Clube clubeMandante = clubeService.buscarPorId(partida.getClubeMandante().getClubeId());
        Clube clubeVisitante = clubeService.buscarPorId(partida.getClubeVisitante().getClubeId());
        Estadio estadio = estadioService.buscarEstadioId(partida.getEstadio().getEstadioId());

        if (clubeMandante == null || clubeVisitante == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um dos clubes é invalido!");
        }

        if (clubeMandante.getClubeId().equals(clubeVisitante.getClubeId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os clubes não podem ser iguais!");
        }

        if (!clubeMandante.isAtivo() || !clubeVisitante.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Um dos clubes está inativos!");
        }

        if (estadio == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estádio não encontrado!");
        }

        if (partida.getDataHora().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data da partida não pode ser anterior a atual!");
        }

        if (partida.getGolsMandante() < 0 || partida.getGolsVisitante() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os gols não podem ser negativos");
        }


        if (clubeMandante.getDataCriacao().isAfter(partida.getDataHora().toLocalDate()) || clubeVisitante.getDataCriacao().isAfter(partida.getDataHora().toLocalDate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data da partida não pode ser anterior a data de criação dos clubes");
        }
/// rev
        Partida partidaExistente = partidaRepository.findByPartidaId(partida.getPartidaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida inexistente"));
        Partida partidaAtualizada = partida;

        LocalDateTime startOfDay = partidaAtualizada.getDataHora().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        partidaRepository.findByEstadioIdAndDataHoraBetween(estadio.getEstadioId(), startOfDay, endOfDay);


        if (!partidaExistente.getEstadio().equals(partidaAtualizada.getEstadio()) || !partidaExistente.getDataHora().equals(partidaAtualizada.getDataHora())) {

            List<Partida> partidaEstadio = partidaRepository.findByEstadioIdAndDataHoraBetween(partidaAtualizada.getEstadio().getEstadioId(), startOfDay, endOfDay);
            if (!partidaEstadio.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Estádio ocupado");
            }
        }

        conflitoHorario(clubeMandante, partidaAtualizada.getDataHora());
        conflitoHorario(clubeVisitante, partidaAtualizada.getDataHora());
    }

    private void conflitoHorario(Clube clube, LocalDateTime dataHora) {
        LocalDateTime startRange = dataHora.minusHours(48);
        LocalDateTime endRange = dataHora.plusHours(48);

        List<Partida> partidasClube = partidaRepository.findByClubeMandanteIdOrClubeVisitanteIdAndDataHoraBetween(clube.getClubeId(), clube.getClubeId(), startRange, endRange);

        if (!partidasClube.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Clube já possui partida no horário informado");
        }
    }
}