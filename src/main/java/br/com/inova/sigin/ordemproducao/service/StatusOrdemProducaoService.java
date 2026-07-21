package br.com.inova.sigin.ordemproducao.service;

import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusOrdemProducaoService {

    private final OrdemProducaoRepository repository;


    public void atualizar(
            OrdemProducao ordemProducao) {

        if (ordemProducao.getStatus()
                == StatusOrdemProducao.ABERTA) {

            ordemProducao.setStatus(
                    StatusOrdemProducao.EM_PRODUCAO
            );
        }

        repository.save(ordemProducao);
    }
}