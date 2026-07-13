package br.com.inova.sigin.reservaestoque.mapper;

import br.com.inova.sigin.reservaestoque.dto.ReservaEstoqueResponse;
import br.com.inova.sigin.reservaestoque.entity.ReservaEstoque;
import org.springframework.stereotype.Component;

@Component
public class ReservaEstoqueMapper {

    public ReservaEstoqueResponse toResponse(
            ReservaEstoque entity) {

        return ReservaEstoqueResponse.builder()
                .id(entity.getId())

                .materialId(entity.getMaterial().getId())
                .material(entity.getMaterial().getNome())

                .ordemProducaoId(entity.getOrdemProducao().getId())
                .ordemProducao(entity.getOrdemProducao().getNumero())

                .quantidade(entity.getQuantidade())

                .dataReserva(entity.getDataReserva())

                .ativo(entity.getAtivo())
                .build();
    }
}