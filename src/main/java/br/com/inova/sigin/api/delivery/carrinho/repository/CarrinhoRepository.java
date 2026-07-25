package br.com.inova.sigin.api.delivery.carrinho.repository;

import br.com.inova.sigin.api.delivery.carrinho.entity.Carrinho;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findByClienteAndStatus(
            Pessoa cliente,
            String status
    );

}