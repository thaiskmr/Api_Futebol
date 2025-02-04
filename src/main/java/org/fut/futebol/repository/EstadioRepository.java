package org.fut.futebol.repository;
import java.util.Optional;

import org.fut.futebol.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Long> {
    Optional<Estadio> findByNomeEstadio(String nome);

    boolean existsByNomeEstadio(String nome);

    boolean existsByNomeEstadioIgnoreCase (String nome);
}








