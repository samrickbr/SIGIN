package br.com.inova.sigin.produto.repository;

import br.com.inova.sigin.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Produto> findByAtivoTrue();

    List<Produto> findByAtivoFalse();

}