package br.com.inova.sigin.api.delivery.carrinho.repository;

import br.com.inova.sigin.api.delivery.carrinho.entity.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long> {

}