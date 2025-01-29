package org.fut.futebol.repository;


import org.fut.futebol.model.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


@Repository
public interface ClubeRepository extends JpaRepository<Clube, Long> {
    List<Clube> findBySiglaEstadoClube(String siglaEstadoClube);

    Page<Clube> findByNomeClubeContainingIgnoreCaseOrSiglaEstadoClubeContainingIgnoreCaseOrAtivo(
            String nome, String estado, boolean ativo, Pageable pageable);

    Optional<Clube> findByNomeClubeAndSiglaEstadoClube(String nomeClube, String siglaEstadoClube);

    boolean existsByNomeClubeAndSiglaEstadoClube(String nomeClube, String siglaEstadoClube);

    List<Clube> findByAtivoTrue();
}





