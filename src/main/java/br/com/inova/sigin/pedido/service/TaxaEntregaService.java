package br.com.inova.sigin.pedido.service;

import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import br.com.inova.sigin.pedido.enums.TipoRecebimento;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TaxaEntregaService {

    private final ConfiguracaoSistemaService configuracaoSistemaService;

    public BigDecimal calcular(TipoRecebimento tipoRecebimento) {

        if (tipoRecebimento == null) {
            throw new RegraNegocioException(
                    "Tipo de recebimento é obrigatório."
            );
        }

        if (tipoRecebimento == TipoRecebimento.RETIRADA) {
            return BigDecimal.ZERO;
        }

        if (tipoRecebimento == TipoRecebimento.ENTREGA) {
            return configuracaoSistemaService
                    .getTaxaEntregaPadrao();
        }

        throw new RegraNegocioException(
                "Tipo de recebimento inválido."
        );
    }
}