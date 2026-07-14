package br.com.inova.sigin.configuracao.service;

import br.com.inova.sigin.configuracao.entity.ConfiguracaoSistema;
import br.com.inova.sigin.configuracao.repository.ConfiguracaoSistemaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfiguracaoSistemaService {

    private final ConfiguracaoSistemaRepository repository;

    private ConfiguracaoSistema obterConfiguracao() {

        return repository.findById(1L)
                .orElseThrow(() ->
                        new RegraNegocioException("Configuração do sistema não encontrada."));
    }

    @Transactional
    public String gerarProximoNumeroPedido() {

        ConfiguracaoSistema config = obterConfiguracao();

        String numero = String.format(
                "PED%06d",
                config.getProximoNumeroPedido()
        );

        config.setProximoNumeroPedido(
                config.getProximoNumeroPedido() + 1
        );

        repository.save(config);

        return numero;
    }

    @Transactional
    public String gerarProximoNumeroOp() {

        ConfiguracaoSistema config = obterConfiguracao();

        String numero = String.format(
                "OP%06d",
                config.getProximoNumeroOp()
        );

        config.setProximoNumeroOp(
                config.getProximoNumeroOp() + 1
        );

        repository.save(config);

        return numero;
    }

    public Long getLocalProducaoPadraoId() {
        return obterConfiguracao()
                .getLocalProducaoPadrao()
                .getId();
    }
}