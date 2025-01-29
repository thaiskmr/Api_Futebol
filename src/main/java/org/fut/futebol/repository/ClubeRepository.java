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

    Page<Clube> findByNomeContainingIgnoreCaseOrSiglaEstadoContainingIgnoreCaseOrAtivo(
            String nome, String estado, boolean ativo, Pageable pageable);

    Optional<Clube> findByNomeAndSiglaEstado(String nome, String siglaEstado);

    boolean existsByNomeAndSiglaEstado(String nome, String siglaEstado);

    List<Clube> findBySiglaEstado(String siglaEstado);

    List<Clube> findByAtivoTrue();
}





