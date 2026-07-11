package br.com.inova.sigin.material.repository;

import br.com.inova.sigin.material.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    Optional<Material> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

}