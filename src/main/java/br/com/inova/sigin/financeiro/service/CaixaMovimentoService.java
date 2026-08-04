package br.com.inova.sigin.financeiro.service;

import br.com.inova.sigin.financeiro.dto.CaixaMovimentoRequest;
import br.com.inova.sigin.financeiro.dto.CaixaMovimentoResponse;
import br.com.inova.sigin.financeiro.entity.CaixaMovimento;
import br.com.inova.sigin.financeiro.mapper.CaixaMovimentoMapper;
import br.com.inova.sigin.financeiro.repository.CaixaMovimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaixaMovimentoService {

    private final CaixaMovimentoRepository repository;

    public CaixaMovimentoResponse salvar(
            CaixaMovimentoRequest request
    ){
        CaixaMovimento movimento = CaixaMovimento.builder()
                .tipo(request.tipo())
                .valor(request.valor())
                .origem(request.origem())
                .referenciaId(request.referenciaId())
                .observacao(request.observacao())
                .build();

        return CaixaMovimentoMapper.toResponse(
                repository.save(movimento)
        );
    }

    public List<CaixaMovimentoResponse> listar(){
        return repository.findAll()
                .stream()
                .map(CaixaMovimentoMapper::toResponse)
                .toList();
    }
}