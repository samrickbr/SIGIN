package br.com.inova.sigin.financeiro.service;

import br.com.inova.sigin.financeiro.dto.FormaPagamentoRequest;
import br.com.inova.sigin.financeiro.dto.FormaPagamentoResponse;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import br.com.inova.sigin.financeiro.mapper.FormaPagamentoMapper;
import br.com.inova.sigin.financeiro.repository.FormaPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository repository;


    public FormaPagamentoResponse salvar(FormaPagamentoRequest dto) {

        FormaPagamento entity = FormaPagamento.builder()
                .descricao(dto.descricao())
                .ativo(true)
                .baixaAutomatica(
                        dto.baixaAutomatica() != null
                                && dto.baixaAutomatica()
                )
                .build();

        return FormaPagamentoMapper.toDTO(
                repository.save(entity)
        );
    }


    public List<FormaPagamentoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(FormaPagamentoMapper::toDTO)
                .toList();
    }
}